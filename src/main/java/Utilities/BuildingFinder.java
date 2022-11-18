package Utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static Utilities.Constants.*;
import static Utilities.Constants.commandInputBufferTime;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_X;

public class BuildingFinder {



    //this is looking for a building to shoot
    public static void Look_for_Building() throws IOException, InterruptedException {
        Boolean buildingWasShot = false;

        for (int Colum_Num = 120; Colum_Num < PLAYABLE_SCREEN_WIDTH_1920x1080 - 120;  Colum_Num = Colum_Num+149){
            BufferedImage game_colum = captureGameScreenColum(Colum_Num);
            for (int Row_Num = 1; Row_Num < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 128; Row_Num = Row_Num + 4){
                int numberOfGreenPixels = 0;
                int color = game_colum.getRaster().getDataBuffer().getElem(Row_Num);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                if (red < 20 && green > 240 && blue < 20) {
                    //System.out.println("Found GREEN at x = " + Colum_Num + " y = " + Row_Num);
                    BufferedImage game_row = captureGameScreenRow(Colum_Num-120,Row_Num);
                    for (int j = 1; j < 240; j++){
                        color = game_row.getRaster().getDataBuffer().getElem(j);
                        blue = color & 0xff;
                        green = (color & 0xff00) >> 8;
                        red = (color & 0xff0000) >> 16;
                        if (red < 20 && green > 240 && blue < 20) {
                            numberOfGreenPixels = numberOfGreenPixels + 1;
                            if (numberOfGreenPixels > 90){
                                //we want to shoot the building near the left side so we can see white pop up if it is our building
                                //System.out.println("Found Building at x = " + Colum_Num + "y = " + Row_Num);
                                shootBuilding(Colum_Num - 120 + j - 64,Row_Num+75);
                                Thread.sleep(commandInputBufferTime);
                                controller.keyPress(VK_X);
                                controller.keyRelease(VK_X);
                                Thread.sleep(commandInputBufferTime);
                                //gaurd
                                controller.keyPress(VK_G);
                                controller.keyRelease(VK_G);
                                Thread.sleep(commandInputBufferTime);
                                buildingWasShot = true;
                                break;
                            }

                        }else{
                            numberOfGreenPixels = 0;
                        }
                    }

                }
                if (buildingWasShot){
                    break;
                }

            }
            if (buildingWasShot){
                break;
            }

        }

    }






}
