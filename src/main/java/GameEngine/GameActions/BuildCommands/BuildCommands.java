package GameEngine.GameActions.BuildCommands;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


import static Utilities.Constants.*;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.*;

public class BuildCommands {

    /***
     * Auto deploys the starting MCV
     */
    public void deployMCV() {
        try {
            boolean mcvInPlace = false;
            int startX = SCREEN_WIDTH_1920x1080/2;
            int startY = SCREEN_HEIGHT_1920x1080/2;
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            //select MCV

            //need to make sure the MCV is in the middle of the screen, move if not
            while(!mcvInPlace) {
                controller.keyPress(VK_A); //key down
                controller.keyRelease(VK_A); //key up
                Thread.sleep(commandInputBufferTime);
                controller.keyPress(VK_H);
                controller.keyRelease(VK_H);
                Thread.sleep(commandInputBufferTime);
                controller.mouseMove(startX,startY);
                BufferedImage cursorSquareBuffer = captureCursorBuildSquare(startX + 32, startY - 128);
                mcvInPlace = isBuildingThere(cursorSquareBuffer);
                controller.mousePress(LEFT_MOUSE_CLICK);
                controller.mouseRelease(LEFT_MOUSE_CLICK);
                Game_over();
            }

            //Deploy button
            controller.keyPress(VK_BACK_SLASH);
            controller.keyRelease(VK_BACK_SLASH);
            Thread.sleep(mcvDeployingTime);
            Thread.sleep(commandInputBufferTime);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void queuePowerPlant() {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            //Initiate building power plant
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_D);
            controller.keyRelease(VK_D);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /***
     * Builds a small power plant
     */
    public void buildPowerPlant(int xCoords, int yCoords) {
        try {
            //Select power plant to be placed
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_D);
            controller.keyRelease(VK_D);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoords, yCoords, 1, 1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void queueRefinery() {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            //Initiate building Refinery
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_M);
            controller.keyRelease(VK_M);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void buildRefinery(int xCoord, int yCoord) {
        try {

            //Select ore refinery to be placed
            controller.keyPress(VK_M);
            controller.keyRelease(VK_M);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoord, yCoord,1, 1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void queuePillBox() {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            //Initiate building PillBox
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_Y);
            controller.keyRelease(VK_Y);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void buildPillBox(int xCoord, int yCoord) {
        try {

            //Select ore refinery to be placed
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_Y);
            controller.keyRelease(VK_Y);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoord, yCoord,1, 1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void queueBarracks() {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            //Initiate building Barracks
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_C);
            controller.keyRelease(VK_C);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildBarracks(int xCoord, int yCoord) {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            Thread.sleep(commandInputBufferTime);
            //Select Barracks to be placed
            controller.keyPress(VK_C);
            controller.keyRelease(VK_C);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoord, yCoord,1, 1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void queueWarFactory() {
        try {
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            //Initiate building WarFactory
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void buildWarFactory(int xCoordinate, int yCoordinate) {
        try {
            //Initiate building war factory
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);
            Thread.sleep(commandInputBufferTime);
            //Select war factory to be placed
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoordinate, yCoordinate,1, 1);
            Thread.sleep(commandInputBufferTime);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sellConYard() {
        try {
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            toggleSellCursor();
            //Move the mouse to the location to sell building AV North
            controller.mouseMove(middleXScreenPos, middleYScreenPos + (MCV_VERT_SIZE / 2) - 128 );
            Thread.sleep(commandInputBufferTime);
            //Try to sell it
            leftMouseClick();
            Thread.sleep(commandInputBufferTime);
            //Move the mouse to the location to sell building Generally
            controller.mouseMove(middleXScreenPos, middleYScreenPos + (MCV_VERT_SIZE / 2) );
            Thread.sleep(commandInputBufferTime);
            //Try to sell it
            leftMouseClick();
            Thread.sleep(commandInputBufferTime);
            toggleSellCursor();
            Thread.sleep(commandInputBufferTime);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void toggleSellCursor(){
        try {
            controller.keyPress(VK_SHIFT);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_S);
            controller.keyRelease(VK_S);
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandInputBufferTime);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildInfs() {
        try {
            controller.keyPress(SELECT_INFANTRY_MENU);
            controller.keyRelease(SELECT_INFANTRY_MENU);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_SHIFT);
            for (int i = 0; i < 3;i++) {
                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_I);
                controller.keyRelease(VK_I);
            }
            //BUILD AN ELTON
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandTextedInputBufferTime);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_U);
            controller.keyRelease(VK_U);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void build99Infs() {
        try {
            controller.keyPress(SELECT_INFANTRY_MENU);
            controller.keyRelease(SELECT_INFANTRY_MENU);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_SHIFT);
            for (int i = 0; i < 10;i++) {
                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_I);
                controller.keyRelease(VK_I);
            }
            //BUILD AN ELTON
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void buildLightTanks() {
        try {
            controller.keyPress(SELECT_VEHICLE_MENU);
            controller.keyRelease(SELECT_VEHICLE_MENU);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_SHIFT);
            for (int i = 0; i < 3;i++) {
                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_L);
                controller.keyRelease(VK_L);
            }
            //BUILD AN EXTRA ORE TRUCK HERE
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandTextedInputBufferTime);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_SHIFT);

            for (int i = 0; i < 3;i++) {
                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_L);
                controller.keyRelease(VK_L);
            }
            //BUILD AN EXTRA ORE TRUCK HERE
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandTextedInputBufferTime);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_SHIFT);

            for (int i = 0; i < 20; i++) {

                Thread.sleep(commandTextedInputBufferTime);
                controller.keyPress(VK_L);
                controller.keyRelease(VK_L);
            }
            controller.keyRelease(VK_SHIFT);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_W);
            controller.keyRelease(VK_W);

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    /***
     * Attempts to place a building down at the specified screen coordinates
     * @param x - the X position of the screen
     * @param y - the Y position of the screen
     * @throws InterruptedException
     * @throws IOException
     */
    private void placeBuildingDownAtCoordinates(int x, int y , int iterate, int numberOfTries) throws InterruptedException, IOException {
        //see if the game is over
        Game_over();
        //System.out.println("Trying to place a building at coordinates X=> " + x + " Y=> " + y);

        //Move the mouse to the location to place building
        controller.mouseMove(x, y);
        mouseLineMove(x,y,5,5,4);

        //Capture a piece of the screen to the right of the cursor
        BufferedImage cursorSquareBuffer = captureCursorBuildSquare( x+10, y-64);

        //Determine if the building is already in the spot
        boolean there1 = isBuildingThere(cursorSquareBuffer);

        //Capture a piece of the screen to the right of the cursor
        cursorSquareBuffer = captureCursorBuildSquare( x+74, y-64);

        //Determine if the building is already in the spot
        boolean there2 = isBuildingThere(cursorSquareBuffer);

        //Capture a piece of the screen to the right of the cursor
        //cursorSquareBuffer = captureCursorBuildSquare( x+138, y-64);
        //Determine if the building is already in the spot
        //boolean there3 = isBuildingThere(cursorSquareBuffer);

        BufferedImage cursorColumBuffer = captureGameScreenColum(x);
        BufferedImage cursorRowBuffer = captureGameScreenRow(y);
        boolean too_far_in_shroud = isTooFarInBlack(cursorColumBuffer,cursorRowBuffer,x,y);

        //System.out.println("too far into the shroud? " + too_far_in_shroud);
        boolean placed = false;

        //if there's not already a building there, attempt to place
        if (!there1 && !there2 && !too_far_in_shroud) {
            //Try to place it
            leftMouseClick();
            mouseLineMove(x,y,-64,0,4);
            Thread.sleep(commandTextedInputBufferTime);
            leftMouseClick();
            //need to make sure the building is fully inplace before sensing if the build is really there
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);

            //Capture a piece of the screen to the right of the cursor
            cursorSquareBuffer = captureCursorPlaceBuildingSquare(x + 10, y - 36);
            //Determine if the building was placed successfully or not
            placed = isBuildingPlaced(cursorSquareBuffer);
            //System.out.println("Placed building? " + placed);
        }
        //if not placed, then pick a random new mouse coordinate to try to place the building again
        if (!placed){
            int iter = iterate + 5;
            Random random = new Random();
            int newX = x + ((random.nextInt(iter)-(iter/2)) * 64);
            if (newX > PLAYABLE_SCREEN_WIDTH_1920x1080-50){
                newX = PLAYABLE_SCREEN_WIDTH_1920x1080/2;
                iterate = 1;
            }
            if (newX < 50){
                newX = PLAYABLE_SCREEN_WIDTH_1920x1080/2;
                iterate = 1;
            }

            random = new Random();
            int newY = y + ((random.nextInt(iter)-(iter/2)) * 64);
            if (newY > PLAYABLE_SCREEN_HEIGHT_1920x1080-25){
                newY = PLAYABLE_SCREEN_HEIGHT_1920x1080/2;
                iterate = 1;
            }
            if (newY < 50){
                newY = PLAYABLE_SCREEN_HEIGHT_1920x1080/2;
                iterate = 1;
            }

            iterate++;
            //so if we try to place a building too many times, hit the build buttons again
            numberOfTries++;
            if ( numberOfTries%15 == 1 ){
                rightMouseClick();
                queuePowerPlant();
                queueWarFactory();
                queueRefinery();
                queueBarracks();
                queuePillBox();
                //return;
            }

            placeBuildingDownAtCoordinates(newX, newY, iterate, numberOfTries);
        } else {
            //Building was placed, break out of recursive loop
            return ;
        }
    }

    private boolean isTooFarInBlack(BufferedImage cursorColumBuffer, BufferedImage cursorRowBuffer, int x_index, int y_index) {
        int blue = 0;
        int green = 0;
        int red = 0;

        if (x_index < 96){
            x_index = 96;
        }
        if (x_index > PLAYABLE_SCREEN_WIDTH_1920x1080 - 192){
            x_index = PLAYABLE_SCREEN_WIDTH_1920x1080 - 192;
        }

        for (int i = x_index - 96; i < x_index + 190; i++){
            int color = cursorRowBuffer.getRaster().getDataBuffer().getElem(i);
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            if (green != 0 && red != 0 && blue != 0) {
                //System.out.println("found not black in row");
                return false;
            }
        }

        if (y_index < 96){
            y_index = 96;
        }
        if (y_index > PLAYABLE_SCREEN_HEIGHT_1920x1080 - 192){
            y_index = PLAYABLE_SCREEN_HEIGHT_1920x1080 - 192;
        }

        for (int i = y_index - 96; i < y_index +190; i++){
            int color = cursorColumBuffer.getRaster().getDataBuffer().getElem(i);
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            if (green != 0 && red != 0 && blue != 0) {
                //System.out.println("found not black in colum");
                return false;
            }
        }


        return true;
    }


    private boolean isBuildingPlaced(BufferedImage screen){
        //Point mousePointerLocation = MouseInfo.getPointerInfo().getLocation();
        //System.out.println("Mouse Coords X => " + mousePointerLocation.getX() + " Y => " + mousePointerLocation.getY());

        int blue = 0;
        int green = 0;
        int red = 0;
        int sizeOfCursorSquareArray = CURSOR_BUILD_SQUARE_WIDTH * CURSOR_PLACE_BUILDING_SQUARE_HEIGHT;
        for (int i = 0; i<sizeOfCursorSquareArray; i++) {
            int color = screen.getRaster().getDataBuffer().getElem(i);
            //System.out.println("Pixel color (integer value): " + color);
            //determine individual colors
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            if (green == 255 && red == 0 && blue == 0){
                //System.out.println("found green pixel at element i = " + i );
                return true;
            }

        }
        //If all pixel values are atleast 240 return false meaning the building was not placed
        return false;
    }

    private boolean isBuildingThere(BufferedImage screen){
        //Point mousePointerLocation = MouseInfo.getPointerInfo().getLocation();
        //System.out.println("Mouse Coords X => " + mousePointerLocation.getX() + " Y => " + mousePointerLocation.getY());

        int blue = 0;
        int green = 0;
        int red = 0;
        int sizeOfCursorSquareArray = CURSOR_BUILD_SQUARE_WIDTH * CURSOR_BUILD_SQUARE_HEIGHT;
        for (int i = 0; i<sizeOfCursorSquareArray; i++) {
            int color = screen.getRaster().getDataBuffer().getElem(i);
            //System.out.println("Pixel color (integer value): " + color);
            //determine individual colors
            blue = color & 0xff;
            green = (color & 0xff00) >> 8;
            red = (color & 0xff0000) >> 16;
            if (red == 0 && green == 255 &&  blue == 0){
                // look for some green
                //System.out.println("found green pixel at element i = " + i );
                return true;
            } else if (red > 160 && green < 35  && blue < 35 ) {
                //look for red stripes
                //System.out.println("found red pixel at element i = " + i);
                return true;

            }

        }
        //If all pixel values are atleast 240 return false meaning the building was not placed
        return false;
    }


    private void mouseLineMove(int start_x, int start_y, int length_x, int length_y, int steps) throws InterruptedException {

        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }


}
