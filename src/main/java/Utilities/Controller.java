package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static GameEngine.Game.startAlliesGame;
import static Utilities.Constants.*;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.lang.System.exit;

public class Controller {

    //Static controller object to be used throughout project to perform programmatic inputs
    public static Robot controller;
    public static Integer middleXScreenPos, middleYScreenPos;

    static {
        try {
            controller = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /***
     * ALT TABS into Red Alert
     * Assumes that the game is tabbed into manually at first and then the
     * AI bot is tabbed into next, setting up the alt tab into the game
     * as performed by this method
     * @throws InterruptedException
     */
    public static void altTabIntoGame() throws InterruptedException {
        controller.keyPress(VK_ALT);
        controller.keyRelease(VK_TAB);
        controller.keyPress(VK_TAB);
        controller.keyRelease(VK_ALT);
        Thread.sleep(commandInputBufferTime);
    }

    public static String readMoneyAmount() throws IOException {
        BufferedImage moneyScreenCapture = controller.createScreenCapture(new Rectangle(1663,13,92,34));
        File outputfile = new File(".\\money.png");
        ImageIO.write(moneyScreenCapture, "png", outputfile);
        File imageFile = new File(".\\money.png");
        ITesseract instance = new Tesseract();
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static BufferedImage capturePlayableGameScreen() throws IOException {
        //Grab the screen not including the side buildings/units bar
        Rectangle playableScreenRect = new Rectangle(0,0,PLAYABLE_SCREEN_WIDTH_1920x1080, PLAYABLE_SCREEN_HEIGHT_1920x1080);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    public static BufferedImage captureCursorBuildSquare(int cursor_x, int cursor_y) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(cursor_x,cursor_y,CURSOR_BUILD_SQUARE_WIDTH, CURSOR_BUILD_SQUARE_HEIGHT);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }
    public static BufferedImage captureLoadScreenStart(int x) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(x,425,1, 350);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    public static BufferedImage captureGameScreenBegin() throws IOException {
        //Grab the pixel at top right of screen
        Rectangle playableScreenRect = new Rectangle(1900,20,1, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    public static BufferedImage captureGameScreenOver() throws IOException {
        //Grab the pixel at top right of screen
        Rectangle playableScreenRect = new Rectangle(1850,60,1, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }
    //check to see if game is over and stop bot if it is

    public static void Game_over() throws IOException, InterruptedException {
        BufferedImage overPixel = captureGameScreenOver();
        int color = overPixel.getRaster().getDataBuffer().getElem(0);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        System.out.println("TopRight pixel:  red = " + red + "   green = " + green + "    blue = " + blue);
        if (red > 90 && green == 0 && blue == 0) {
            System.out.println("GAME OVER");
            //click on continue button
            Thread.sleep(gameStartWaitTime);
            controller.mouseMove(1000,1020);
            controller.mousePress(LEFT_MOUSE_CLICK);
            controller.mouseRelease(LEFT_MOUSE_CLICK);
            //click on queue button
            Thread.sleep(gameStartWaitTime);
            controller.mouseMove(1250,875);
            controller.mousePress(LEFT_MOUSE_CLICK);
            controller.mouseRelease(LEFT_MOUSE_CLICK);
            //recurse
            startAlliesGame(determineMap());

            exit(0);
        }
    }

    /***
     * Find the resolution of the screen the game is being played on
     * The last pixel before the sidebar is 1484 on width
     * Playable game area: 1484px X 1080px (i.e. width to not include building menu)
     *
     * My screen resolution: 1920px X 1080px => 2073600px to iterate through in buffer
     */
    public static void findScreenResolution() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)size.getWidth();
        int screenHeight = (int)size.getHeight();

        //1920x1080 resolution
        if (screenWidth == 1920 && screenHeight == 1080) {
            middleXScreenPos = 1484 / 2;
            middleYScreenPos = screenHeight / 2;
        } else {
            //TODO: Add more resolutions in, fail until then..
            exit(0);
        }
    }

    public static void leftMouseClick() throws InterruptedException {
        controller.mousePress(LEFT_MOUSE_CLICK);
        controller.mouseRelease(LEFT_MOUSE_CLICK);
        Thread.sleep(commandInputBufferTime);
    }

    public static void rightMouseClick() throws InterruptedException {
        controller.mousePress(RIGHT_MOUSE_CLICK);
        controller.mouseRelease(RIGHT_MOUSE_CLICK);
        Thread.sleep(commandInputBufferTime);
    }

    /***
     *
     * @return An enum denoting the map that is being played in the quickplay ladder match
     */
    public static Constants.MAP_START determineMap() throws IOException, InterruptedException {
        boolean game_started = false;
        int blue = 0;
        int green = 0;
        int red = 0;
        int i = 0;
        int j = 0;
        while (!game_started) {
            int sizeOfLoadScreenLeftArray = 350;
            //look for the turqouise or yellow pixel on start screen
            //startLocationColumPix = [AV left, Bullseye left, KOTG Left, Canyon Left, Canyon Right, BullseyeRight, AV Right
            // Orerift Right, Orerift Left, NBNW MidLeft, NBNW MidRight]
            int [] startLocationColumPix = {528 , 553, 586, 648 , 721, 789, 808, 843, 832, 532, 637, 754};
            int lengthOfStartArray = startLocationColumPix.length;
            for (j = 0; j<lengthOfStartArray; j++){
            BufferedImage loadScreenLeft = captureLoadScreenStart(startLocationColumPix[j]);
                for (i = 0; i < sizeOfLoadScreenLeftArray; i++) {
                    int color = loadScreenLeft.getRaster().getDataBuffer().getElem(i);
                    //System.out.println("Pixel color (integer value): " + color);
                    //determine individual colors
                    blue = color & 0xff;
                    green = (color & 0xff00) >> 8;
                    red = (color & 0xff0000) >> 16;
                    //System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
                    //turquoise player color
                    if (red < 80 && green > 165 && green < 190 && blue > 215) {
                        game_started = true;
                        System.out.println("i = " + i + "   j = " + j);
                        break;
                    }
                    //yellow player color
                    if (red > 230 && green > 230 && blue < 70) {
                        game_started = true;
                        System.out.println("i = " + i + "   j = " + j);
                        break;
                    }
                }
                if (game_started){
                    break;
                }
            }
        }
        //here we need to look for pixel @ 1900,20 for color to become 120,97,56
        boolean begin_build = false;
        while (!begin_build){
           BufferedImage beginPixel = captureGameScreenBegin();
            int color = beginPixel.getRaster().getDataBuffer().getElem(0);
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            System.out.println("TopRight pixel:  red = " + red + "   green = " + green + "    blue = "+ blue);
            if (red == 118  && green == 94 && blue == 63) {
                //prolly gunna need to change this to look at whether the mcv had spawned or not
                Thread.sleep(commandInputBufferTime);
                Thread.sleep(commandInputBufferTime);
                begin_build = true;
            }
        }




        if (i < 175 && ( (j == 0) || (j==1) || (j==2) || (j==3) || (j==9))) {
            System.out.println("TOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPLEFT;
        }else if (i > 175 && ( (j == 0) || (j==1) || (j==2) || (j==3)|| (j==9) )) {
            System.out.println("BOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMLEFT;
        }else if (i < 175 && ( (j == 4) || (j==5) || (j==6) || (j==7) || (j==8) )) {
            System.out.println("TOPRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.TOPRIGHT;
        }else if (i > 175 && ( (j == 4) || (j==5) || (j==6) || (j==7) || (j==8))) {
            System.out.println("BOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMRIGHT;
        }else if (i > 175 && ((j == 10))) {
            System.out.println("BOTTOMMIDLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMMIDLEFT;
        }else if (i > 175 && (j == 11)) {
            System.out.println("BOTTOMMIDRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMMIDRIGHT;
        }else if (i < 175 && (j == 10)) {
            System.out.println("TOPMIDRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.TOPMIDRIGHT;
        }else if (i > 175 && (j == 11)) {
            System.out.println("TOPMIDLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPMIDLEFT;
        }

        return MAP_START.NONE;

    }




}


