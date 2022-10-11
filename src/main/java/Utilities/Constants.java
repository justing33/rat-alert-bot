package Utilities;

import java.awt.event.InputEvent;

public class Constants {

    public static final String SOVIETS = "SOVIETS";
    public static final String ALLIES = "ALLIES";

    public static final Integer LEFT_CLICK = InputEvent.BUTTON1_DOWN_MASK;

    public static final Integer PLAYABLE_SCREEN_WIDTH_1920x1080 = 1484;
    public static final Integer PLAYABLE_SCREEN_HEIGHT_1920x1080 = 1080;

    public static final Integer MCV_VERT_SIZE = 100;

    //The time it takes for the MCV to complete deploying before another input can be sent
    public static final int mcvDeployingTime = 1000; //TODO: Revisit these build times to see if can optimize further
    public static final int powerPlantBuildTime = 6500;
    //The input buffer time in-between commands
    public static final int refineryBuildTime = 32000;

    public static final int warFactoryBuildTime = 32000;

    public static final int commandInputBufferTime = 150;

    public static final int commandTextedInputBufferTime = 30;
}
