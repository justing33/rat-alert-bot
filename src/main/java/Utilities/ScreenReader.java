package Utilities;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenReader {


    public static String readScreenString(int x_coord, int y_coord, int width, int height) throws IOException {


        BufferedImage screenStringCapture = Controller.controller.createScreenCapture(new Rectangle(x_coord,y_coord,width,height));
        int new_width = screenStringCapture.getWidth() * 1;
        int new_height = screenStringCapture.getHeight() * 1;
        BufferedImage screenStringCaptureBW = new BufferedImage(new_width, new_height,
                BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = screenStringCaptureBW.createGraphics();
        //g.drawImage(screenStringCapture, 0, 0, null);

        g.drawImage(screenStringCapture, 0, 0, new_width, new_height, null);


        //g.dispose();


        File outputfile = new File(".\\writing.png");
        ImageIO.write(screenStringCaptureBW, "png", outputfile);


        File imageFile = new File(".\\writing.png");
        ITesseract instance = new Tesseract();
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }


        return result;



    }
}