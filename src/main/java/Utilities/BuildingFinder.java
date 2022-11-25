package Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Utilities.Constants.*;
import static Utilities.Constants.commandInputBufferTime;
import static Utilities.Controller.*;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_X;

public class BuildingFinder {

    public static int Look_for_the_Blackness() throws IOException {
        int numberOfBlackPixels = 0;
        for (int Colum_Num = 150; Colum_Num < PLAYABLE_SCREEN_WIDTH_1920x1080 - 120;  Colum_Num = Colum_Num+149) {
            BufferedImage game_colum = captureGameScreenColum(Colum_Num);
            //only need to check every fourth pixel
            for (int Row_Num = 1; Row_Num < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 128; Row_Num = Row_Num + 4) {
                int color = game_colum.getRaster().getDataBuffer().getElem(Row_Num);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                if (red < 10 && green < 10 && blue < 10){
                    numberOfBlackPixels++;
                }

            }

        }
        System.out.println("number of Black Pixels:" + numberOfBlackPixels);
        return numberOfBlackPixels;
    }

    //this is looking for a building to shoot
    public static List Look_for_Building() throws IOException, InterruptedException {
       List<Integer> Building_Locations = new ArrayList<>();
        for (int Colum_Num = 150; Colum_Num < PLAYABLE_SCREEN_WIDTH_1920x1080 - 120;  Colum_Num = Colum_Num+149){
            BufferedImage game_colum = captureGameScreenColum(Colum_Num);
            //only need to check every fourth pixel due to heighth of the health bar
            for (int Row_Num = 1; Row_Num < PLAYABLE_SCREEN_HEIGHT_1920x1080 - 128; Row_Num = Row_Num + 4){
                int numberOfGreenPixels = 0;
                int color = game_colum.getRaster().getDataBuffer().getElem(Row_Num);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                if (red < 20 && green > 240 && blue < 20) {
                    //System.out.println("Found GREEN at x = " + Colum_Num + " y = " + Row_Num);
                    BufferedImage game_row = captureGameScreenRowBelow(Colum_Num-150,Row_Num, 299);
                    for (int j = 1; j < 299; j++){
                        color = game_row.getRaster().getDataBuffer().getElem(j);
                        blue = color & 0xff;
                        green = (color & 0xff00) >> 8;
                        red = (color & 0xff0000) >> 16;
                        if (red < 20 && green > 240 && blue < 20) {
                            numberOfGreenPixels = numberOfGreenPixels + 1;
                            if (numberOfGreenPixels > 90){
                                //we want to shoot the building near the left side so we can see white pop up if it is our building
                                //System.out.println("Found Building at x = " + Colum_Num + "y = " + Row_Num);
                                Building_Locations.add(Colum_Num-150+j-90);
                                Building_Locations.add(Row_Num);
                                //shootBuilding(Colum_Num - 120 + j - 64,Row_Num+75);
                                numberOfGreenPixels = 0;
                                Capture_Building_Image(Colum_Num-150+j-90,Row_Num);


                            }
                        }else{
                            numberOfGreenPixels = 0;
                        }
                    }

                }

            }

        }
        System.out.println("found building at:" + Building_Locations);
    return Building_Locations;
    }

    private static void Capture_Building_Image(int cursor_x,int cursor_y) throws IOException {

        //Grab the screen near the cursor
        Rectangle playableScreenRect = new Rectangle(cursor_x,cursor_y,128, 128);
        BufferedImage gameScreenBuffer = controller.createScreenCapture(playableScreenRect);
        // Save as JPEG
        File file = new File("mybuildingimage.jpg");
        ImageIO.write(gameScreenBuffer, "jpg", file);

    }


}
