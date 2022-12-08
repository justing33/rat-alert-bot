package GameEngine.GameActions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static Utilities.Controller.*;
import static Utilities.ObserverConstants.PLAYER_NAMES;
import static Utilities.ObserverConstants.RASTER_caption1v1;
import static Utilities.ScreenReader.readScreenString;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.Math.abs;

public class ObserverMode {
    public static void watchGame() throws InterruptedException, AWTException, IOException {

        //1500,50 top left of minimap
        //1900,400 bottom right of minimap
        int minimapTopLeft_x = 1500;
        int minimapTopLeft_y = 50;
        int minimapWidth = 400;
        int minimapHeight = 350;
        int color;
        int red;
        int green;
        int blue;
        int index;
        int cursor_x = 1;
        int cursor_y = 2;
        int screen_move_accuity = 4;
        int hasNotMoved = 0;
        int dist_square = 10;
        while(!gameCompleted()) {

            ArrayList<Integer> yellow_x = new ArrayList<Integer>();
            ArrayList<Integer> yellow_y = new ArrayList<Integer>();
            ArrayList<Integer> blue_x = new ArrayList<Integer>();
            ArrayList<Integer> blue_y = new ArrayList<Integer>();
            int yellowIndex = 0;
            int blueIndex = 0;


            Thread.sleep(500);

            Rectangle minimapRect = new Rectangle(minimapTopLeft_x, minimapTopLeft_y, minimapWidth, minimapHeight);
            BufferedImage gameScreenBuffer = controller.createScreenCapture(minimapRect);

            for (int j = 0; j < minimapHeight; j++) {
                for (int i = 0; i < minimapWidth; i++) {
                    index = j * minimapWidth + i;
                    color = gameScreenBuffer.getRaster().getDataBuffer().getElem(index);
                    blue = color & 0xff;
                    green = (color & 0xff00) >> 8;
                    red = (color & 0xff0000) >> 16;
                    //yellow
                    if (red > 230 && green > 230 && blue < 70) {
                        yellow_x.add(i);
                        yellow_y.add(j);
                        yellowIndex++;
                    }

                    //blue
                    if (red < 80 && green > 200 && green < 250 && blue > 215) {
                        blue_x.add(i);
                        blue_y.add(j);
                        blueIndex++;
                    }
                }
            }



            int dist_square_min = 100000;
            int yellow_element;
            int blue_element;
            int avg_x = 10;
            int avg_y = 10;
            int final_yellow_element = 0;
            int final_blue_element = 0;

            for (yellow_element = 0; yellow_element < yellow_x.size(); yellow_element++) {
                Integer yellow_element_x = yellow_x.get(yellow_element);
                Integer yellow_element_y = yellow_y.get(yellow_element);

                for (blue_element = 0; blue_element < blue_x.size(); blue_element++) {
                    Integer blue_element_x = blue_x.get(blue_element);
                    Integer blue_element_y = blue_y.get(blue_element);

                    dist_square = (yellow_element_x - blue_element_x) * (yellow_element_x - blue_element_x)
                            + (yellow_element_y - blue_element_y) * (yellow_element_y - blue_element_y);

                    if (dist_square < dist_square_min) {
                        avg_x = (yellow_element_x + blue_element_x) / 2;
                        avg_y = (yellow_element_y + blue_element_y) / 2;
                        dist_square_min = dist_square;
                        final_yellow_element = yellow_element;
                        final_blue_element = blue_element;
                    }

                }

            }

            if (yellow_x.size() > 0 && blue_x.size() > 0) {

                //System.out.println((yellow_x.get(0) + minimapTopLeft_x) + "  " + (yellow_y.get(0) + minimapTopLeft_y));
                //System.out.println((blue_x.get(0) + minimapTopLeft_x) + "  " + (blue_y.get(0) + minimapTopLeft_y));
                System.out.println("avg x = " + (avg_x + minimapTopLeft_x) + "  avg y = " + (avg_y + minimapTopLeft_y));
                System.out.println("Screen hasn't moved in " + hasNotMoved + " iterations");
                //System.out.println((yellow_x.get(final_yellow_element) + minimapTopLeft_x) + "  " + (yellow_y.get(final_yellow_element) + minimapTopLeft_y));
                //System.out.println((blue_x.get(final_blue_element) + minimapTopLeft_x) + "  " + (blue_y.get(final_blue_element) + minimapTopLeft_y));
                System.out.println("dist squared = " + dist_square_min);

                if (abs(cursor_x - (avg_x + minimapTopLeft_x)) > screen_move_accuity &&
                        abs(cursor_y - (avg_y + minimapTopLeft_y)) > screen_move_accuity) {
                    System.out.println("Moving Screen to the action");
                    cursor_x = (avg_x + minimapTopLeft_x);
                    cursor_y = (avg_y + minimapTopLeft_y);
                    controller.mouseMove((avg_x + minimapTopLeft_x)+8, (avg_y + minimapTopLeft_y)-4);
                    leftMouseClick();
                    //we moved the screen so reset this counter
                    hasNotMoved = 1;
                }
            } else {
                Boolean onLaunch = onTheLaunchScreen();
                System.out.println("We on the launch screen? " + onLaunch);
                if (onLaunch) {
                    lookForNext1v1(0 , false);
                }
            }
            hasNotMoved = hasNotMoved + 1;
            if (hasNotMoved%30 == 0 && (dist_square_min < 1 || dist_square_min > 800)){
                LookAtBlueBase();
            }
            if (hasNotMoved%35 == 0 && (dist_square_min < 1 || dist_square_min > 800)){
                LookAtYellowBase();
            }
            if (hasNotMoved%40 == 0 && yellow_x.size() > 0 && blue_x.size() > 0  && (dist_square_min < 1 || dist_square_min > 800)){
                System.out.println("moving back to the closest units");
                controller.mouseMove((avg_x + minimapTopLeft_x)+8, (avg_y + minimapTopLeft_y)-4);
                leftMouseClick();
            }

        }

    }

    private static Boolean onTheLaunchScreen() throws InterruptedException, AWTException {
        Rectangle minimapRect = new Rectangle(340,117,1,1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(minimapRect);
        int color = gameScreenBuffer.getRaster().getDataBuffer().getElem(0);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;

        //System.out.println("red =" + red + "  green = " + green + "    blue = " + blue);

        if (red == 130 && green == 105 && blue == 70 ){
            scrollToBottom();
            return true;
        }

        return false;
    }


    private static void LookAtBlueBase() throws InterruptedException {

        int minimapTopLeft_x = 1500;
        int minimapTopLeft_y = 50;
        int minimapWidth = 400;
        int minimapHeight = 350;
        int color;
        int red;
        int green;
        int blue;
        int index;
        ArrayList<Integer> blue_x = new ArrayList<Integer>();
        ArrayList<Integer> blue_y = new ArrayList<Integer>();
        int blueIndex = 0;

        Rectangle minimapRect = new Rectangle(minimapTopLeft_x, minimapTopLeft_y, minimapWidth, minimapHeight);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(minimapRect);

        for (int j = 0; j < minimapHeight; j++) {
            for (int i = 0; i < minimapWidth; i++) {
                index = j * minimapWidth + i;
                color = gameScreenBuffer.getRaster().getDataBuffer().getElem(index);
                blue = color & 0xff;
                green = (color & 0xff00) >> 8;
                red = (color & 0xff0000) >> 16;
                //blue
                if (red < 20 && green > 150 && green < 175 && blue > 170 && blue < 190) {
                    blue_x.add(i);
                    blue_y.add(j);
                    blueIndex++;
                }

            }
        }
        int blue_element_x_sum = 0;
        int blue_element_y_sum = 0;
        for (int blue_element = 0; blue_element < blue_x.size(); blue_element++) {
            blue_element_x_sum = blue_x.get(blue_element) + blue_element_x_sum;
            blue_element_y_sum = blue_y.get(blue_element) + blue_element_y_sum;

        }
        if (blue_x.size() > 0) {
            int avg_x = (blue_element_x_sum) / blue_x.size();
            int avg_y = (blue_element_y_sum) / blue_x.size();
            System.out.println("Moving Screen to look at blue's base");
            controller.mouseMove((avg_x + minimapTopLeft_x) + 8, (avg_y + minimapTopLeft_y) - 2);
            leftMouseClick();
        }
    }
    private static void LookAtYellowBase() throws InterruptedException {

        int minimapTopLeft_x = 1500;
        int minimapTopLeft_y = 50;
        int minimapWidth = 400;
        int minimapHeight = 350;
        int color;
        int red;
        int green;
        int blue;
        int index;
        ArrayList<Integer> yellow_x = new ArrayList<Integer>();
        ArrayList<Integer> yellow_y = new ArrayList<Integer>();
        int yellowIndex = 0;

        Rectangle minimapRect = new Rectangle(minimapTopLeft_x, minimapTopLeft_y, minimapWidth, minimapHeight);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(minimapRect);

        for (int j = 0; j < minimapHeight; j++) {
            for (int i = 0; i < minimapWidth; i++) {
                index = j * minimapWidth + i;
                color = gameScreenBuffer.getRaster().getDataBuffer().getElem(index);
                blue = color & 0xff;
                green = (color & 0xff00) >> 8;
                red = (color & 0xff0000) >> 16;
                //blue
                if (red > 180 && red < 200 && green > 180 && green < 200 && blue < 20) {
                    yellow_x.add(i);
                    yellow_y.add(j);
                    yellowIndex++;
                }

            }
        }
        int blue_element_x_sum = 0;
        int blue_element_y_sum = 0;
        for (int blue_element = 0; blue_element < yellow_x.size(); blue_element++) {
            blue_element_x_sum = yellow_x.get(blue_element) + blue_element_x_sum;
            blue_element_y_sum = yellow_y.get(blue_element) + blue_element_y_sum;

        }
        if (yellow_x.size()>0) {
            int avg_x = (blue_element_x_sum) / yellow_x.size();
            int avg_y = (blue_element_y_sum) / yellow_x.size();
            System.out.println("Moving Screen to look at yellow's base");
            controller.mouseMove((avg_x + minimapTopLeft_x) + 8, (avg_y + minimapTopLeft_y) - 2);
            leftMouseClick();
        }
    }

    public static boolean gameCompleted() throws InterruptedException, AWTException, IOException {
        //we're looking at a specific pixel within the M on "Match Completed"
        Rectangle gameCompleteRect = new Rectangle(777, 958, 1, 1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(gameCompleteRect);
        int color = gameScreenBuffer.getRaster().getDataBuffer().getElem(0);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        //System.out.println("red = "+ red + "  green = " + green + " blue = " + blue);
        if (red > 249
                && green > 175 && green < 193
                && blue > 100 && blue < 120) {
            System.out.println("Game has completed");
            return_to_menu();
            Thread.sleep(3000);
            lookForNext1v1(0, false);
            return true;
        }

        return false;
    }


    static void return_to_menu() throws InterruptedException, AWTException {
        Thread.sleep(1000);
        //hit escape key
        System.out.println("Escape key");
        controller.keyPress(VK_ESCAPE);
        controller.keyRelease(VK_ESCAPE);
        Thread.sleep(2000);
        //click Quit
        System.out.println("click Quit");
        controller.mouseMove(950,760);
        Thread.sleep(20);
        leftMouseClick();
        Thread.sleep(1000);
        //click quit
        System.out.println("click Quit");
        controller.mouseMove(835,600);
        Thread.sleep(20);
        leftMouseClick();
        Thread.sleep(5000);
        //click continue
        System.out.println("click continue");
        controller.mouseMove(990,1020);
        Thread.sleep(20);
        leftMouseClick();
        Thread.sleep(4000);
        //click replay/observe
        System.out.println("click replay/observe");
        controller.mouseMove(915,656);
        Thread.sleep(20);
        leftMouseClick();
        Thread.sleep(2000);
        //click observe
        System.out.println("click observe");
        controller.mouseMove(615,231);
        Thread.sleep(20);
        leftMouseClick();

        //wait for the list to populate
        Thread.sleep(10000);
        //scroll to bottom of list
        scrollToBottom();
    }

    static void scrollToBottom() throws InterruptedException, AWTException {
        //scroll to bottom of list
        controller.mouseMove(600,600);
        Thread.sleep(20);
        System.out.println("scroll to bottom of list");
        Robot mouse = new Robot();
        mouse.mouseWheel(100);
        Thread.sleep(1000);
        mouse.mouseWheel(0);


    }
    public static void lookForNext1v1(int iteration, Boolean cycledSearch) throws InterruptedException, AWTException, IOException {
        //78 pixels between entries
        while(true) {
            scrollToBottom();
            Thread.sleep(500);
            System.out.println("Looking for the next match");
            System.out.println("ppl playing");
            int screenRowStart = 857 - (78*iteration);


            for (int screenRow = screenRowStart; screenRow > 360; screenRow = screenRow - 78) {
                Rectangle gameCompleteRect = new Rectangle(346, screenRow, 50, 1);
                BufferedImage gameScreenBuffer = controller.createScreenCapture(gameCompleteRect);
                //System.out.println(gameScreenBuffer);
                int index;
                String[] captionScreen1v1 = new String[50];

                for (index = 0; index < 50; index++) {
                    int color = gameScreenBuffer.getRaster().getDataBuffer().getElem(index);
                    //System.out.println(String.format("0x%08X", color));
                    captionScreen1v1[index] = String.format("0x%08X", color);
                }
                int ED = editDistance(captionScreen1v1, RASTER_caption1v1);
                System.out.println("total edit difference = " + ED);
                ///sensitivity
                if (ED < 200) {
                    controller.mouseMove(346, screenRow);
                    Thread.sleep(300);
                    System.out.println("click on the game");
                    leftMouseClick();

                    //wait for player names to populate
                    Thread.sleep(2000);
                    String textDisplayed = readScreenString(1297,301,188,15);
                    String textPlayerA = textDisplayed.trim();
                    System.out.println("Player   " + textPlayerA);
                    textDisplayed = readScreenString(1297,325,188,15);
                    String textPlayerB = textDisplayed.trim();
                    System.out.println("Player   " + textPlayerB);


                    //we need to figure out if players are cabbage or not here

                    boolean playerMatched = false;
                    for (int i = 0; i < PLAYER_NAMES.length; i++){
                        if (Objects.equals(textPlayerA, PLAYER_NAMES[i])||Objects.equals(textPlayerB, PLAYER_NAMES[i])){
                            playerMatched = true;
                        }
                    }


                    Thread.sleep(100);
                    System.out.println("player matched? = " + playerMatched);
                    //if we don't have a player that matched on first cycle, reset and repeat
                    if (!playerMatched && iteration == 7){
                        System.out.println("did not find good player looking for any playernow");
                        //this is supposed to be true
                        lookForNext1v1(0, false);

                    }
                    if (!playerMatched){
                        iteration = iteration + 1;
                        System.out.println("did not find good player");
                        lookForNext1v1(iteration, false);
                    }

                    //make a text file with the names of the players in them
                    FileWriter myWriter = new FileWriter(".\\PlayerA.txt");
                    myWriter.write(textPlayerA + " vs. " + textPlayerB);
                    myWriter.close();

                    //click the launch button
                    controller.mouseMove(1505, 1002);
                    Thread.sleep(300);
                    System.out.println("click launch button");
                    leftMouseClick();
                    watchGame();
                } else if (inGame()) {
                    watchGame();
                }


            }
            //this needs to go away
            lookForNext1v1(0, false);

            System.out.println("not that many ppl playing");
            for (int screenRow = 813; screenRow > 360; screenRow = screenRow - 78) {
                Rectangle gameCompleteRect = new Rectangle(346, screenRow, 50, 1);
                BufferedImage gameScreenBuffer = controller.createScreenCapture(gameCompleteRect);
                //System.out.println(gameScreenBuffer);
                int index;
                String[] captionScreen1v1 = new String[50];

                for (index = 0; index < 50; index++) {
                    int color = gameScreenBuffer.getRaster().getDataBuffer().getElem(index);
                    //System.out.println(String.format("0x%08X", color));
                    captionScreen1v1[index] = String.format("0x%08X", color);
                }
                int ED = editDistance(captionScreen1v1, RASTER_caption1v1);
                System.out.println("total edit difference = " + ED);
                ///sensitivity
                if (ED < 220) {
                    controller.mouseMove(346, screenRow);
                    Thread.sleep(300);
                    leftMouseClick();
                    Thread.sleep(1500);
                    controller.mouseMove(1505, 1002);
                    Thread.sleep(300);
                    leftMouseClick();
                    watchGame();
                }else{
                   if (inGame()) {
                       watchGame();
                   }
                }
            }
        }
    }

    private static boolean inGame() {
        Rectangle minimapRect = new Rectangle(1898,22,1,1);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(minimapRect);
        int color = gameScreenBuffer.getRaster().getDataBuffer().getElem(0);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        System.out.println("ingame?");
        System.out.println("red =" + red + "  green = " + green + "    blue = " + blue);

        if (red == 153 && green == 127 && blue == 66 ){
            return true;
        }

        return false;
    }

    private static int editDistance(String[] stringsA, String[] stringsB) {
        int total = 0;
        for (int index = 0; index < stringsA.length; index++){
            int difference = minDistance(stringsA[index], stringsB[index] );
            total = total + difference;

        }

        return total;


    }


    /****
    //below is borrowed code
     credits to F
        honestly any comp-sci undergrad
     */
    public static int minDistance(String word1, String word2) {
        int m=word1.length();
        int n=word2.length();
        int[][] mem = new int[m][n];
        for(int[] arr: mem){
            Arrays.fill(arr, -1);
        }
        return calDistance(word1, word2, mem, m-1, n-1);
    }

    private static int calDistance(String word1, String word2, int[][] mem, int i, int j){
        if(i<0){
            return j+1;
        }else if(j<0){
            return i+1;
        }

        if(mem[i][j]!=-1){
            return mem[i][j];
        }

        if(word1.charAt(i)==word2.charAt(j)){
            mem[i][j]=calDistance(word1, word2, mem, i-1, j-1);
        }else{
            int prevMin = Math.min(calDistance(word1, word2, mem, i, j-1), calDistance(word1, word2, mem, i-1, j));
            prevMin = Math.min(prevMin, calDistance(word1, word2, mem, i-1, j-1));
            mem[i][j]=1+prevMin;
        }

        return mem[i][j];
    }

}
