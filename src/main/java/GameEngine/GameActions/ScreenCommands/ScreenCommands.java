package GameEngine.GameActions.ScreenCommands;

import static Utilities.Constants.*;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.*;


public class ScreenCommands {

    public void moveScreenDownRight() {
        try {
            //Initiate cursor position
            int cursor_x = 100;
            int cursor_y = 100;
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(RIGHT_MOUSE_CLICK);

            Thread.sleep(commandInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, 600, 600, 20);
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
        System.out.println("Trying to move mouse in a line X=> " + length_x + " Y=> " + length_y);

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
    public void cursorGQScreen(){
        try {
             //Initiate cursor position to top leftish of screen
            int cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2 - 512;
            int cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2 - 512;
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_F2);
            controller.keyRelease(VK_F2);
            Thread.sleep(commandInputBufferTime);

            cursorGQclicks(cursor_x, cursor_y);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void cursorGQclicks(int cursor_x, int cursor_y) throws InterruptedException {

        //put cursor to center of screen
        controller.mouseMove(cursor_x, cursor_y);
        int sizeOfGQDirectionArray = arrayGQx.length;
        //We want to cycle the GQing
        for( int i = 0; i < sizeOfGQDirectionArray ; i++) {
            //shift a all units
            controller.keyPress(VK_SHIFT);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyPress(VK_A);
            controller.keyRelease(VK_A);
            Thread.sleep(commandTextedInputBufferTime);
            controller.keyRelease(VK_SHIFT);

            Thread.sleep(commandInputBufferTime);
            //stop
            controller.keyPress(VK_S);
            controller.keyRelease(VK_S);
            Thread.sleep(commandTextedInputBufferTime);
            //gaurd
            controller.keyPress(VK_G);
            controller.keyRelease(VK_G);
            Thread.sleep(commandGQCursorPauseBufferTime);
            //press q and alt and start clicking around the screen
            controller.keyPress(VK_Q);
            controller.keyPress(VK_ALT);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineClick(cursor_x, cursor_y, arrayGQx[i], arrayGQy[i], commandCursorGQJumpPixels, commandCursorGQNumberofJumps);
            controller.keyRelease(VK_Q);
            controller.keyRelease(VK_ALT);

            cursor_x = commandCursorGQJumpPixels * arrayGQx[i] * commandCursorGQNumberofJumps + cursor_x;
            cursor_y = commandCursorGQJumpPixels * arrayGQy[i] * commandCursorGQNumberofJumps + cursor_y;
        }


    }

    //this will cause the mouse
    private void mouseLineClick(int start_x, int start_y, int direction_x, int direction_y, int pixels_skipped, int loops) throws InterruptedException {
        System.out.println("Trying to move mouse in spiral from line X=> " + start_x + " Y=> " + start_y);
        int steps = 5;
         for( int i = 0; i < loops ; i++) {

             mouseLineMove(start_x,start_y, pixels_skipped * direction_x, pixels_skipped * direction_y, steps);
             Thread.sleep(commandInputBufferTime);
             controller.mousePress(LEFT_MOUSE_CLICK);
             controller.mouseRelease(LEFT_MOUSE_CLICK);
             Thread.sleep(commandTextedInputBufferTime);
             controller.mousePress(LEFT_MOUSE_CLICK);
             controller.mouseRelease(LEFT_MOUSE_CLICK);
             start_x = start_x + pixels_skipped * direction_x;
             start_y = start_y + pixels_skipped * direction_y;

         }

    }


}
