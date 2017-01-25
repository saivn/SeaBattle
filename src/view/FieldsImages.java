package view;

import control.HController;
import model.Field;
import model.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

//import static view.ShipsImages.shipsImages;


/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 12.05.16
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class FieldsImages extends JFrame implements Serializable {
    public static final int SIZE_FIELDS_IMAGE = 40;
    public static final int SIZE_GAME_PANEL_X = 1200;
    public static final int SIZE_GAME_PANEL_Y = 650;
    public static JLabel jLabel;
    public static JPanel panel1;
    public static JPanel panel2;
    public static JPanel panel4;
    public JPanel panel1Serial;
    public static JButton[][] jb = new JButton[2][100];
    public static int tagSerializable = 0;
    public static Image img;
    public static FieldsImages fieldImages;
    public static Image imgCursor;
    public static Color colorBackPanel1;
    public static JTextArea multi1 = new JTextArea();
    public static JTextArea multi2 = new JTextArea();


    public FieldsImages() throws InterruptedException {

        MenuGame menu = new MenuGame();
        JMenuBar menuBar = menu.createMenuGame();
        setJMenuBar(menuBar);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 5, 10));
        //panel.setOpaque(false);


        panel1 = createPanel(new TitledBorder("Игрок 1"), "One");
        //!!   panel1.setOpaque(false);
        panel1.add(multi1);
        panel.add(panel1);
        //JPanel
        panel2 = createPanel(new TitledBorder("Игрок 2"), "Two");
        panel2.setLayout(null);
        panel2.add(multi2);
        panel.add(panel2);

        panel.setBounds(0, 0, SIZE_GAME_PANEL_X, SIZE_GAME_PANEL_Y);
        setContentPane(panel);

        pack();
        setBounds(0, 0, SIZE_GAME_PANEL_X, SIZE_GAME_PANEL_Y);
    }


    private JPanel createPanel(Border border, String text) throws InterruptedException {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String nameSkins = SkinsPlayer.skinsName[SkinsPlayer.numberOutputSkins];
        String fileName = "src/view/img/" + nameSkins + "/" + "back_" + nameSkins + ".jpg";

        JPanel panel10 = new JPanel();
     //   img = new ImageIcon(ClassLoader.getSystemResource(
      //          fileName)).getImage();
        img = new ImageIcon(fileName).getImage();

        ImagePanel pan10 = new ImagePanel(img);

        panel10.add(pan10);
        panel10.setBounds(0, 0, 550, 550);


        JPanel panel1 = new JPanel();
//        img = new ImageIcon(ClassLoader.getSystemResource(
//                "view/img/numbers.png")).getImage();
        img = new ImageIcon("src/view/img/numbers.png").getImage();

        ImagePanel pan = new ImagePanel(img);
        panel1.add(pan);
        panel1.setBounds(20, 80, 64, 425);


        JPanel panel2 = new JPanel();
//        panel2.add(new ImagePanel(new ImageIcon(
//                ClassLoader.getSystemResource(
//                "view/img/alphavit.jpg")).getImage()));
        panel2.add(new ImagePanel(new ImageIcon(
                "src/view/img/alphavit.jpg").getImage()));

        panel2.setBounds(98, 25, 400, 50);//x,y,w,h   //25


        panel4 = new JPanel();
        panel4.setBounds(95, 95, 404, 400);  //105
        panel4.setLayout(null);
        panel4.setOpaque(false);

        if (text == "One") {
            ShipsComponent component = null;  //

            for (int i = 0; i < Ship.humanShips.length; i++) {
                int deck = Ship.humanShips[i].getLengthShip();
                boolean vertical = Ship.humanShips[i].getVertical();

                fileName = "src/view/img/" + nameSkins + "/" + deck + "deck_" + nameSkins + ".jpg";
//                fileName = "view/img/" + nameSkins + "/" + deck + "deck_" + nameSkins + ".jpg";
                component = new ShipsComponent(fileName, 0);
                component.setName(String.valueOf(i));

                Point point = Ship.humanShips[i].minCoordinate();
                int x = (int) point.getX();
                int y = (int) point.getY();

                System.out.print("human _" + "x " + x + " y " + y + " deck " + deck +
                        " vertical " + Ship.humanShips[i].getVertical() +
                        " course " + Ship.humanShips[i].getCourse());

                for (int j = 0; j < Ship.humanShips[i].getLengthShip(); j++) {

                    System.out.println(Ship.humanShips[i].getListPointShip().get(j));
                }


                System.out.println();
                int widthSh = (vertical) ? SIZE_FIELDS_IMAGE + 2 : (SIZE_FIELDS_IMAGE + 2) * deck;
                int heightSh = (vertical) ? (SIZE_FIELDS_IMAGE + 2) * deck : SIZE_FIELDS_IMAGE + 2;//64 ;;

                if (vertical) {
                    Ship.humanShips[i].setAngle(90);
                    component.setBounds(x * SIZE_FIELDS_IMAGE, y * SIZE_FIELDS_IMAGE, widthSh, heightSh);// 32, 32*deck);//, 120, 120);
                } else {
                    Ship.humanShips[i].setAngle(0);
                    component.setBounds(x * SIZE_FIELDS_IMAGE, y * SIZE_FIELDS_IMAGE, widthSh, heightSh); //32*deck, 32);//, 120, 120);
                }
                panel4.add(component);
            }

            /*  JButton jb2 = new JButton();
            jb2.setText("Случайно расставить");
            jb2.setBounds(340, 80, 160, 20);
            jb2.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Ship.initShips();
                    repaintFieldsImage();
                   // LegendGame lg =new LegendGame();
                    LegendGame.legendGame.ClearLegendGame();

                }
            });
            panel.add(jb2);
            */
        } else {

            //fileName = "view/img/target-red.png";
            fileName = "src/view/img/target-red.png";
            try {
              //  imgCursor = ImageIO.read(new File(ClassLoader.getSystemResource(
              //   fileName).getFile()));
	        imgCursor = ImageIO.read(new File(fileName));
            
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toolkit tk = Toolkit.getDefaultToolkit();
            panel4.setCursor(tk.createCustomCursor(imgCursor, new Point(0, 0), null));

        }

        panel.add(panel4);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(10, 10));
        panel3.setOpaque(false);
        int number = (text == "One") ? 0 : 1;
        for (int i = 0; i < 10 * 10; i++) {
//            jb[number][i] = new JButton(new ImageIcon(
//                     ClassLoader.getSystemResource(
//                     "view/img/kl1.jpg")));
            jb[number][i] = new JButton(new ImageIcon(
                     "src/view/img/kl1.jpg"));

            if (text == "Two") {
                jb[number][i].addMouseListener(new HController());
            }
            panel3.add(jb[number][i]);
        }
        panel3.setBounds(95, 95, 404, 400);//105
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);

        panel.add(panel10);
        return panel;
    }

    public static void repaintFieldsImage() {

        FieldsImages.fieldImages.setVisible(false);
        //!  ShipsImages shipsimages = new ShipsImages();

        //Ship.initShips();
        System.out.println("Human");

        Field.printCells(Field.humanField.cells);
        System.out.println("PC");
        Field.printCells(Field.pcField.cells);

        try {
            FieldsImages.fieldImages = new FieldsImages();
        } catch (InterruptedException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}

class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(
                img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
    }

}


class BackFon extends JPanel {
    public void paintComponent(Graphics g) {
        Image im = null;
        try {
            im = ImageIO.read(new File("src/view/img/kosmos/planeta-kosmos.jpg"));
        } catch (IOException e) {
        }
        //g.drawImage(im, 0, 0, null);
        g.drawImage(im, 0, 0, this);
    }
}
