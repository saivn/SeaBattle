package view;

import model.Ship;
import model.TransferCells;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import static model.Field.SIZE_FIELD_X;
import static model.Field.SIZE_FIELD_Y;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 12.05.16
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */

public class ShipsComponent extends JComponent {
    public Image getImage() {
        return image;
    }

    private Image image;
    static TransferCells cellsPressed;
    AffineTransform affine = new AffineTransform();

    public ShipsComponent(String fileName, int i) {

        try {

          //  image = ImageIO.read(new File(ClassLoader.getSystemResource(
          //          fileName).getFile()));
          //  URL ur = ClassLoader.getSystemResource("view/img/mult/4deck_mult.jpg");
          //  File fl = new File(ClassLoader.getSystemResource(
          //          fileName).getFile());
            
           // image = ImageIO.read(new File(ClassLoader.getSystemResource(
           //         fileName).getFile()));
            image = ImageIO.read(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseMotionListener(new MouseMotionListener() {
            Point old;

            public void mouseMoved(MouseEvent e) {
                old = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                Point new_p = e.getPoint();
                Component comp = e.getComponent();
                Point p = comp.getLocation();
                comp.setLocation(new_p.x - (old.x - p.x),
                        new_p.y - (old.y - p.y));
            }
        });


        addMouseListener(new MouseAdapter() { /* ОБРАБОТЧИК СОБЫТИЙ */

            Point oldPosition;

            //otvechaet za ruchnuy rasstonovku korabley
            public void mouseReleased(MouseEvent e) {
                Component comp = e.getComponent();
                System.out.println("mouseReleased");
                Point pp = repaintCells(e);
                comp.setBounds(pp.x, pp.y, comp.getWidth(), comp.getHeight());//novie granici risunka

            }

            public void mousePressed(MouseEvent e)          /* обработчик события простого нажатия */ {
                System.out.println("The button have been pressed");
                Component comp = e.getComponent();
                int x = comp.getX() / FieldsImages.SIZE_FIELDS_IMAGE;
                int y = comp.getY() / FieldsImages.SIZE_FIELDS_IMAGE;
                int width = comp.getWidth() / FieldsImages.SIZE_FIELDS_IMAGE;
                int height = comp.getHeight() / FieldsImages.SIZE_FIELDS_IMAGE;

                cellsPressed = new TransferCells(x, y, width, height); //zapomnili nagatie cells
                //chtob potom perepisat v mausedReleased
                System.out.println("x " + x + " y " + y + " width " + width + " height " + height);
            }

            public void mouseClicked(MouseEvent e) {

                System.out.println("mouseClicked");
                if (SwingUtilities.isRightMouseButton(e)) {
                    Component comp = e.getComponent();
                    String i = comp.getName();
                    int j = Integer.parseInt(i);

                    int widthSh = (Ship.humanShips[j].getVertical()) ? 42 * Ship.humanShips[j].getLengthShip() : 42;
                    int heightSh = (Ship.humanShips[j].getVertical()) ? 42 : 42 * Ship.humanShips[j].getLengthShip();//64 ;;

                    Ship.humanShips[j].setAngle(Ship.humanShips[j].getAngle() + 90);
                    Ship.humanShips[j].isVertical = (Ship.humanShips[j].getVertical() ? false : true);

                    comp.setBounds(comp.getX(), comp.getY(), widthSh, heightSh);// 32, 32*deck);//, 120, 120);

                    if (Ship.humanShips[j].getAngle() >= 360.0) {
                        Ship.humanShips[j].setAngle(0);
                    }
                    Point p = repaintCells(e);
                    repaint();
                }
            }
        });
    }

    public void paintComponent(Graphics gr) {

        gr.setColor(this.getBackground());
        super.paintComponent(gr);
        Graphics2D g2d = (Graphics2D) gr;
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform) (origXform.clone());

        String i = super.getName();
        int j = Integer.parseInt(i);

        int xRot = getWidth() / 2;           //center of rotation is center of the panel
        int yRot = getHeight() / 2;


        newXform.rotate(Math.toRadians(Ship.humanShips[j].getAngle()), xRot, yRot);
        g2d.setTransform(newXform);

        int x = (getWidth() - image.getWidth(null)) / 2; //draw image centered in panel
        int y = (getHeight() - image.getHeight(null)) / 2;

        g2d.drawImage(image, x, y, this);
        g2d.setTransform(origXform);
    }

    static Point repaintCells(MouseEvent e) {
        Component comp = e.getComponent();
        String i = comp.getName();
        int numberHumanShip = Integer.parseInt(i);

        int ex = comp.getX() / FieldsImages.SIZE_FIELDS_IMAGE;
        int ey = comp.getY() / FieldsImages.SIZE_FIELDS_IMAGE;

        int x = ex * FieldsImages.SIZE_FIELDS_IMAGE;
        int y = ey * FieldsImages.SIZE_FIELDS_IMAGE;

        System.out.println("RAZ x " + x + " y " + y + " w " + comp.getWidth() + " num ship " + numberHumanShip);
        //ogranichenie peremechenia po polu kartinok korabley kogda vistavlaem v ruchnuy
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > (FieldsImages.SIZE_FIELDS_IMAGE * SIZE_FIELD_X) - (comp.getWidth() - 2 * 4))
            x = (FieldsImages.SIZE_FIELDS_IMAGE * SIZE_FIELD_X) - (comp.getWidth() - 2 * 4);
        if (y > (FieldsImages.SIZE_FIELDS_IMAGE * SIZE_FIELD_Y) - (comp.getHeight() - 2 * 4))
            y = (FieldsImages.SIZE_FIELDS_IMAGE * SIZE_FIELD_Y) - (comp.getHeight() - 2 * 4);

        System.out.println("DVA  x " + x + " y " + y);
        int x1 = x / FieldsImages.SIZE_FIELDS_IMAGE;
        int y1 = y / FieldsImages.SIZE_FIELDS_IMAGE;

        System.out.println("sss nach koord x1 " + x1 + " y1 " + y1);
        System.out.println("razmer w " + comp.getWidth() / FieldsImages.SIZE_FIELDS_IMAGE +
                " height " + comp.getHeight() / FieldsImages.SIZE_FIELDS_IMAGE);
        int width = comp.getWidth() / FieldsImages.SIZE_FIELDS_IMAGE;
        int height = comp.getHeight() / FieldsImages.SIZE_FIELDS_IMAGE;

        TransferCells cellsReleased = new TransferCells(x1, y1, width, height);

        cellsReleased.setFieldInHandPlacingShipReleased(cellsPressed, numberHumanShip);

        return new Point(x, y);
    }
}
