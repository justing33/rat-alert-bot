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
import java.util.ArrayList;
import java.util.List;

import static Utilities.Constants.LEFT_MOUSE_CLICK;
import static Utilities.Constants.PLAYABLE_SCREEN_HEIGHT_1920x1080;
import static Utilities.Constants.PLAYABLE_SCREEN_WIDTH_1920x1080;
import static Utilities.Constants.RIGHT_MOUSE_CLICK;
import static Utilities.Constants.arenaValleyExtremeMegaMapCode;
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
     * It takes a screen capture of a row of pixels on map preview screen
     * and compares that list of pixels to predefined map lists that are
     * keys to identify the map.  This is all hardcoded values.
     */
    public static String midentifyQuickmatchMap() throws IOException, InterruptedException {
        boolean pixelIsGolden = true;

        //Keep checking for the screen to change by observing a single pixel color
        while(pixelIsGolden) {
            //Single pixel in the golden/bronze circle in the top left of the QM screen
            BufferedImage quickmatchScreenCapture = controller.createScreenCapture(new Rectangle(310, 113, 1, 1));
            int goldishPixelColor = quickmatchScreenCapture.getRaster().getDataBuffer().getElem(0);
            int blue = goldishPixelColor & 0xff;
            int green = (goldishPixelColor & 0xff00) >> 8;
            int red = (goldishPixelColor & 0xff0000) >> 16;
            //Hardcoded values looking for
            if (goldishPixelColor != -1317558 && red != 235 && green != 229 && blue != 74) {
                pixelIsGolden = false;
            }
        }

        //Give some time for the screen to change to map preview screen
        Thread.sleep(3000); //TODO: this is variable and needs to be guestimated..?

        //Single line of pixels from the preview map screen used as a key to determine the map
        BufferedImage lineOfMapPixelsScreenCapture = controller.createScreenCapture(new Rectangle(510, 490, 369, 1));
        List<Integer> someMapKey = new ArrayList<>();

        //Fill the temp list with pixel values
        for (int c= 0; c < 369; c++){
            someMapKey.add(lineOfMapPixelsScreenCapture.getRaster().getDataBuffer().getElem(c));
        }
        //Compare to the predefined map lists
        if (someMapKey.equals(arenaValleyExtremeMegaMapCode)){
            return "ARENA VALLEY EXTREME (MEGA)";
        } else {
            //TODO: This..
            exit(0);
        }

        return null;
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

    public static void startQuickmatchMatch() throws InterruptedException {
        controller.mouseMove(1260, 860);
        leftMouseClick();
    }

    private static void selectNewEnglandFaction() throws InterruptedException {
        controller.mouseMove(1185, 565);
        leftMouseClick();
    }
}
