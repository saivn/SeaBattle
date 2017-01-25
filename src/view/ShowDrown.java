package view;

import model.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 01.07.16
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class ShowDrown extends Thread {
    public void run() {

        int numberCurrentDrown = Ship.getNumberCurrentDrown();
        Ship ship = Ship.pcShips[numberCurrentDrown];
        for (int j = 0; j < ship.getLengthShip(); j++) {
            for (int i = 0; i < 10; i++) {
                try {
                    sleep(10);
                    ImageIcon imc = rescaleImage("src/view/img/vzriv.png", 10 + i * 5, 10 + i * 5);

                    Point point = ship.getListPointShip().get(j);
                    int pointShift = point.y * 10 + point.x;

                    FieldsImages.jb[1][pointShift].setBackground(Color.black);
                    FieldsImages.jb[1][pointShift].setIcon(imc);


                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        try {
            sleep(1000);
            for (int j = 0; j < ship.getLengthShip(); j++) {
                for (int i = 10; i > 0; i--) {
                    try {
                        sleep(1000);
                        Point point = ship.getListPointShip().get(j);
                        int pointShift = point.y * 10 + point.x;

                        ImageIcon imc = rescaleImage("src/view/img/vzriv.png", 10 + i * 5, 10 + i * 5);
                        FieldsImages.jb[1][pointShift].setBackground(Color.black);
                        FieldsImages.jb[1][pointShift].setIcon(imc);

                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }

            for (int j = 0; j < ship.getLengthShip(); j++) {
                Point point = ship.getListPointShip().get(j);
                int pointShift = point.y * 10 + point.x;

                ImageIcon imc = new ImageIcon("");
                FieldsImages.jb[1][pointShift].setIcon(imc);
                FieldsImages.jb[1][pointShift].setBackground(Color.red);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public ImageIcon rescaleImage(String source, int maxHeight, int maxWidth) {
        int newHeight = 0, newWidth = 0;        // Variables for the new height and width
        int priorHeight = 0, priorWidth = 0;
        BufferedImage image = null;
        ImageIcon sizeImage;

        try {
//            image = ImageIO.read(new File(ClassLoader.getSystemResource(
//                    source).getFile()));  // get the image
            image = ImageIO.read(new File(source));  // get the image

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Picture upload attempted & failed");
        }

        sizeImage = new ImageIcon(image);

        if (sizeImage != null) {
            priorHeight = sizeImage.getIconHeight();
            priorWidth = sizeImage.getIconWidth();
        }

        // Calculate the correct new height and width
        if ((float) priorHeight / (float) priorWidth > (float) maxHeight / (float) maxWidth) {
            newHeight = maxHeight;
            newWidth = (int) (((float) priorWidth / (float) priorHeight) * (float) newHeight);
        } else {
            newWidth = maxWidth;
            newHeight = (int) (((float) priorHeight / (float) priorWidth) * (float) newWidth);
        }

        // Resize the image
        // 1. Create a new Buffered Image and Graphic2D object
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();

        // 2. Use the Graphic object to draw a new image to the image in the buffer
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, newWidth, newHeight, null);
        // g2.dispose();

        // 3. Convert the buffered image into an ImageIcon for return
        return (new ImageIcon(resizedImg));
    }
}
