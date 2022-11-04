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
            mouseLineMove(cursor_x, cursor_y, moveScreenX * attackDirection[2], moveScreenY * attackDirection[3], 20);
            Thread.sleep(commandCursorPauseBufferTime);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);

            //
            //Move the screen a little out of the corner to where we want to attack
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            //PROLLY NEED TO CLEAN THIS UP buildDirection[] WHERE 0 INDEX IS X AND 1 INDEX IS Y
            mouseLineMove(cursor_x, cursor_y, screenScrollGQDistance * attackDirection[2] * -1, screenScrollGQDistance * attackDirection[3] * -1, 20);
            Thread.sleep(commandCursorPauseBufferTime);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);

            Thread.sleep(commandCursorPauseBufferTime);
            controller.mousePress(LEFT_MOUSE_CLICK);
            Thread.sleep(commandCursorPauseBufferTime);
            controller.mouseRelease(LEFT_MOUSE_CLICK);

            //let's set this as the F2 position
            setF2Position();

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



    public void defendBase() throws IOException, InterruptedException {
        //make ctrl group 1
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

        cursorGQclicks(100,20,-1);

    }
    public void cursorGQScreen(int[] attackDirection){
        try {
            int cursor_x;
            int cursor_y;
            int sizeOfGQScreenMask = screenMaskGQx.length;
            int cycleNumber = 1;
            //go to F2 screen bookmark
            Thread.sleep(commandInputBufferTime);
            controller.keyPress(VK_F2);
            controller.keyRelease(VK_F2);
            Thread.sleep(commandInputBufferTime);
            int j = 0;
            while( j < sizeOfGQScreenMask){

                //make ctrl group 1
                makeCtrlGroup1();


                //don't wait a few seconds before scrolling the screen
                Thread.sleep(commandInputBufferTime);
                //set cursor to middle of screen
                cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2;
                cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2;
                controller.mouseMove(cursor_x, cursor_y);
                Thread.sleep(commandInputBufferTime);
                //This controls the overall screen scrolling
                //only moves the screen if there's no building on screen...arg..a little bit better with 50%
                Boolean buildingIsThere = Find_a_Building();
                Random random = new Random();
                int screenMoveProbability = random.nextInt(64);
                if (!buildingIsThere || screenMoveProbability < 55 ) {
                    controller.mousePress(RIGHT_MOUSE_CLICK);
                    Thread.sleep(commandInputBufferTime);
                    //iterate the screen mask index
                    j = j + 1;
                    //we wanna really try to find the enemy base on the first cycle
                    if (cycleNumber == 1) {
                        mouseLineMove(cursor_x, cursor_y, attackDirection[0] * (screenScrollGQDistance * screenMaskGQx[j] + screenMaskGQx[j] * cycleNumber * 20)
                                , attackDirection[1] * (screenScrollGQDistance * screenMaskGQy[j] + screenMaskGQy[j] * cycleNumber *  3), 20);
                    }else {
                        mouseLineMove(cursor_x, cursor_y, attackDirection[0] * (screenScrollGQDistance * screenMaskGQx[j] + screenMaskGQx[j] * cycleNumber * 10)
                                , attackDirection[1] * (screenScrollGQDistance * screenMaskGQy[j] + screenMaskGQy[j] * cycleNumber *  2), 20);

                    }
                    Thread.sleep(commandCursorPauseBufferTime);
                    controller.mouseRelease(RIGHT_MOUSE_CLICK);
                }
                //reset cursor position for clicks
                cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2 - 512;
                cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2 - 512;

                cursorGQclicks(cursor_x, cursor_y, cycleNumber);
                Look_for_Building();

                //just make it loop forever until someone looses
                if (j == sizeOfGQScreenMask -1 ){
                    j = -1;
                    cycleNumber = cycleNumber + 1;
                    //make new ctrl group 1
                    //make ctrl group 1
                    makeCtrlGroup1();

                    //goback to f2
                    Thread.sleep(commandTextedInputBufferTime);
                    controller.keyPress(VK_F2);
                    controller.keyRelease(VK_F2);
                    Thread.sleep(commandTextedInputBufferTime);

                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void cursorGQclicks(int cursor_x, int cursor_y, int noAltProbability) throws InterruptedException, IOException {

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

            //stop every fifth cycle
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
