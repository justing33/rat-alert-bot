package GameEngine.GameActions.ScreenCommands;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static Utilities.Constants.*;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.*;


public class ScreenCommands {
    public void moveScreenDownRight() throws InterruptedException {
        try {
            //Initiate cursor position
            int cursor_x = 100;
            int cursor_y = 100;
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(BUTTON2_DOWN_MASK);

            Thread.sleep(commandInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, 300, 300);


             controller.mouseRelease(BUTTON2_DOWN_MASK);


        } catch (Exception e){
            e.printStackTrace();
        }
    }



    private void mouseLineMove(int start_x, int start_y, int length_x, int length_y) throws InterruptedException, IOException {
        System.out.println("Trying to move mouse in a line X=> " + length_x + " Y=> " + length_y);

        int steps = 20;
        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }



}
