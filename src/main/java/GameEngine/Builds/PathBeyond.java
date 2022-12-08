package GameEngine.Builds;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import java.io.IOException;

import static Utilities.Constants.*;
import static Utilities.Controller.middleXScreenPos;
import static Utilities.Controller.middleYScreenPos;

public class PathBeyond {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void WFx2SellBuild(int[] attackDirection) throws IOException, InterruptedException {
        System.out.println("attack direction = " + attackDirection[0] + " " + attackDirection[1] + " " + attackDirection[2] + " " + attackDirection[3] + " " + attackDirection[4] + " ");
        System.out.println("Path Beyond, eh better do a 2 war all in");
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

        screenCommands.ScreenCycle(attackDirection, 0);
    }

    public static void InfantryBuild(int[] attackDirection) throws IOException, InterruptedException {
        System.out.println("attack direction = " + attackDirection[0] + " " + attackDirection[1] + " " + attackDirection[2] + " " + attackDirection[3] + " " + attackDirection[4] + " ");
        System.out.println("Path Beyond, Let's try an infantry play");
        buildCommands.deployMCV();
        //Power Plant
        buildCommands.queuePowerPlant();
        Thread.sleep(powerPlantBuildTime);
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        //Barracks
        buildCommands.queueBarracks();
        Thread.sleep(barracksBuildTime);
        buildCommands.buildBarracks(middleXScreenPos, middleYScreenPos);
        buildCommands.buildInfs();
        //Refinery
        buildCommands.queueRefinery();
        Thread.sleep((refineryBuildTime-defendBaseTime)/2);
        screenCommands.defendBase();
        Thread.sleep((refineryBuildTime-defendBaseTime)/2);
        buildCommands.buildRefinery(middleXScreenPos, middleYScreenPos);
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
        buildCommands.queuePillBox();
        screenCommands.defendBase();

        buildCommands.buildInfs();
        buildCommands.buildLightTanks();
        Thread.sleep(pillBuildTime);
        buildCommands.buildPillBox(middleXScreenPos, middleYScreenPos);

        buildCommands.sellConYard();
        //System.out.println("Trying to move THE SCREEN X=> " + attackDirection[0] + " Y=> " + attackDirection[1]);
        screenCommands.moveScreen(attackDirection);
        buildCommands.build99Infs();
        screenCommands.ScreenCycle(attackDirection, 0);
    }
}
