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
     * @throws InterruptedException
     */
    public void deployMCV() throws InterruptedException {
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
    }

    /***
     * Builds a small power plant
     * @throws InterruptedException
     */
    public void buildPowerPlant() throws InterruptedException {
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
            placeBuildingDownAtCoordinates(middleXScreenPos + 64, middleYScreenPos + (MCV_VERT_SIZE / 2));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildRefinery() throws InterruptedException {
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
            placeBuildingDownAtCoordinates(middleXScreenPos + 256, middleYScreenPos + (MCV_VERT_SIZE / 2));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildWarFactory() throws InterruptedException {
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
            placeBuildingDownAtCoordinates(middleXScreenPos + 256, middleYScreenPos + (MCV_VERT_SIZE / 2) - 320);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void buildSecondWarFactory() throws InterruptedException {
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
            placeBuildingDownAtCoordinates(middleXScreenPos + 512, middleYScreenPos + (MCV_VERT_SIZE / 2) - 192);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sellConYard() throws InterruptedException {
        try {
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_H);
            controller.keyRelease(VK_H);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_SHIFT);
            Thread.sleep(commandInputBufferTime);
            //Select the sell cursor
            controller.keyPress(VK_S);
            controller.keyRelease(VK_S);
            //Move the mouse to the location to sell building
            controller.mouseMove(middleXScreenPos, middleYScreenPos + (MCV_VERT_SIZE / 2) - 128 );
            Thread.sleep(commandInputBufferTime);
            //Try to place it
            leftMouseClick();
            Thread.sleep(commandInputBufferTime);
            controller.keyRelease(VK_SHIFT);


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void buildLightTanks() throws InterruptedException {
        try {
            placeBuildingDownAtCoordinates(middleXScreenPos + 256, middleYScreenPos + (MCV_VERT_SIZE / 2) - 320);
            Thread.sleep(commandInputBufferTime);
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_R);
            controller.keyRelease(VK_R);
            Thread.sleep(commandInputBufferTime);
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
    private void placeBuildingDownAtCoordinates(int x, int y) throws InterruptedException, IOException {
        System.out.println("Trying to place a building at coordinates X=> " + x + " Y=> " + y);

        //Move the mouse to the location to place building
        controller.mouseMove(x, y);
        Thread.sleep(commandInputBufferTime);
        //Try to place it
        leftMouseClick();
        Thread.sleep(commandInputBufferTime);

        //Capture the game screen
        BufferedImage gameScreenBuffer = capturePlayableGameScreen();

        //Determine if the building was placed successfully or not
        boolean placed = isBuildingPlaced(gameScreenBuffer);
        System.out.println("Placed? " + placed);

        //if not placed, then pick a random new mouse coordinate to try to place the building again
        if (!placed){
            Thread.sleep(commandInputBufferTime);
            Random random = new Random();
            int newX = random.nextInt(x - 10) + 10;
            System.out.println(newX);

            random = new Random();
            int newY = random.nextInt(y - 10) + 10;
            System.out.println(newY);

            placeBuildingDownAtCoordinates(newX, newY);
        } else {
            //Building was placed, break out of recursive loop
            return;
        }
    }

    /***
     * Determines if the building was placed successfully or not
     * @param screen - the section of playable game screen stored in the buffer
     * @return
     */
    private boolean isBuildingPlaced(BufferedImage screen){
        Point mousePointerLocation = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Mouse Coords X => " + mousePointerLocation.getX() + " Y => " + mousePointerLocation.getY());

        /***
         * Get the color pixel at the mouse location to see if the building was placed successfully or not
         * If the RGB value of the color that is returned from this is near RGB(255,255,255) which is
         * pure WHITE pixel, then it is very likely that we did NOT place the building as the placement
         * outline is still there.
         * //TODO: This is going to be problematic logic when we play on a snow map so may need to reconsider approach
         */
        int color = screen.getRaster().getDataBuffer().getElem((((int)mousePointerLocation.getY() + 2 ) * PLAYABLE_SCREEN_WIDTH_1920x1080) + (int)mousePointerLocation.getX() + 5);
        System.out.println("Pixel color: " + color);
        //determine individual colors
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        System.out.println("red: " + red + " green: " + green + " blue: " + blue);
        //If all pixel values are atleast 240 return false meaning the building was not placed
        return red >= 240 && green >= 240 && blue >= 240 ? false : true;
    }
}
