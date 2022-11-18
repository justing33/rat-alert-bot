package GameEngine.Builds;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import java.io.IOException;

import static Utilities.Constants.*;
import static Utilities.Controller.middleXScreenPos;
import static Utilities.Controller.middleYScreenPos;

public class ArenaValleyAllies {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void WFx2SellBuild(int[] attackDirection) throws IOException, InterruptedException {
        buildCommands.deployMCV();
        buildCommands.queuePowerPlant();
        Thread.sleep(powerPlantBuildTime);
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        buildCommands.queueRefinery();
        Thread.sleep(refineryBuildTime);
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
        buildCommands.queueWarFactory();
        Thread.sleep(warFactoryBuildTime);
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.queueWarFactory();
        buildCommands.buildLightTanks();
        Thread.sleep((warFactoryBuildTime)/2);
        screenCommands.defendBase();
        Thread.sleep((warFactoryBuildTime/2-defendBaseTime));
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        buildCommands.sellConYard();
        screenCommands.defendBase();
        buildCommands.buildLightTanks();
        //System.out.println("Trying to move THE SCREEN X=> " + attackDirection[0] + " Y=> " + attackDirection[1]);
        screenCommands.moveScreen(attackDirection);

        screenCommands.cursorGQScreen(attackDirection);
    }

    public static void InfantryBuild(int[] attackDirection) throws IOException, InterruptedException {
        buildCommands.deployMCV();
        //Power Plant
        buildCommands.queuePowerPlant();
        Thread.sleep(powerPlantBuildTime);
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        //Refinery
        buildCommands.queueRefinery();
        Thread.sleep(refineryBuildTime);
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
        //Barracks
        buildCommands.queueBarracks();
        Thread.sleep(barracksBuildTime);
        buildCommands.buildBarracks(middleXScreenPos, middleYScreenPos);
        buildCommands.buildInfs();
        //WarFactory
        buildCommands.queueWarFactory();
        Thread.sleep((warFactoryBuildTime-defendBaseTime)/2);
        screenCommands.defendBase();
        Thread.sleep((warFactoryBuildTime-defendBaseTime)/2);
        buildCommands.buildWarFactory(middleXScreenPos, middleYScreenPos);
        //PowerPlant
        buildCommands.queuePowerPlant();
        buildCommands.buildLightTanks();
        screenCommands.defendBase();

        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        //Refinery
        buildCommands.queueRefinery();
        screenCommands.defendBase();
        Thread.sleep(refineryBuildTime-defendBaseTime*2);
        screenCommands.defendBase();
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
        buildCommands.sellConYard();

        buildCommands.buildInfs();
        buildCommands.buildLightTanks();
        screenCommands.defendBase();
        //System.out.println("Trying to move THE SCREEN X=> " + attackDirection[0] + " Y=> " + attackDirection[1]);
        screenCommands.moveScreen(attackDirection);

        screenCommands.cursorGQScreen(attackDirection);
    }
}
