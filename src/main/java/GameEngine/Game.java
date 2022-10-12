package GameEngine;

import GameEngine.Builds.ArenaValleyAllies;
import Utilities.Constants;

import static java.lang.System.exit;

public class Game {

    public static void startSovietGame(Constants.MAP map) throws InterruptedException {
        //TODO: Implement me!
        exit(0);
    }

    public static void startAlliesGame(Constants.MAP map) throws InterruptedException {
        if (Constants.MAP.ARENA_VALLEY_EXTREME_MEGA == map) {
            ArenaValleyAllies.topLeft2WFSellBuild();
        } else if (Constants.MAP.BULLSEYE == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.CANYON == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.KEEP_OFF_THE_GRASS == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.NORTH_BY_NORTHWEST == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.PATH_BEYOND == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.TOURNAMENT_ARENA == map){
            //TODO: Implement me!
            exit(0);
        } else if (Constants.MAP.TOURNAMENT_ORE_RIFT == map) {
            //TODO: Implement me!
            exit(0);
        } else {
            System.out.println("Could not determine the map, exiting now..");
            exit(0);
        }
    }
}
