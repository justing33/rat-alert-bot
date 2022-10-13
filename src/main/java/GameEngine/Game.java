package GameEngine;

import GameEngine.Builds.ArenaValleyAllies;

import static java.lang.System.exit;

public class Game {

    public static void startSovietGame(String map) throws InterruptedException {
        //TODO: Implement me!
        exit(0);
    }

    public static void startAlliesGame(String map) throws InterruptedException {
        if ("ARENA VALLEY EXTREME (MEGA)".equals(map)) {
            ArenaValleyAllies.topLeft2WFSellBuild();
        } else if ("BULLSEYE".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("CANYON".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("KEEP OFF THE GRASS".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("NORTH BY NORTHWEST".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("PATH BEYOND".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("TOURNAMENT ARENA".equals(map)){
            //TODO: Implement me!
            exit(0);
        } else if ("TOURNAMENT ORE RIFT".equals(map)) {
            //TODO: Implement me!
            exit(0);
        } else {
            System.out.println("Could not determine the map, exiting now..");
            exit(0);
        }
    }
}
