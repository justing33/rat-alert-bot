package Utilities;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    //The buffer time the cursor need to stay right mouse pressed to scroll map
    public static final int commandCursorPauseBufferTime = 1000;
    //The buffer time for screen changes in menu options
    public static final int menuScreenChangeBufferTime = 2000;

//    //MAPS INFO
//    public enum MAP {
//        CANYON,
//        KEEP_OFF_THE_GRASS,
//        TOURNAMENT_ARENA,
//        TOURNAMENT_ORE_RIFT,
//        ARENA_VALLEY_EXTREME_MEGA,
//        BULLSEYE,
//        NORTH_BY_NORTHWEST,
//        PATH_BEYOND
//    }

    public static final List<String> quickmatchMapList = new ArrayList(Arrays.asList("BULLSEYE", "CANYON",
            "KEEP OFF THE GRASS", "TOURNAMENT AREA", "TOURNAMENT ORE RIFT", "ARENA VALLEY EXTREME (MEGA)",
            "NORTH BY NORTHWEST", "PATH BEYOND"));


    public static final List<Integer> arenaValleyExtremeMegaMapCode = new ArrayList<>(Arrays.asList(-13682902,
            -13682901, -13354451, -12303050, -11382719, -13947614, -13290715, -13553628, -13944535, -13944021,
            -13878485, -13944277, -13878740, -13812947, -13878740, -13944277, -13878740, -13878996, -13878740,
            -13878740, -13878740, -13944277, -13878740, -13878740, -13812947, -13878484, -14009814, -13944277,
            -13944278, -13812949, -13878742, -13944534, -13878741, -13878484, -13878484, -13878484, -13944021,
            -13944277, -13878741, -13878741, -13878741, -13878741, -13944533, -13878741, -13944277, -13944533,
            -13878740, -13944277, -13944277, -13878740, -13878740, -13878740, -13878996, -13944533, -13944277,
            -14009814, -14009814, -13944021, -13944276, -13944276, -13681879, -11317197, -10922698, -12630222,
            -10132673, -11381960, -12958415, -12038603, -12367308, -11841994, -11316169, -11776459, -11710667,
            -12302285, -11118792, -10330051, -11317195, -12892880, -11841227, -11907789, -10921928, -10921927,
            -11776204, -11513033, -12236237, -11513032, -11119048, -11447237, -12367308, -11775423, -11316682,
            -13287123, -13944022, -13878740, -13878740, -13878740, -13813203, -13878996, -13944277, -13944277,
            -13944277, -13878740, -13812947, -13878740, -13944277, -13944277, -13878740, -13813203, -13878996,
            -13878740, -13944277, -13944277, -13878484, -13878484, -13878484, -13944533, -13878740, -13878740,
            -13813203, -13878996, -14009814, -14009558, -13944021, -13944020, -13944021, -14009813, -14009559,
            -13680851, -11512003, -11117501, -12170184, -12432586, -11448266, -10659524, -11316677, -11382214,
            -11710665, -10397640, -11644873, -11448009, -11645130, -11250888, -11973324, -11513546, -11250889,
            -10922184, -10987976, -11776460, -12236238, -10922184, -10987976, -11448010, -11776203, -11973579,
            -11382216, -11776459, -10528199, -11382474, -11644873, -12038858, -11448009, -10659264, -12302283,
            -13418961, -13812948, -13878998, -13616083, -12369099, -10463417, -10660796, -11777991, -12763601,
            -12894929, -13026258, -13223380, -13551573, -13617109, -13617109, -13748180, -13748180, -13223122,
            -12566991, -12370126, -12764883, -11843528, -13158870, -14605028, -14736358, -13881307, -12500684,
            -10791867, -10463160, -11777734, -12960979, -13420502, -13486294, -13486294, -13551061, -13551061,
            -13551061, -13551062, -13551062, -13354965, -12829649, -12106697, -13683416, -11843270, -13027541,
            -14737385, -14737385, -14340831, -13944534, -13878484, -13944277, -14010070, -14010070, -13944277,
            -13812947, -13812947, -13944277, -13944277, -13878484, -13878484, -13878228, -13878740, -13944533,
            -14075863, -13944277, -13812691, -13812947, -13878740, -13944533, -13878484, -13944533, -13878740,
            -13878484, -13944277, -14075863, -13944021, -13812691, -13812691, -13878740, -13944277, -13878484,
            -13878484, -13878484, -13944277, -14010070, -14075863, -13944277, -13812435, -13812691, -13878484,
            -13944277, -13878228, -13878484, -13878484, -13878740, -13944533, -13944533, -13878485, -10987974,
            -12235980, -13024207, -11840957, -11052992, -11709890, -10264762, -11775935, -11907006, -10725563,
            -11579079, -10527933, -10659261, -11906756, -11709637, -10986429, -11578562, -9803961, -11578824,
            -12235979, -11907272, -11249860, -10855360, -10724546, -11250632, -12170958, -11119305, -11448266,
            -11185353, -11382219, -11841995, -11841995, -11578826, -11644362, -11119048, -11513031, -10790597,
            -11973321, -11907528, -11643577, -11907530, -13484243, -13944277, -13944277, -13878740, -13812691,
            -13878228, -13944277, -13944277, -13878484, -13878484, -13878484, -13944277, -14010070, -14075863,
            -13878740, -13812691, -13878228, -13944277, -13944533, -13878484, -13944277, -13878740, -13878740,
            -14010070, -14075863, -13944277, -13813205, -13747669, -13616340, -12960979, -12698320, -12763599,
            -12959950, -13945817, -13879255, -13879255, -13813206, -13747411, -13878228, -13878484, -14009814,
            -13812691, -13944021, -13878484, -13878484, -14010070, -14075863, -14141912, -15394528, -15526115,
            -15657701, -15855081, -15855337, -15789288, -13552065, -13288636, -13091256, -13025719, -13091513,
            -13223099, -16775155, -16775155, -16775155, -16775155, -16775155, -15722467, -15722467));
}
