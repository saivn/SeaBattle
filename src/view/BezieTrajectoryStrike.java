package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 31.05.16
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class BezieTrajectoryStrike extends JPanel {
    public Point[] arrayCube = new Point[4];
    static Point pointTrajectory = new Point();
    Image img;
    Timer delayOutputOnDisplay;
    BufferedImage image;
    public static boolean flagBezie = false;

    
    public BezieTrajectoryStrike(Point pBegin,
                                 Point pEnd) throws InterruptedException {

        Random random = new Random();

        int px1 = random.nextInt(300);
        int py1 = random.nextInt(300);

        int px2 = random.nextInt(300);
        int py2 = random.nextInt(300);

        arrayCube[0] = new Point((int) pBegin.getX(), (int) pBegin.getY());
        arrayCube[1] = new Point(px1, py1);
        arrayCube[2] = new Point(px2, py2);
        arrayCube[3] = new Point((int) pEnd.getX(), (int) pEnd.getY());

        String fileName = "src/view/img/target-red.png";
        try {
//            img = ImageIO.read(new File(ClassLoader.getSystemResource(
//                    fileName).getFile()));
            img = ImageIO.read(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int factorial(int n) {
        return (n <= 1) ? 1 : n * factorial(n - 1);
    }

    static double getBezierBasis(int i, int n, double t) {
        // Факториал
        // считаем i-й элемент полинома Берштейна
        return (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

    static Point bezier(double t, Point[] arr) {
        double sumX = 0;
        double sumY = 0;

        for (int i = 0; i < arr.length; i++) {
            double b = getBezierBasis(i, arr.length - 1, t);
            sumY += arr[i].getY() * b;
            sumX += arr[i].getX() * b;
        }
        Point point = new Point();
        point.setLocation(sumX, sumY);
        return point;
    }

    public void graphTrajectory() {
        flagBezie = false;
        delayOutputOnDisplay = new Timer(20, new ActionListener() {
            double t = 0;

            public void actionPerformed(ActionEvent e) {
                t += 0.01;
                if (t > 1) {
                    t = 1;
                    delayOutputOnDisplay.stop();
                    flagBezie = true;
                }
                pointTrajectory = bezier(t, arrayCube);
                setBounds(0, 0, 400, 400);
                repaint();
            }

        });
        delayOutputOnDisplay.start();
    }

    // @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) pointTrajectory.getX() + 120, (int) pointTrajectory.getY() + 120, 10, 10); //cleaning the image;
        g.setColor(Color.RED);

        g.fillOval((int) pointTrajectory.getX() + 120, (int) pointTrajectory.getY() + 120, 10, 10);
        repaint();
    }
}
