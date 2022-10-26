package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            int [] startLocationColumPix = {542, 825};
            for (j = 0; j<2; j++){
            BufferedImage loadScreenLeft = captureLoadScreenStart(startLocationColumPix[j]);
                for (i = 0; i < sizeOfLoadScreenLeftArray; i++) {
                    int color = loadScreenLeft.getRaster().getDataBuffer().getElem(i);
                    //System.out.println("Pixel color (integer value): " + color);
                    //determine individual colors
                    blue = color & 0xff;
                    green = (color & 0xff00) >> 8;
                    red = (color & 0xff0000) >> 16;
                    System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
                    //turquoise player color
                    if (red < 15 && green > 165 && green < 190 && blue > 230) {
                        game_started = true;
                        System.out.println("i = " + i + "   j = " + j);
                        break;
                    }
                    //yellow player color
                    if (red > 230 && green > 230 && blue < 15) {
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

        Thread.sleep(gameStartWaitTime);

        if (i < 175 && j == 0) {
            System.out.println("TOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPLEFT;
        }else if (i > 175 && j == 0) {
            System.out.println("BOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMLEFT;
        }else if (i < 175 && j == 1) {
            System.out.println("TOPRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.TOPRIGHT;
        }else if (i > 175 && j == 1) {
            System.out.println("BOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMRIGHT;
        }

        return MAP_START.NONE;

        //TODO: Hardcoded for now, needs logic to determine map later..
        //return MAP.ARENA_VALLEY_EXTREME_MEGA;
    }




}


