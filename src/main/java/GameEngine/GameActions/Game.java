package GameEngine.GameActions;

import GameEngine.Builds.*;
import Utilities.Constants;

import java.io.IOException;
import java.util.Random;

import static java.lang.System.exit;

public class Game {

    public static void startSovietGame(Constants.MAP map) throws InterruptedException {
        //TODO: Implement me!
        exit(0);
    }
//BELOW WE DEFINE THE EIGENVALUES IN buildDirection AS {
// [0] gqscan_x,  direction to look away from when moving screen -x pixels
// [1] gqscan_y,  direction to look away from when moving screen -y pixels
// [2] mapscroll_x, number of pixels to scroll map to guess enemy location at start x pixels
// [3] mapscroll_y, number of pixels to scroll map to guess enemy location at start y pixels
// [4] pixelsscrolloutofcorner, number of pixels to scroll map to guess enemy location at start away from corner xy pixels
// }
    public static void startAlliesGame(Constants.MAP_START map) throws InterruptedException, IOException {
        int[] buildDirection = {1,1,1,1,25};
        Random buildProbabiliy = new Random();
        System.out.println("The Robot thinks we're on " + map);

        if ( Constants.MAP_START.KOTG_BOTTOMRIGHT == map ){
            buildDirection = new int[] {-1,-1,-1,-1, 10};
            DefaultMap.InfantryBuild(buildDirection);
        }else if(Constants.MAP_START.KOTG_TOPLEFT == map){
            buildDirection = new int[] {1,1,1,1, 10};
            DefaultMap.InfantryBuild(buildDirection);
        }else if(Constants.MAP_START.TA_TOPLEFT == map){
            buildDirection = new int[] {1,1,1,1, 20};
            TA.InfantryBuild(buildDirection);
        }else if(Constants.MAP_START.TA_BOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,-1, 20};
            TA.InfantryBuild(buildDirection);
        }else if(Constants.MAP_START.AV_TOPLEFT == map){
            buildDirection = new int[] {1,1,1,1, 40};
            DefaultMap.WFx2SellBuild(buildDirection);
        }else if(Constants.MAP_START.AV_BOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,-1, 40};
            DefaultMap.WFx2SellBuild(buildDirection);
        }else if(Constants.MAP_START.BULLSEYE_TOPRIGHT == map){
            buildDirection = new int[] {-1,1,-1,1, 40};
        }else if(Constants.MAP_START.BULLSEYE_BOTTOMLEFT == map) {
            buildDirection = new int[] {1, -1, 1, -1, 40};
        }else if(Constants.MAP_START.CANYON_TOPRIGHT == map) {
            buildDirection = new int[] {-1,1,-1,1, 11};
            Canyon.WFx2SellBuild(buildDirection);
        }else if(Constants.MAP_START.CANYON_BOTTOMLEFT == map){
            buildDirection = new int[] {1, -1, 1, -1, 11};
            Canyon.InfantryBuild(buildDirection);
        }else if(Constants.MAP_START.ORERIFT_TOPLEFT == map) {
            buildDirection = new int[] {1,1,1,1, 40};
            OreRift.WFx2SellBuild(buildDirection);
        }else if(Constants.MAP_START.ORERIFT_BOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,-1, 40};
            OreRift.WFx2SellBuild(buildDirection);
        }else if(Constants.MAP_START.PATHBEYOND_TOPLEFT == map) {
            buildDirection = new int[] {1,1,1,1, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                PathBeyond.WFx2SellBuild(buildDirection);
            }else{
                PathBeyond.InfantryBuild(buildDirection);
            }
        }else if(Constants.MAP_START.PATHBEYOND_BOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,-1, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                PathBeyond.WFx2SellBuild(buildDirection);
            }else{
                PathBeyond.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_BOTTOMMIDLEFT == map){
            buildDirection = new int[] {1,-1,0,-1, 40};
            DefaultMap.WFx2SellBuild(buildDirection);
        } else if (Constants.MAP_START.NBNW_BOTTOMMIDRIGHT == map){
            buildDirection = new int[] {1,-1,0,-1, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_TOPMIDLEFT == map){
            buildDirection = new int[] {1,1,0,1, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_TOPMIDRIGHT == map){
            buildDirection = new int[] {-1,1,0,1, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_MIDTOPRIGHT == map){
            buildDirection = new int[] {-1,1,-1,0, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_MIDTOPLEFT == map){
            buildDirection = new int[] {1,1,1,0, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_MIDBOTTOMLEFT == map){
            buildDirection = new int[] {1,1,1,0, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.NBNW_MIDBOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,0, 40};
            int buildStrat = buildProbabiliy.nextInt(9);
            if (buildStrat < 5) {
                NBNW.WFx2SellBuild(buildDirection);
            }else{
                NBNW.InfantryBuild(buildDirection);
            }
        } else if (Constants.MAP_START.TOPLEFT == map){
            buildDirection = new int[] {1,1,1,1, 25};
        } else if (Constants.MAP_START.BOTTOMLEFT == map){
            buildDirection = new int[] {1,-1,1,-1, 25};
        } else if (Constants.MAP_START.TOPRIGHT == map){
            buildDirection = new int[] {-1,1,-1,1, 25};
        } else if (Constants.MAP_START.BOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,-1, 25};
        } else if (Constants.MAP_START.BOTTOMMIDLEFT == map){
            buildDirection = new int[] {1,-1,0,-1, 25};
        } else if (Constants.MAP_START.BOTTOMMIDRIGHT == map){
            buildDirection = new int[] {1,-1,0,-1, 25};
        } else if (Constants.MAP_START.TOPMIDLEFT == map){
            buildDirection = new int[] {1,1,0,1, 25};
        } else if (Constants.MAP_START.TOPMIDRIGHT == map){
            buildDirection = new int[] {-1,1,0,1, 25};
        } else if (Constants.MAP_START.MIDTOPRIGHT == map){
            buildDirection = new int[] {-1,1,-1,0, 25};
        } else if (Constants.MAP_START.MIDTOPLEFT == map){
            buildDirection = new int[] {1,1,1,0, 25};
        } else if (Constants.MAP_START.MIDBOTTOMLEFT == map){
            buildDirection = new int[] {1,1,1,0, 25};
        } else if (Constants.MAP_START.MIDBOTTOMRIGHT == map){
            buildDirection = new int[] {-1,-1,-1,0, 25};
        }  else {
            System.out.println("Could not determine the map, exiting now..");
            exit(0);
        }
        System.out.println(map);


        int buildStrat = buildProbabiliy.nextInt(9);

         if (buildStrat < 5) {
             System.out.println("Executing 2WF All in");
             DefaultMap.WFx2SellBuild(buildDirection);
         }else{

             System.out.println("Executing Infantry Build");
             DefaultMap.InfantryBuild(buildDirection);
         }
    }
}
