package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 07.06.16
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class NameInscription extends JPanel {
    Image img;
    Timer delayOutputOnDisplay;
    public static int alpha = 0;
    public static boolean flagNameInscription = false;

    public static enum NameInscript {Промах, Попал, Утопил};

    public NameInscription(NameInscript nameInscription) {
        String fileName = null;
        switch (nameInscription) {
            case Промах:
                fileName = "src/view/img/ups.jpg";
                break;
            case Попал:
                fileName = "src/view/img/ups.jpg";
                break;
            case Утопил:
                fileName = "src/view/img/vzriv.png.";
                break;
        }

        try {
//            img = ImageIO.read(new File(ClassLoader.getSystemResource(
//                    fileName).getFile()));
            img = ImageIO.read(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeName() {
        flagNameInscription = false;
        delayOutputOnDisplay = new Timer(2, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                alpha += 1;
                System.out.println("aa-" + alpha);
                setBounds(0, 0, 400, 400);
                repaint();

                if (alpha == 100) {
                    delayOutputOnDisplay.stop();
                    flagNameInscription = true;
                }
            }
        });

        delayOutputOnDisplay.start();
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 255, alpha));
        //g.setColor(new Color(255, 255, 255, 255));
        g.drawImage(img, 50, 50, img.getWidth(null), img.getHeight(null), this);
    }
}
