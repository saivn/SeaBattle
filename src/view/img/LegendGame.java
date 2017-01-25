package view.img;

import view.FieldsImages;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 03.06.16
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class LegendGame extends JPanel implements Runnable {
    public static Image image;
    public static int alpha;
    public static int cntPlayer1;
    public static int cntPlayer2;
    public static LegendGame legendGame = new LegendGame();

    public LegendGame() {
    }

    ;

    public void ClearLegendGame() {
        cntPlayer1 = -1;
        cntPlayer2 = -1;
        LegendGamePlayer1("", 0);
        LegendGamePlayer2("", 0);
        ;
    }

    public void LegendGamePlayer1(String nameStatus, int cntDrowingShips) {
        Color color = FieldsImages.panel1.getBackground();
        FieldsImages.multi1.setBackground(color);
        FieldsImages.multi1.setBounds(120, 555, 460, 40);
        FieldsImages.multi1.setFont(new Font("", Font.BOLD, 12));

        String text = "";
        cntPlayer1++;
        text = "Выстрелов : " + cntPlayer1 + "      Статус: " + nameStatus + "\nКоличество затопленных кораблей :" + cntDrowingShips;

        FieldsImages.multi1.setText(" ");
        FieldsImages.multi1.append(text);
        FieldsImages.panel1.add(FieldsImages.multi1);
    }

    public void LegendGamePlayer2(String nameStatus, int cntDrowingShips) {
        Color color = FieldsImages.panel1.getBackground();
        FieldsImages.multi2.setBackground(color);
        FieldsImages.multi2.setBounds(120, 555, 460, 40);
        FieldsImages.multi2.setFont(new Font("", Font.BOLD, 12));

        String text = "";
        cntPlayer2++;
        text = "Выстрелов : " + cntPlayer2 + "      Статус: " + nameStatus + "\nКоличество затопленных кораблей :" + cntDrowingShips;

        FieldsImages.multi2.setText(" ");
        FieldsImages.multi2.append(text);
        FieldsImages.panel2.add(FieldsImages.multi2);

    }


    public void run() {
        //for (int i = 0; i < 255; i++) {
        for (int i = 0; i < 25; i++) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception ex) {
            }

            alpha++;
            FieldsImages.jLabel.setBounds(200, 520, 160, 20);
            FieldsImages.jLabel.setText("Уе!");
            FieldsImages.jLabel.setForeground(new Color(0, 255, 0, LegendGame.alpha));
            FieldsImages.panel2.add(FieldsImages.jLabel);

            //  repaint();
        }
        alpha = 0;

        System.out.println("++++RRR+++++");
    }
}

class MyImage extends JComponent {
    MyImage() {
        try {
            //Dimension d=getToolkit().getScreenSize();
            Robot r = new Robot();
            LegendGame.image = r.createScreenCapture(
                    //  new Rectangle(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height, 200, 100));
                    new Rectangle(600 - 150, 300 - 150, 200, 100));
            //       new Rectangle(FieldsImages.panel4.getX(),FieldsImages.panel4.getY(),
            //               FieldsImages.panel4.getWidth(),FieldsImages.panel4.getHeight()));
            //                  200,200));
        } catch (Exception ex) {
        }
    }
}

