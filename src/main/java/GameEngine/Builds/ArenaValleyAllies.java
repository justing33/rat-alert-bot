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
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.buildLightTanks();
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.sellConYard();
        screenCommands.waitForTanks();
        screenCommands.moveScreenDownRight();
        screenCommands.cursorGQScreen();
    }
}
