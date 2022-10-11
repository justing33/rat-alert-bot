package GameEngine;

import GameEngine.GameActions.BuildCommands.BuildCommands;

import java.awt.*;

import static Utilities.Controller.readMoneyAmount;

public class Game {

    private Robot controller = new Robot();
    static BuildCommands buildCommands = new BuildCommands();

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
        //TODO: Testing
//        try {
//            readMoneyAmount();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
