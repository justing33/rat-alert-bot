package GameEngine;

import GameEngine.Builds.ArenaValleyAllies;
import Utilities.Constants;

import java.io.IOException;
import java.util.Random;

import static java.lang.System.exit;

public class Game {

    public static void startSovietGame(Constants.MAP map) throws InterruptedException {
        //TODO: Implement me!
        exit(0);
    }
//BELOW WE DEFINE THE EIGENVALUES IN buildDirection AS {gqscan_x, gqscan_y, mapscroll_x, mapscroll_y}
    public static void startAlliesGame(Constants.MAP_START map) throws InterruptedException, IOException {
        int buildDirection[] = {0,0,0,0};
         if (Constants.MAP_START.TOPLEFT == map){
             buildDirection = new int[] {1,1,1,1};
        } else if (Constants.MAP_START.BOTTOMLEFT == map){
             buildDirection = new int[] {1,-1,1,-1};
        } else if (Constants.MAP_START.TOPRIGHT == map){
             buildDirection = new int[] {-1,1,-1,1};
        } else if (Constants.MAP_START.BOTTOMRIGHT == map){
             buildDirection = new int[] {-1,-1,-1,-1};
         } else if (Constants.MAP_START.BOTTOMMIDLEFT == map){
             buildDirection = new int[] {-1,-1,0,-1};
         } else if (Constants.MAP_START.BOTTOMMIDRIGHT == map){
             buildDirection = new int[] {1,-1,0,-1};
         } else if (Constants.MAP_START.TOPMIDLEFT == map){
             buildDirection = new int[] {-1,1,0,1};
         } else if (Constants.MAP_START.TOPMIDRIGHT == map){
             buildDirection = new int[] {-1,1,0,1};
         } else if (Constants.MAP_START.MIDTOPRIGHT == map){
             buildDirection = new int[] {-1,1,-1,0};
         } else if (Constants.MAP_START.MIDTOPLEFT == map){
             buildDirection = new int[] {1,1,1,0};
         } else if (Constants.MAP_START.MIDBOTTOMLEFT == map){
             buildDirection = new int[] {1,1,1,0};
         } else if (Constants.MAP_START.MIDBOTTOMRIGHT == map){
             buildDirection = new int[] {-1,-1,-1,0};
         } else {
            System.out.println("Could not determine the map, exiting now..");
            exit(0);
        }
        Random buildProbabiliy = new Random();
         int buildStrat = buildProbabiliy.nextInt(9);
         if (buildStrat < 5) {
             System.out.println("Executing 2WF All in");
             ArenaValleyAllies.WFx2SellBuild(buildDirection);
         }else{

             System.out.println("Executing Infantry Build");
             ArenaValleyAllies.InfantryBuild(buildDirection);
         }
    }
}
