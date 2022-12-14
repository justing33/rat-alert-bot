package Utilities;

import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_R;

public class Constants {

    public static final String SOVIETS = "SOVIETS";
    public static final String ALLIES = "ALLIES";

    public static final Integer LEFT_MOUSE_CLICK = InputEvent.BUTTON1_DOWN_MASK;
    public static final Integer RIGHT_MOUSE_CLICK = InputEvent.BUTTON2_DOWN_MASK;

    public static final Integer SELECT_VEHICLE_MENU = VK_R;

    public static final Integer PLAYABLE_SCREEN_WIDTH_1920x1080 = 1484;
    public static final Integer PLAYABLE_SCREEN_HEIGHT_1920x1080 = 1080;

    //BUILDING PIXEL SIZES
    //TODO: Check this one later.. its estimated for now
    public static final Integer MCV_VERT_SIZE = 100;

    //TODO: Revisit these build times to see if can optimize further
    //BUILD TIMES
    //The time it takes for the MCV to complete deploying before another input can be sent
    public static final int mcvDeployingTime = 1000;
    public static final int powerPlantBuildTime = 6500;
    //The input buffer time in-between commands
    public static final int refineryBuildTime = 32000;
    public static final int warFactoryBuildTime = 32000;

    //BUFFER TIMES
    public static final int commandInputBufferTime = 150;
    //The buffer time for repeated texted inputs i.e. tank building
    public static final int commandTextedInputBufferTime = 30;
    //The buffer time for moving the cursor to scroll map
    public static final int commandCursorLineBufferTime = 10;
    //The buffer time the cursor needs to stay right mouse pressed to scroll map
    public static final int commandCursorPauseBufferTime = 1000;

    // The buffer time in between moving the cursor for GQ positions
    public static final int commandGQCursorPauseBufferTime = 150;

    //The directions that the GQ will scan the screen
    public static final int [] arrayGQx = {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    public static final int [] arrayGQy = {1, 1, 1, 1, 0,-1,-1,-1,-1, 0, 1, 1, 1, 1, 0,-1,-1,-1,-1, 0};
    //The size of the jumps in pixels while GQing
    public static final int commandCursorGQJumpPixels = 64;
    //The number of the jumps while GQing
    public static final int commandCursorGQNumberofJumps = 3;
    //The buffer time enough tanks to be built for an effective first strike
    public static final int commandWaitForTanksTime = 30000;


    //MAPS INFO
    public enum MAP {
        CANYON,
        KEEP_OFF_THE_GRASS,
        TOURNAMENT_ARENA,
        TOURNAMENT_ORE_RIFT,
        ARENA_VALLEY_EXTREME_MEGA,
        BULLSEYE,
        NORTH_BY_NORTHWEST,
        PATH_BEYOND
    }
}
