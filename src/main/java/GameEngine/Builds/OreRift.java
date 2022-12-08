package GameEngine.Builds;

import GameEngine.GameActions.BuildCommands.BuildCommands;
import GameEngine.GameActions.ScreenCommands.ScreenCommands;

import java.io.IOException;

import static Utilities.Constants.*;
import static Utilities.Controller.middleXScreenPos;
import static Utilities.Controller.middleYScreenPos;

public class OreRift {

    static BuildCommands buildCommands = new BuildCommands();
    static ScreenCommands screenCommands = new ScreenCommands();

    public static void WFx2SellBuild(int[] attackDirection) throws IOException, InterruptedException {

        //if bottom left
        int buildRefineryXLocation;
        int buildRefineryYLocation;

        if (attackDirection[0] == 1) {

            buildRefineryXLocation = 538;
            buildRefineryYLocation = 494;

        } else {

            buildRefineryXLocation = middleXScreenPos + 128;
            buildRefineryYLocation = middleYScreenPos;
        }

        System.out.println("Executing 2WF All in");
        buildCommands.deployMCV();
        buildCommands.queuePowerPlant();
        Thread.sleep(powerPlantBuildTime);
        buildCommands.buildPowerPlant(middleXScreenPos, middleYScreenPos);
        buildCommands.queueRefinery();
        Thread.sleep(refineryBuildTime);
        buildCommands.buildRefinery(buildRefineryXLocation, buildRefineryYLocation);
        buildCommands.queueWarFactory();
        Thread.sleep(warFactoryBuildTime/2);
        buildCommands.queueWarFactory();
        Thread.sleep(warFactoryBuildTime/2);
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
        System.out.println("Executing raxWF Build");
        System.out.println("attack direction = " + attackDirection[0] + " " + attackDirection[1] + " " + attackDirection[2] + " " + attackDirection[3] + " " + attackDirection[4] + " ");
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
