package GameEngine.Builds;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import java.io.IOException;

import static Utilities.Constants.MCV_VERT_SIZE;
import static Utilities.Constants.commandTextedInputBufferTime;
import static Utilities.Controller.middleXScreenPos;
import static Utilities.Controller.middleYScreenPos;

public class ArenaValleyAllies {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void topLeft2WFSellBuild(int[] attackDirection) throws IOException, InterruptedException {
        buildCommands.deployMCV();
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.buildLightTanks();
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.sellConYard();
        buildCommands.buildLightTanks();
        screenCommands.waitForTanks();
        System.out.println("Trying to move THE SCREEN X=> " + attackDirection[0] + " Y=> " + attackDirection[1]);
        screenCommands.moveScreen(attackDirection);

        screenCommands.cursorGQScreen(attackDirection);
    }
}
