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
            //select MCV
            controller.keyPress(VK_A); //key down
            controller.keyRelease(VK_A); //key up
            Thread.sleep(commandInputBufferTime);
            //Deploy button
            controller.keyPress(VK_BACK_SLASH);
            controller.keyRelease(VK_BACK_SLASH);
            Thread.sleep(mcvDeployingTime);
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * Builds a small power plant
     */
    public void buildPowerPlant(int xCoords, int yCoords) {
        try {
            //Initiate building power plant
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_D);
            controller.keyRelease(VK_D);
            //Wait for the power plant to build
            Thread.sleep(powerPlantBuildTime);
            //Select power plant to be placed
            controller.keyPress(VK_D);
            controller.keyRelease(VK_D);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoords, yCoords, 1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildRefinery(int xCoord, int yCoord) {
        try {
            //Initiate building power plant
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_M);
            controller.keyRelease(VK_M);
            //Wait for the power plant to build
            Thread.sleep(refineryBuildTime);
            //Select ore refinery to be placed
            controller.keyPress(VK_M);
            controller.keyRelease(VK_M);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoord, yCoord,1);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildWarFactory(int xCoordinate, int yCoordinate) {
        try {
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
            //Wait for the war factoryt to build
            Thread.sleep(warFactoryBuildTime);
            //Initiate building war factory
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            //Select war factory to be placed
            controller.keyPress(VK_F);
            controller.keyRelease(VK_F);
            Thread.sleep(commandInputBufferTime);
            placeBuildingDownAtCoordinates(xCoordinate, yCoordinate,1);
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
            Thread.sleep(5000);

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
    private void placeBuildingDownAtCoordinates(int x, int y , int iterate) throws InterruptedException, IOException {

        //System.out.println("Trying to place a building at coordinates X=> " + x + " Y=> " + y);

        //Move the mouse to the location to place building
        controller.mouseMove(x, y);
        mouseLineMove(x,y,5,5,4);

        //Capture a piece of the screen to the right of the cursor
        BufferedImage cursorSquareBuffer = captureCursorBuildSquare( x+64, y-64);

        //Determine if the building is already in the spot
        boolean there = isBuildingPlaced(cursorSquareBuffer);
        System.out.println("Placed building already there? " + there);
        boolean placed = false;

        //if there's not already a building there, attempt to place
        if (!there) {
            Thread.sleep(commandInputBufferTime);
            //Try to place it
            leftMouseClick();
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);


            //Capture a piece of the screen to the right of the cursor
            cursorSquareBuffer = captureCursorBuildSquare(x + 64, y - 64);

            //Determine if the building was placed successfully or not
            placed = isBuildingPlaced(cursorSquareBuffer);
            System.out.println("Placed building? " + placed);
        }

        //if not placed, then pick a random new mouse coordinate to try to place the building again
        if (!placed){
            int iter = iterate + 5;
            Random random = new Random();
            int newX = x + ((random.nextInt(iter)-(iter/2)) * 64);
            if (newX > PLAYABLE_SCREEN_WIDTH_1920x1080-128){
                newX = PLAYABLE_SCREEN_WIDTH_1920x1080/2;
                iterate = 1;
            }
            if (newX < 128){
                newX = PLAYABLE_SCREEN_WIDTH_1920x1080/2;
                iterate = 1;
            }
            System.out.println(newX);

            random = new Random();
            int newY = y + ((random.nextInt(iter)-(iter/2)) * 64);
            if (newY > PLAYABLE_SCREEN_HEIGHT_1920x1080-128){
                newY = PLAYABLE_SCREEN_HEIGHT_1920x1080/2;
                iterate = 1;
            }
            if (newY < 128){
                newY = PLAYABLE_SCREEN_HEIGHT_1920x1080/2;
                iterate = 1;
            }
            System.out.println(newY);
            iterate++;
            placeBuildingDownAtCoordinates(newX, newY, iterate);
        } else {
            //Building was placed, break out of recursive loop
            return ;
        }
    }

    /***
     * Determines if the building was placed successfully or not
     * @param screen - the section of playable game screen stored in the buffer
     * @return
     */
    private boolean isBuildingPlaced(BufferedImage screen){
        //Point mousePointerLocation = MouseInfo.getPointerInfo().getLocation();
        //System.out.println("Mouse Coords X => " + mousePointerLocation.getX() + " Y => " + mousePointerLocation.getY());

        /***
         * Get the color pixel at the mouse location to see if the building was placed successfully or not
         * If the RGB value of the color that is returned from this is near RGB(255,255,255) which is
         * pure WHITE pixel, then it is very likely that we did NOT place the building as the placement
         * outline is still there.
         * //TODO: This is going to be problematic logic when we play on a snow map so may need to reconsider approach
         */
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
            //System.out.println("Pixel RGB color values => red: " + red + " green: " + green + " blue: " + blue);
            if (green == 255 && red == 0 && blue == 0){
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
