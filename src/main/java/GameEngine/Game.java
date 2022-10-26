package GameEngine;

import GameEngine.Builds.ArenaValleyAllies;
import Utilities.Constants;

import static java.lang.System.exit;

public class Game {

    public static void startSovietGame(Constants.MAP map) throws InterruptedException {
        //TODO: Implement me!
        exit(0);
    }

    public static void startAlliesGame(Constants.MAP_START map) throws InterruptedException {
        int buildDirection[];
         if (Constants.MAP_START.TOPLEFT == map){
             buildDirection = new int[] {1,1};

            ArenaValleyAllies.topLeft2WFSellBuild(buildDirection);
            exit(0);
        } else if (Constants.MAP_START.BOTTOMLEFT == map){
             buildDirection = new int[] {1,-1};

             ArenaValleyAllies.topLeft2WFSellBuild(buildDirection);
            exit(0);
        } else if (Constants.MAP_START.TOPRIGHT == map){
             buildDirection = new int[] {-1,1};

             ArenaValleyAllies.topLeft2WFSellBuild(buildDirection);
            exit(0);
        } else if (Constants.MAP_START.BOTTOMRIGHT == map){
             buildDirection = new int[] {-1,-1};

             ArenaValleyAllies.topLeft2WFSellBuild(buildDirection);
            exit(0);
        } else {
            System.out.println("Could not determine the map, exiting now..");
            exit(0);
        }
    }
}
