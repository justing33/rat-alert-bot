package GameEngine.GameActions.ScreenCommands;

import Utilities.BuildingFinder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Utilities.BuildingFinder.Look_for_Building;
import static Utilities.BuildingFinder.Look_for_the_Blackness;
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
            mouseLineMove(cursor_x, cursor_y, 25 * attackDirection[2] * -1, 25 * attackDirection[3] * -1, 20);
            Thread.sleep(commandCursorPauseBufferTime);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            selectAllMap();
            makeCtrlGroup1();
            Thread.sleep(commandInputBufferTime);
            controller.mousePress(LEFT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            controller.mouseRelease(LEFT_MOUSE_CLICK);

            //let's set this as the F2 position
            setF2Position();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void defendBase() throws IOException, InterruptedException {
        //make ctrl group 1
        selectAllScreen();
        makeCtrlGroup1();
        cursorGQdefend(100,30,-1);
        Game_over();

    }

    /***
     This is main method of most of the gameplay
     */


    public void ScreenCycle(int[] attackDirection, int cycle_number){
        try {
            gotoF2Position();
            setF3Position();
            selectAllMap();
            makeCtrlGroup1();
            boolean f2PositionReset = false;
            int screenScrollPixels = XY_SCREEN_SCROLL_PIXELS + cycle_number * 15;
            //set cursor position for clicks
            int cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2 - 512;
            int cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2 - 512;
            int blackPixelsHere = Look_for_the_Blackness();
            //gq around then shoot the building if there are any
            List Building_locs = Look_for_Building();
            int mumBuildingsHere = Building_locs.size();
            if (mumBuildingsHere > 0) {
                //g-q click around the screen
                cursorGQclicks(cursor_x, cursor_y, -1);
                Random random = new Random();
                int random_Building = random.nextInt(Building_locs.size()/2);
                int shoot_x = (int) Building_locs.get(random_Building * 2);
                int shoot_y = (int) Building_locs.get(random_Building * 2 + 1);
                if (cycle_number > 2){
                    shootBuilding(shoot_x,shoot_y);
                }
            }else{
                cursorGQdefend(cursor_x-10, cursor_y+10, -1);
            }


            //move the screen to another location
            //

            //set cursor to middle of screen
            cursor_x = PLAYABLE_SCREEN_WIDTH_1920x1080 / 2;
            cursor_y = PLAYABLE_SCREEN_HEIGHT_1920x1080 / 2;

            //look to see if there's a better place to attack
            //
            //look away from self
            gotoF2Position();
            controller.mouseMove(cursor_x, cursor_y);
            int x_scroll_pixel = 1 * attackDirection[0] * screenScrollPixels ;
            int y_scroll_pixel = 1 * attackDirection[1] * screenScrollPixels ;
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            Building_locs = Look_for_Building();
            int blackPixelsAway = Look_for_the_Blackness();
            int numBuildingsAway = Building_locs.size();
            gotoF2Position();

            //look left from self
            controller.mouseMove(cursor_x, cursor_y);
            x_scroll_pixel = -1 * attackDirection[0] * screenScrollPixels ;
            y_scroll_pixel = 1 * attackDirection[1] * screenScrollPixels ;
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            Building_locs = Look_for_Building();
            int blackPixelsLeft = Look_for_the_Blackness();
            int numBuildingsLeft = Building_locs.size();
            gotoF2Position();

            //look right from self
            controller.mouseMove(cursor_x, cursor_y);
            x_scroll_pixel = 1 * attackDirection[0] * screenScrollPixels;
            y_scroll_pixel = -1 * attackDirection[1] * screenScrollPixels;
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            Building_locs = Look_for_Building();
            int blackPixelsRight = Look_for_the_Blackness();
            int numBuildingsRight = Building_locs.size();
            gotoF2Position();

            System.out.println("Here: " + mumBuildingsHere + " Away: " + numBuildingsAway + " Left: "+ numBuildingsLeft + " Right: "+ numBuildingsRight);

            //reset the f2 bookmark CLOSER
            gotoF3Position();
            controller.mouseMove(cursor_x, cursor_y);
            x_scroll_pixel = -1 * attackDirection[2] * screenScrollPixels * 2;
            y_scroll_pixel = -1 * attackDirection[3] * screenScrollPixels * 2;
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandInputBufferTime);
            Building_locs = Look_for_Building();
            //if there's enough buildings there reset the f2 bookmark and recurse
            if (Building_locs.size() > 1 ) {
                Boolean Buildings_Allied = (Are_buildings_allied(Building_locs));
                if (!Buildings_Allied) {
                    System.out.println("Moving horde closer to base");
                    setF2Position();
                    ScreenCycle(attackDirection, cycle_number + 1);
                }
            }

            //reset the f2 bookmark AWAY
            gotoF3Position();
            if (numBuildingsAway > mumBuildingsHere &&
                    numBuildingsAway >= numBuildingsRight &&
                    numBuildingsAway >= numBuildingsLeft ||
                    blackPixelsAway > blackPixelsRight &&
                            blackPixelsAway > blackPixelsLeft &&
                            mumBuildingsHere == 0 &&
                            numBuildingsLeft == 0 &&
                            numBuildingsRight == 0) {
                x_scroll_pixel = 1 * attackDirection[0] * screenScrollPixels;
                y_scroll_pixel = 1 * attackDirection[1] * screenScrollPixels;
                controller.mousePress(RIGHT_MOUSE_CLICK);
                Thread.sleep(commandTextedInputBufferTime);
                mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
                controller.mouseRelease(RIGHT_MOUSE_CLICK);
                f2PositionReset = true;
                System.out.println("Moving horde away");
                setF2Position();
                ScreenCycle(attackDirection, cycle_number + 1);
            }

            //reset the f2 bookmark RIGHT
            gotoF3Position();
            controller.mouseMove(cursor_x, cursor_y);
            if (numBuildingsRight > mumBuildingsHere &&
                    numBuildingsRight > numBuildingsLeft &&
                    numBuildingsRight > numBuildingsAway  ||
                    blackPixelsRight > blackPixelsAway &&
                            blackPixelsRight > blackPixelsLeft &&
                            mumBuildingsHere == 0 &&
                            numBuildingsLeft == 0) {

                x_scroll_pixel = 1 * attackDirection[0] * screenScrollPixels;
                y_scroll_pixel = -1 * attackDirection[1] * screenScrollPixels;
                controller.mousePress(RIGHT_MOUSE_CLICK);
                Thread.sleep(commandTextedInputBufferTime);
                mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
                controller.mouseRelease(RIGHT_MOUSE_CLICK);
                f2PositionReset = true;
                System.out.println("Moving horde right");
                setF2Position();
                ScreenCycle(attackDirection, cycle_number + 1);
            }
            //reset the f2 bookmark LEFT
            gotoF3Position();
            if (numBuildingsLeft > mumBuildingsHere &&
                    numBuildingsLeft >= numBuildingsRight &&
                    numBuildingsLeft > numBuildingsAway ||
                    blackPixelsLeft > blackPixelsAway &&
                            blackPixelsLeft > blackPixelsRight &&
                            mumBuildingsHere == 0) {
                x_scroll_pixel = -1 * attackDirection[0] * screenScrollPixels;
                y_scroll_pixel = 1 * attackDirection[1] * screenScrollPixels;
                controller.mousePress(RIGHT_MOUSE_CLICK);
                Thread.sleep(commandTextedInputBufferTime);
                mouseLineMove(cursor_x, cursor_y, x_scroll_pixel, y_scroll_pixel, 20);
                controller.mouseRelease(RIGHT_MOUSE_CLICK);
                f2PositionReset = true;
                System.out.println("Moving horde left");
                setF2Position();
                ScreenCycle(attackDirection, cycle_number + 1);
            }


            //reset the f2 bookmark HERE
            gotoF3Position();
            if (mumBuildingsHere > 0){
                f2PositionReset = true;
                System.out.println("Moving horde here");
                setF2Position();
                ScreenCycle(attackDirection, cycle_number + 1);
            }


            //move the horde further down the map
            controller.mouseMove(cursor_x, cursor_y);
            gotoF3Position();
            double moveScreenAngle = Math.toRadians(Math.random() * 270);
            if (moveScreenAngle > 90 && moveScreenAngle < 180) {
                moveScreenAngle = Math.toRadians(Math.random() * 270);
            }
            double away_direction_Y = Math.sin(moveScreenAngle);
            double away_direction_X = -1 * Math.cos(moveScreenAngle);
            int away_direction_Y_pixels = (int) (away_direction_Y * attackDirection[1] * screenScrollPixels * 2);
            int away_direction_X_pixels = (int) (away_direction_X * attackDirection[0] * screenScrollPixels * 2);
            controller.mouseMove(cursor_x, cursor_y);
            controller.mousePress(RIGHT_MOUSE_CLICK);
            Thread.sleep(commandTextedInputBufferTime);
            mouseLineMove(cursor_x, cursor_y, away_direction_X_pixels, away_direction_Y_pixels, 20);
            controller.mouseRelease(RIGHT_MOUSE_CLICK);
            System.out.println("Moving horde furth randomly down map");
            if (f2PositionReset == false){
                setF2Position();
            }

            System.out.println("Moved Y = " + away_direction_Y_pixels + " X = " + away_direction_X_pixels);
            ScreenCycle(attackDirection, cycle_number + 1);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Boolean Are_buildings_allied(List building_locations) throws InterruptedException, IOException {
        int x_loc = (int) building_locations.get(0);
        int y_loc = (int) building_locations.get(1);
        int look_x = x_loc + 64;
        int look_y = y_loc + 45;
        selectAllMap();
        makeCtrlGroup1();
        controller.mouseMove(look_x, look_y);
        Thread.sleep(commandTextedInputBufferTime);
        controller.keyPress(VK_Q);
        Thread.sleep(commandInputBufferTime);
        controller.mousePress(LEFT_MOUSE_CLICK);
        controller.mouseRelease(LEFT_MOUSE_CLICK);
        Thread.sleep(commandInputBufferTime);
        controller.keyRelease(VK_Q);
        BufferedImage buildingStillThere = captureCursorBuildAttackSquare(x_loc, y_loc);
        //return false if the building is allied
        boolean buildingThereBool = isBuildingAttackedThere(buildingStillThere);
        if (!buildingThereBool) {
            return true;
        }else{
            return false;
        }



    }




    private void cursorGQdefend(int cursor_x, int cursor_y, int noAltProbability) throws InterruptedException, IOException {

        //put cursor to center of screen
        controller.mouseMove(cursor_x, cursor_y);
        int sizeOfGQDirectionMask = cursorMaskGQDefendx.length;
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

            mouseLineClick(cursor_x, cursor_y, cursorMaskGQDefendx[i], cursorMaskGQDefendy[i], commandCursorGQJumpPixels, commandCursorGQNumberofJumps);
            controller.keyRelease(VK_ALT);

            cursor_x = commandCursorGQJumpPixels * cursorMaskGQDefendx[i] * commandCursorGQNumberofJumps + cursor_x;
            cursor_y = commandCursorGQJumpPixels * cursorMaskGQDefendy[i] * commandCursorGQNumberofJumps + cursor_y;

            //check if games is over
            Game_over();
        }
        controller.keyRelease(VK_Q);
    }

    public void mouseLineMove(int start_x, int start_y, int length_x, int length_y, int steps) throws InterruptedException {

        for( int i = 0; i < steps ; i++) {
            Thread.sleep(commandCursorLineBufferTime);
            controller.mouseMove(start_x + ((i * length_x )/ steps) , start_y + ((i * length_y) / steps));
        }
    }


}
