package GameEngine.Builds;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import static Utilities.Constants.MCV_VERT_SIZE;
import static Utilities.Constants.commandTextedInputBufferTime;
import static Utilities.Controller.middleXScreenPos;
import static Utilities.Controller.middleYScreenPos;

public class ArenaValleyAllies {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void topLeft2WFSellBuild(){
        buildCommands.deployMCV();
        buildCommands.buildPowerPlant(middleXScreenPos + 100, middleYScreenPos + (MCV_VERT_SIZE / 2));
        buildCommands.buildRefinery(middleXScreenPos + 290, middleYScreenPos + (MCV_VERT_SIZE / 2) + 100);
        buildCommands.buildWarFactory(middleXScreenPos + 550, middleYScreenPos + (MCV_VERT_SIZE / 2) + 100);
        buildCommands.buildLightTanks();
        buildCommands.buildWarFactory(middleXScreenPos + 550, middleYScreenPos + (MCV_VERT_SIZE / 2) - 60);
        buildCommands.sellConYard();
        screenCommands.waitForTanks();
        screenCommands.moveScreenDownRight();
        screenCommands.cursorGQScreen();
    }
}
