package GameEngine.GameActions.ScreenCommands;

import static Utilities.Constants.*;
import static Utilities.Controller.*;


public class ScreenCommands {

    public void moveScreenDownRight() {
        try {
            //Initiate cursor position
            int cursor_x = 100;
            int cursor_y = 100;
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(RIGHT_MOUSE_CLICK);

            Thread.sleep(commandInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, 600, 600);
            Thread.sleep(commandCursorPauseBufferTime);

            controller.mouseRelease(RIGHT_MOUSE_CLICK);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void mouseLineMove(int start_x, int start_y, int length_x, int length_y) throws InterruptedException {
        System.out.println("Trying to move mouse in a line X=> " + length_x + " Y=> " + length_y);

        int steps = 20;
        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }

}
