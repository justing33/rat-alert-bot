package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static GameEngine.Game.startAlliesGame;
import static Utilities.BuildingFinder.Look_for_Building;
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
        Rectangle playableScreenRect = new Rectangle(cursor_x,cursor_y,CURSOR_BUILD_SQUARE_WIDTH, 25);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        // Save as JPEG
        File file = new File("myimage.jpg");
        ImageIO.write(gameScreenBuffer, "jpg", file);

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
        for (int i = 4; i<sizeOfCursorSquareArray; i++) {
            int color = screen.getRaster().getDataBuffer().getElem(i);
            //System.out.println("Pixel color (integer value): " + color);
            //determine individual colors
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            //System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
            if (green > 249 && red > 249 && blue > 249){
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
                return true;
            }

        }
        
        //If there is no black pixel, building is gone set this as new f3 bookmark
        setF3Position();
        System.out.println("building is gone now");
        return false;
    }

    public static BufferedImage captureGameScreenColum(int x) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(x,1,1, PLAYABLE_SCREEN_HEIGHT_1920x1080 - 2);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }


    public static BufferedImage captureGameScreenRow(int y) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(1,y,PLAYABLE_SCREEN_WIDTH_1920x1080, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    public static BufferedImage captureGameScreenRowBelow(int x, int y, int width) throws IOException {
        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(x,y,width, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }





    public static void shootBuilding( int x, int y) throws InterruptedException, IOException {
        int eigen_value_x = 1;
        int shoot_x = x + 64;
        int shoot_y = y + 45;
        List building_list = Look_for_Building();
        if (building_list.size() == 0) {
            System.out.println("Aborting shootBuilding: no buildings found");
            return;
        }        
        
        for (int numOfClicks = 0; numOfClicks < 12; numOfClicks++) {
            Thread.sleep(commandGQCursorPauseBufferTime);
            controller.mouseMove(shoot_x, shoot_y);
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
            eigen_value_x = eigen_value_x * -1;
            //System.out.println("Moving around x=" + x + " y=" + y);
            moveToShoot(x, y, eigen_value_x);

            controller.keyRelease(VK_ALT);
            controller.keyRelease(VK_Q);
            BufferedImage buildingStillThere = captureCursorBuildAttackSquare(x, y);
            boolean buildingThereBool = isBuildingAttackedThere(buildingStillThere);
            if (!buildingThereBool) {
                controller.keyPress(VK_G);
                controller.keyRelease(VK_G);
                return;
            }
        }
        regroup();
    }

    private static void regroup() throws InterruptedException, IOException {
        System.out.println("Regrouping Because building did not die");
        makeCtrlGroup1();
        setF2Position();

        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_1);
        controller.keyRelease(VK_1);
        Thread.sleep(commandInputBufferTime);
        controller.keyPress(VK_1);
        controller.keyRelease(VK_1);
        int cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2 - 512;
        int cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2 - 512;
        int cycleNumber = 1;
        cursorGQclicks(cursor_x, cursor_y, cycleNumber);
        gotoF2Position();
    }

    public static void cursorGQclicks(int cursor_x, int cursor_y, int noAltProbability) throws InterruptedException, IOException {

            //put cursor to center of screen
            controller.mouseMove(cursor_x, cursor_y);
            int sizeOfGQDirectionMask = cursorMaskGQx.length;
            //We want to cycle the GQing
            for( int i = 0; i < sizeOfGQDirectionMask ; i++) {
                //select ctrl group 1
                Thread.sleep(commandGQCursorPauseBufferTime);
                controller.keyPress(VK_Q);
                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_1);
                controller.keyRelease(VK_1);
                Thread.sleep(commandTextedInputBufferTime);

                //stop every second cycle
                if ( i % 2 == 0 ) {
                    controller.keyPress(VK_S);
                    controller.keyRelease(VK_S);
                    Thread.sleep(commandInputBufferTime);
                    //gaurd
                    controller.keyPress(VK_G);
                    controller.keyRelease(VK_G);
                    Thread.sleep(commandInputBufferTime);
                }
                //press q and alt and start clicking around the screen
                //don't hold alt every time so that we may hit some buildings too
                //cycle 1 is always to alt
                //cycle 2 is always to alt
                //cycle 3 is 1/2 to alt
                //cycle 4 is 2/3 to alt
                //cycle 5 is 3/4 to alt
                Random random = new Random();
                if (noAltProbability<=0){
                    controller.keyPress(VK_ALT);
                }else if (1%(random.nextInt(noAltProbability)+1) != 0) {
                    controller.keyPress(VK_ALT);
                }

                Thread.sleep(commandInputBufferTime);

                mouseLineClick(cursor_x, cursor_y, cursorMaskGQx[i], cursorMaskGQy[i], commandCursorGQJumpPixels, commandCursorGQNumberofJumps);
                controller.keyRelease(VK_ALT);

                cursor_x = commandCursorGQJumpPixels * cursorMaskGQx[i] * commandCursorGQNumberofJumps + cursor_x;
                cursor_y = commandCursorGQJumpPixels * cursorMaskGQy[i] * commandCursorGQNumberofJumps + cursor_y;

                //check if games is over
                Game_over();
            }
            controller.keyRelease(VK_Q);

    }

    //this will cause the mouse to move in a line while clicking
    public static void mouseLineClick(int start_x, int start_y, int direction_x, int direction_y, int pixels_skipped, int loops) throws InterruptedException {
        //System.out.println("Trying to move mouse in spiral from line X=> " + start_x + " Y=> " + start_y);

        int steps = 5;
        for( int i = 0; i < loops ; i++) {
            Random random = new Random();
            mouseLineMove(start_x,start_y, pixels_skipped * direction_x + random.nextInt(64)-32, pixels_skipped * direction_y+ random.nextInt(64)-32, steps);
            Thread.sleep(commandInputBufferTime);
            controller.mousePress(LEFT_MOUSE_CLICK);
            controller.mouseRelease(LEFT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);

            start_x = start_x + pixels_skipped * direction_x;
            start_y = start_y + pixels_skipped * direction_y;

        }

    }

    public static void mouseLineMove(int start_x, int start_y, int length_x, int length_y, int steps) throws InterruptedException {

        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }

    private static void moveToShoot(int x, int y, int eigen_value_x) throws InterruptedException {
        Random random = new Random();
        int offset_x = random.nextInt(256);
        int offset_y = random.nextInt(256)-128;
        int moveToX = x + 96 + ((96+offset_x) * eigen_value_x);
        int moveToY = y + offset_y;
        //System.out.println("Moving to x=" + moveToX + " y=" + moveToY);
        if (moveToX > 10 && moveToX < PLAYABLE_SCREEN_WIDTH_1920x1080 - 10 && moveToY > 10 && moveToY < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 10) {
            controller.mouseMove(moveToX, moveToY);
            controller.mousePress(LEFT_MOUSE_CLICK);
            controller.mouseRelease(LEFT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
        }else{
            eigen_value_x = eigen_value_x * -1;
            moveToShoot(x, y, eigen_value_x);
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
    public static void gotoF2Position() throws InterruptedException {
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_F2);
        controller.keyRelease(VK_F2);
        Thread.sleep(commandTextedInputBufferTime);
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

    public static void gotoF3Position() throws InterruptedException {
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_F3);
        controller.keyRelease(VK_F3);
        Thread.sleep(commandTextedInputBufferTime);
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


