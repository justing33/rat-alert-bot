package GameEngine;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import java.awt.*;

import static Utilities.Controller.readMoneyAmount;

public class Game {

    private Robot controller = new Robot();
    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();
    public Game() throws AWTException {}

    public static void startSovietGame() throws InterruptedException {
        //TODO: Implement me..
    }

    public static void startAlliesGame() throws InterruptedException {
        buildCommands.deployMCV();
        buildCommands.buildPowerPlant();
        buildCommands.buildRefinery();
        buildCommands.buildWarFactory();
        buildCommands.buildLightTanks();
        buildCommands.buildSecondWarFactory();
        buildCommands.sellConYard();
        screenCommands.moveScreenDownRight();




        //TODO: Testing
//        try {
//            readMoneyAmount();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
