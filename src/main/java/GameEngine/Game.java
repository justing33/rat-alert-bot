package GameEngine;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import static Utilities.Constants.MCV_VERT_SIZE;
import static Utilities.Controller.*;
import static java.lang.System.exit;

public class Game {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void startSovietGame() throws InterruptedException {
        //TODO: Implement me..
        exit(0);
    }

    public static void startAlliesGame() throws InterruptedException {
        buildCommands.deployMCV();
        buildCommands.buildPowerPlant(middleXScreenPos + 64, middleYScreenPos + (MCV_VERT_SIZE / 2));
        buildCommands.buildRefinery(middleXScreenPos + 256, middleYScreenPos + (MCV_VERT_SIZE / 2));
        buildCommands.buildWarFactory(middleXScreenPos + 256, middleYScreenPos + (MCV_VERT_SIZE / 2) - 320);
        buildCommands.buildLightTanks();
        buildCommands.buildWarFactory(middleXScreenPos + 512, middleYScreenPos + (MCV_VERT_SIZE / 2) - 192);
        buildCommands.sellConYard();
        screenCommands.moveScreenDownRight();

    }
}
