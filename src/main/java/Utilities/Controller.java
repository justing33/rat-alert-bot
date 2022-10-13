package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Utilities.Constants.LEFT_MOUSE_CLICK;
import static Utilities.Constants.PLAYABLE_SCREEN_HEIGHT_1920x1080;
import static Utilities.Constants.PLAYABLE_SCREEN_WIDTH_1920x1080;
import static Utilities.Constants.RIGHT_MOUSE_CLICK;
import static Utilities.Constants.commandInputBufferTime;
import static Utilities.Constants.menuScreenChangeBufferTime;
import static Utilities.Constants.quickmatchMapList;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.lang.System.exit;

public class Controller {

    //Static controller object to be used throughout project to perform programmatic inputs
    public static Robot controller;
    public static Integer middleXScreenPos, middleYScreenPos;

    static {
        try {
            controller = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /***
     * ALT TABS into Red Alert
     * Assumes that the game is tabbed into manually at first and then the
     * AI bot is tabbed into next, setting up the alt tab into the game
     * as performed by this method
     * @throws InterruptedException
     */
    public static void altTabIntoGame() throws InterruptedException {
        controller.keyPress(VK_ALT);
        controller.keyRelease(VK_TAB);
        controller.keyPress(VK_TAB);
        controller.keyRelease(VK_ALT);
        Thread.sleep(commandInputBufferTime);
    }

    public static void selectQuickmatch() throws InterruptedException {
        //Select skirmish & online button location on Red Alert main menu
        controller.mouseMove(960,540);
        leftMouseClick();
        Thread.sleep(menuScreenChangeBufferTime);
        //Select the quickmatch button
        controller.mouseMove(860,215);
        leftMouseClick();
    }

    public static void selectFaction() throws InterruptedException {
        //TODO: Hardcode England for now to test light tank build.. randomize faction later?
        selectNewEnglandFaction();
        Thread.sleep(menuScreenChangeBufferTime);
    }

    public static String readMoneyAmount() throws IOException {
        BufferedImage moneyScreenCapture = controller.createScreenCapture(new Rectangle(1663,13,92,34));
        File outputfile = new File(".\\money.png");
        ImageIO.write(moneyScreenCapture, "png", outputfile);
        File imageFile = new File(".\\money.png");
        ITesseract instance = new Tesseract();
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static BufferedImage capturePlayableGameScreen() {
        //Grab the screen not including the side buildings/units bar
        Rectangle playableScreenRect = new Rectangle(0,0,PLAYABLE_SCREEN_WIDTH_1920x1080, PLAYABLE_SCREEN_HEIGHT_1920x1080);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        return gameScreenBuffer;
    }

    /***
     * Find the resolution of the screen the game is being played on
     * The last pixel before the sidebar is 1484 on width
     * Playable game area: 1484px X 1080px (i.e. width to not include building menu)
     *
     * My screen resolution: 1920px X 1080px => 2073600px to iterate through in buffer
     */
    public static void findScreenResolution() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)size.getWidth();
        int screenHeight = (int)size.getHeight();

        //1920x1080 resolution
        if (screenWidth == 1920 && screenHeight == 1080) {
            middleXScreenPos = 1484 / 2;
            middleYScreenPos = screenHeight / 2;
        } else {
            //TODO: Add more resolutions in, fail until then..
            exit(0);
        }
    }

    /***
     * This method determines what quickmatch map is being played on.
     * It takes a screen capture of a designated section of the screen where the name of the map
     * is displayed and uses the Tesseract image -> text recognition library to determine this.
     * On 1920x1080 resolution, the start location of the rectangle is x:514 and y:374
     * with the rectangle itself being width:368px and height:30px
     */
    public static String identifyQuickmatchMap() throws IOException {
        //Constantly check for the map name to be available once the quick match 'search opponent' button is selected
        boolean foundMapName = false;

//        List<MAP> listOfQuickmatchMaps = Arrays.asList(MAP.);
        String mapName = null;

//        for (MAP m : listOfQuickmatchMaps){
//            System.out.println(m.toString());
//        }

        while(!foundMapName) {
            BufferedImage moneyScreenCapture = controller.createScreenCapture(new Rectangle(514, 374, 368, 30));
            File outputfile = new File(".\\map_name.png");
            ImageIO.write(moneyScreenCapture, "png", outputfile);
            File imageFile = new File(".\\map_name.png");
            ITesseract instance = new Tesseract();
            try {
                mapName = instance.doOCR(imageFile);
                System.out.println(mapName);
            } catch (TesseractException e) {
                e.printStackTrace();
            }

            if (quickmatchMapList.contains(mapName)){
                foundMapName = true;
            }
        }
        return mapName;
    }

    public static void leftMouseClick() throws InterruptedException {
        controller.mousePress(LEFT_MOUSE_CLICK);
        controller.mouseRelease(LEFT_MOUSE_CLICK);
        Thread.sleep(commandInputBufferTime);
    }

    public static void rightMouseClick() throws InterruptedException {
        controller.mousePress(RIGHT_MOUSE_CLICK);
        controller.mouseRelease(RIGHT_MOUSE_CLICK);
        Thread.sleep(commandInputBufferTime);
    }

    /***
     *
     * @return An enum denoting the map that is being played in the quickplay ladder match
     */
    public static String determineMap(){
        //TODO: Hardcoded for now, needs logic to determine map later..
        return "ARENA VALLEY EXTREME (MEGA)";
    }

    public static void startQuickmatchMatch() throws InterruptedException {
        controller.mouseMove(1260, 860);
        leftMouseClick();
    }

    private static void selectNewEnglandFaction() throws InterruptedException {
        controller.mouseMove(1185, 565);
        leftMouseClick();
    }
}
