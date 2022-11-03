package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static GameEngine.Game.startAlliesGame;
import static Utilities.Constants.*;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_1;
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
    public static BufferedImage captureCursorPlaceBuildingSquare(int cursor_x, int cursor_y) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(cursor_x,cursor_y,CURSOR_BUILD_SQUARE_WIDTH, CURSOR_PLACE_BUILDING_SQUARE_HEIGHT);
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
        //System.out.println("TopRight pixel:  red = " + red + "   green = " + green + "    blue = " + blue);
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
    public static BufferedImage captureCursorBuildAttackSquare(int cursor_x, int cursor_y) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(cursor_x,cursor_y-90,CURSOR_BUILD_SQUARE_WIDTH, 25);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }
    public static boolean isBuildingAttackedThere(BufferedImage screen) throws InterruptedException {
        //Point mousePointerLocation = MouseInfo.getPointerInfo().getLocation();
        //System.out.println("Mouse Coords X => " + mousePointerLocation.getX() + " Y => " + mousePointerLocation.getY());

        int blue = 0;
        int green = 0;
        int red = 0;
        int sizeOfCursorSquareArray = CURSOR_BUILD_SQUARE_WIDTH * 25;

        //below we need to see if there is a white pixel below the HP box
        for (int i = 10; i<sizeOfCursorSquareArray; i++) {
            int color = screen.getRaster().getDataBuffer().getElem(i);
            //System.out.println("Pixel color (integer value): " + color);
            //determine individual colors
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            //System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
            if (green > 245 && red > 245 && blue > 245){
                System.out.println("is freindly or we don't have anymore units selected " + i);
                return false;
            }

        }

        //below we'll look for the black around the HP box
        for (int i = 0; i<sizeOfCursorSquareArray; i++) {
            int color = screen.getRaster().getDataBuffer().getElem(i);
            //System.out.println("Pixel color (integer value): " + color);
            //determine individual colors
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            //System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
            if (green < 20 && red < 20 && blue < 20){
                //System.out.println("building still there @ i= " + i);
                setF3Position();
                return true;
            }

        }
        //If there is no black pixel, building is gone set this as new f2 bookmark

        Thread.sleep(commandInputBufferTime);
        controller.keyPress(VK_F3);
        controller.keyRelease(VK_F3);
        Thread.sleep(commandInputBufferTime);
        setF2Position();
        System.out.println("building is gone now");
        return false;
    }

    public static BufferedImage captureGameScreenColum(int x) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(x,1,1, 1078);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    public static BufferedImage captureGameScreenRow(int x, int y) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(x,y,240, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }
    public static boolean Find_a_Building() throws IOException, InterruptedException {
        for (int Colum_Num = 120; Colum_Num < PLAYABLE_SCREEN_WIDTH_1920x1080 - 120;  Colum_Num = Colum_Num+149){
            BufferedImage game_colum = captureGameScreenColum(Colum_Num);
            for (int Row_Num = 1; Row_Num < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 128; Row_Num = Row_Num + 4){
                int numberOfGreenPixels = 0;
                int color = game_colum.getRaster().getDataBuffer().getElem(Row_Num);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                if (red < 20 && green > 240 && blue < 20) {
                    //System.out.println("Found GREEN at x = " + Colum_Num + " y = " + Row_Num);
                    BufferedImage game_row = captureGameScreenRow(Colum_Num-120,Row_Num);
                    for (int j = 1; j < 240; j++){
                        color = game_row.getRaster().getDataBuffer().getElem(j);
                        blue = color & 0xff;
                        green = (color & 0xff00) >> 8;
                        red = (color & 0xff0000) >> 16;
                        if (red < 20 && green > 240 && blue < 20) {
                            //count number of green pixels in a row on the screen
                            numberOfGreenPixels = numberOfGreenPixels + 1;
                            if (numberOfGreenPixels > 90){
                                System.out.println("Found 90 GREEN pixels at j = " + j );
                                return true;
                            }
                        }else  if (numberOfGreenPixels != 0){
                            //reset the count if there's a break in the green pixels   THIS DONT WORK
                            System.out.println("no more green pixels = " + numberOfGreenPixels );
                            numberOfGreenPixels = 0;
                        }
                    }

                }

            }

        }

        return false;
    }


    //this is looking for a building to shoot
    public static void Look_for_Building() throws IOException, InterruptedException {
        Boolean buildingWasShot = false;
        for (int Colum_Num = 120; Colum_Num < PLAYABLE_SCREEN_WIDTH_1920x1080 - 120;  Colum_Num = Colum_Num+149){
            BufferedImage game_colum = captureGameScreenColum(Colum_Num);
            for (int Row_Num = 1; Row_Num < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 128; Row_Num = Row_Num + 4){
                int numberOfGreenPixels = 0;
                int color = game_colum.getRaster().getDataBuffer().getElem(Row_Num);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                if (red < 20 && green > 240 && blue < 20) {
                    //System.out.println("Found GREEN at x = " + Colum_Num + " y = " + Row_Num);
                    BufferedImage game_row = captureGameScreenRow(Colum_Num-120,Row_Num);
                    for (int j = 1; j < 240; j++){
                        color = game_row.getRaster().getDataBuffer().getElem(j);
                        blue = color & 0xff;
                        green = (color & 0xff00) >> 8;
                        red = (color & 0xff0000) >> 16;
                        if (red < 20 && green > 240 && blue < 20) {
                            numberOfGreenPixels = numberOfGreenPixels + 1;
                            if (numberOfGreenPixels > 90){
                                //we want to shoot the building near the left side so we can see white pop up if it is our building
                                //System.out.println("Found Building at x = " + Colum_Num + "y = " + Row_Num);
                                shootBuilding(Colum_Num - 120 + j - 64,Row_Num+75);
                                Thread.sleep(commandInputBufferTime);
                                controller.keyPress(VK_X);
                                controller.keyRelease(VK_X);
                                Thread.sleep(commandInputBufferTime);
                                //gaurd
                                controller.keyPress(VK_G);
                                controller.keyRelease(VK_G);
                                Thread.sleep(commandInputBufferTime);
                                buildingWasShot = true;
                                break;
                            }

                        }else{
                            numberOfGreenPixels = 0;
                        }
                    }

                }
                if (buildingWasShot){
                    break;
                }

            }
            if (buildingWasShot){
                break;
            }

        }

    }

    public static void shootBuilding( int x, int y) throws InterruptedException, IOException {
        int eigen_value_x = 1;
        for (int numOfClicks = 0; numOfClicks < 10; numOfClicks++) {
            Thread.sleep(commandGQCursorPauseBufferTime);
            controller.mouseMove(x,y);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_1);
            controller.keyRelease(VK_1);
            controller.keyPress(VK_Q);
            Thread.sleep(commandInputBufferTime);
            controller.mousePress(LEFT_MOUSE_CLICK);
            controller.mouseRelease(LEFT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_ALT);
            Thread.sleep(commandInputBufferTime);
            Random random = new Random();
            int offset_x = random.nextInt(192);
            int offset_y = random.nextInt(256)-128;
            int moveToX = x + ((64+offset_x) * eigen_value_x);
            int moveToY = y + ((64+offset_y));
            if (moveToX > 10 && moveToX < PLAYABLE_SCREEN_WIDTH_1920x1080 - 10 && moveToY > 10 && moveToY < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 64) {
                controller.mouseMove(moveToX, moveToY);
                eigen_value_x = eigen_value_x * -1;
                controller.mousePress(LEFT_MOUSE_CLICK);
                controller.mouseRelease(LEFT_MOUSE_CLICK);
                Thread.sleep(commandInputBufferTime);
            }
            controller.keyRelease(VK_ALT);
            controller.keyRelease(VK_Q);
            BufferedImage buildingStillThere = captureCursorBuildAttackSquare(x,y);
            Boolean buildingThereBool = isBuildingAttackedThere(buildingStillThere);
            if (!buildingThereBool){
                controller.keyPress(VK_G);
                controller.keyRelease(VK_G);
                break;
            }
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
    public static void setF2Position() throws InterruptedException {
        Thread.sleep(commandInputBufferTime);
        controller.keyPress(VK_CONTROL);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_F2);
        controller.keyRelease(VK_F2);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyRelease(VK_CONTROL);
    }

    public static void setF3Position() throws InterruptedException {
        Thread.sleep(commandInputBufferTime);
        controller.keyPress(VK_CONTROL);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_F3);
        controller.keyRelease(VK_F3);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyRelease(VK_CONTROL);
    }
    public static void makeCtrlGroup1() throws InterruptedException {
        controller.keyPress(VK_SHIFT);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_A);
        controller.keyRelease(VK_A);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyRelease(VK_SHIFT);
        controller.keyPress(VK_CONTROL);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_1);
        controller.keyRelease(VK_1);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyRelease(VK_CONTROL);

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
            //System.out.println("TopRight pixel:  red = " + red + "   green = " + green + "    blue = "+ blue);
            if (red == 118  && green == 94 && blue == 63) {
                //prolly gunna need to change this to look at whether the mcv had spawned or not
                Thread.sleep(commandInputBufferTime);
                Thread.sleep(commandInputBufferTime);
                begin_build = true;
            }
        }




        if (i < 95 && ( (j == 0) || (j==1) || (j==2) || (j==3) || (j==9))) {
            System.out.println("TOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPLEFT;
        }else if (i < 110 && ((j==2)||(j==1))) {
                System.out.println("TOPLEFT:  i = " + i + "   j = " + j);
                return MAP_START.TOPLEFT;
        }else if (i > 255 && ( (j == 0) || (j==1) || (j==2) || (j==3)|| (j==9) )) {
            System.out.println("BOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMLEFT;
        }else if (i > 175 && (j==9) ) {
            System.out.println("BOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMLEFT;
        }else if (i > 220 && (j==3) ) {
            System.out.println("BOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMLEFT;
        }else if (i < 95 && ( (j == 4) || (j==5) || (j==6) || (j==7) || (j==8) )) {
            System.out.println("TOPRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.TOPRIGHT;
        }else if (i > 255 && ( (j == 4) || (j==5) || (j==6) || (j==7) || (j==8))) {
            System.out.println("BOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMRIGHT;
        }else if (i > 175 && i < 255 && ((j == 6) || (j ==5))) {
            System.out.println("BOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMRIGHT;
        }else if (i > 175 && ((j == 10))) {
            System.out.println("BOTTOMMIDLEFT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMMIDLEFT;
        }else if (i > 175 && (j == 11)) {
            System.out.println("BOTTOMMIDRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMMIDRIGHT;
        }else if (i < 175 && (j == 10)) {
            System.out.println("TOPMIDLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPMIDLEFT;
        }else if (i < 175 && (j == 11)) {
            System.out.println("TOPMIDRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.TOPMIDRIGHT;
        }else if (i < 175 && i > 95 && (j == 8)) {
            System.out.println("MIDTOPRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.MIDTOPRIGHT;
        }else if (i < 175 && i > 95 && (j == 0)) {
            System.out.println("MIDTOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.MIDTOPLEFT;
        }else if (i < 175  && i > 100 && (j == 9)) {
            System.out.println("TOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.TOPLEFT;
        }else if (i < 110 && i > 95 && (j == 9)) {
            System.out.println("MIDTOPLEFT:  i = " + i + "   j = " + j);
            return MAP_START.MIDTOPLEFT;
        }else if (i > 175 && i < 255 &&  (j == 0)) {
            System.out.println("MIDBOTTOMLEFT:  i = " + i + "   j = " + j);
            return MAP_START.MIDBOTTOMLEFT;
        }else if (i > 175 && i < 215 &&  (j == 8)) {
            System.out.println("MIDBOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.MIDBOTTOMRIGHT;
            //Orerift Right i=221 j=8
        }else if (i > 215 && i < 255 &&  (j == 8)) {
            System.out.println("BOTTOMRIGHT:  i = " + i + "   j = " + j);
            return MAP_START.BOTTOMRIGHT;
        }

        return MAP_START.NONE;

    }




}


