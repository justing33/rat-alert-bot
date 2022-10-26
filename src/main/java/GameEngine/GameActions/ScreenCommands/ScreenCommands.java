package GameEngine.GameActions.ScreenCommands;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static Utilities.Constants.*;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.*;
import static java.lang.System.exit;


public class ScreenCommands {

    public void moveScreen(int[] attackDirection) {
        try {
            //Initiate cursor position
            int cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080/2;
            int cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080/2;
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(RIGHT_MOUSE_CLICK);
            //Move the screen to where we want to attack
            Thread.sleep(commandInputBufferTime);
            //PROLLY NEED TO CLEAN THIS UP buildDirection[] WHERE 0 INDEX IS X AND 1 INDEX IS Y
            mouseLineMove(cursor_x, cursor_y, moveScreenX * attackDirection[0], moveScreenY * attackDirection[1], 20);
            Thread.sleep(commandCursorPauseBufferTime);

            controller.mouseRelease(RIGHT_MOUSE_CLICK);

            //let's set this as the F2 position
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_CONTROL);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_F2);
            controller.keyRelease(VK_F2);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyRelease(VK_CONTROL);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void mouseLineMove(int start_x, int start_y, int length_x, int length_y, int steps) throws InterruptedException {

        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }



    public void waitForTanks(){
        try {
            Thread.sleep(commandWaitForTanksTime);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void cursorGQScreen(int[] attackDirection){
        try {
            int cursor_x;
            int cursor_y;
            int sizeOfGQScreenMask = screenMaskGQx.length;

            //go to F2 screen bookmark
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_F2);
            controller.keyRelease(VK_F2);
            Thread.sleep(commandInputBufferTime);
            for( int j = 0; j < sizeOfGQScreenMask ; j++){

                //wait a few seconds before scrolling the screen
                Thread.sleep(commandGQCyclePauseBufferTime);
                //set cursor to middle of screen
                cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2;
                cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2;
                controller.mouseMove(cursor_x, cursor_y);
                Thread.sleep(commandInputBufferTime);
                controller.mousePress(RIGHT_MOUSE_CLICK);
                Thread.sleep(commandInputBufferTime);
                //This controls the overall screen scrolling
                mouseLineMove(cursor_x, cursor_y, screenScrollGQDistance * screenMaskGQx[j] * attackDirection[0], screenScrollGQDistance * screenMaskGQy[j] * attackDirection[1], 20);


                Thread.sleep(commandCursorPauseBufferTime);
                controller.mouseRelease(RIGHT_MOUSE_CLICK);

                //reset cursor position for clicks
                cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2 - 512;
                cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2 - 512;

                cursorGQclicks(cursor_x, cursor_y);

            }

            //Initiate cursor position to top leftish of screen





        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void cursorGQclicks(int cursor_x, int cursor_y) throws InterruptedException, IOException {

        //put cursor to center of screen
        controller.mouseMove(cursor_x, cursor_y);
        int sizeOfGQDirectionMask = cursorMaskGQx.length;
        //We want to cycle the GQing
        for( int i = 0; i < sizeOfGQDirectionMask ; i++) {
            //shift a all units
            controller.keyPress(VK_Q);
            controller.keyPress(VK_SHIFT);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_A);
            controller.keyRelease(VK_A);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyRelease(VK_SHIFT);

            Thread.sleep(commandInputBufferTime);

            //stop every fifth cycle
            if ( i % 5 == 0 ) {
                controller.keyPress(VK_S);
                controller.keyRelease(VK_S);
                Thread.sleep(commandTextedInputBufferTime);
                //gaurd
                controller.keyPress(VK_G);
                controller.keyRelease(VK_G);
            }
            Thread.sleep(commandGQCursorPauseBufferTime);
            //press q and alt and start clicking around the screen
            controller.keyPress(VK_ALT);
            Thread.sleep(commandInputBufferTime);

            mouseLineClick(cursor_x, cursor_y, cursorMaskGQx[i], cursorMaskGQy[i], commandCursorGQJumpPixels, commandCursorGQNumberofJumps);
            controller.keyRelease(VK_ALT);

            cursor_x = commandCursorGQJumpPixels * cursorMaskGQx[i] * commandCursorGQNumberofJumps + cursor_x;
            cursor_y = commandCursorGQJumpPixels * cursorMaskGQy[i] * commandCursorGQNumberofJumps + cursor_y;

            //check to see if game is over and stop bot if it is
            BufferedImage overPixel = captureGameScreenOver();
            int color = overPixel.getRaster().getDataBuffer().getElem(0);
            int blue = color & 0xff;
            int green = (color & 0xff00) >> 8;
            int red = (color & 0xff0000) >> 16;
            System.out.println("TopRight pixel:  red = " + red + "   green = " + green + "    blue = "+ blue);
            if (red > 90  && green == 0 && blue == 0) {
                System.out.println("GAME OVER");
                exit(0);
            }


        }
        controller.keyRelease(VK_Q);


    }

    //this will cause the mouse to move in a line while clicking
    private void mouseLineClick(int start_x, int start_y, int direction_x, int direction_y, int pixels_skipped, int loops) throws InterruptedException {
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


}
