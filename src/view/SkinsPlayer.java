package view;

import model.Ship;
import view.img.LegendGame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 07.07.16
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class SkinsPlayer extends JFrame {
    enum Skins {Мультяшки, Военные_корабли, Пираты, Космос};
    public String skinsNumber[] = {"Мультяшки", "Военные_корабли",
            "Пираты", "Космос"};

    public final String[] skinsFilePath = {"src/view/img/mult/mult.jpg",
            "src/view/img/moen/voen.jpg",
            "src/view/img/piraty/piraty.jpg",
            "src/view/img/kosmos/kosm.jpg"};

    public static String[] skinsName = {"mult",
            "voen",
            "piraty",
            "kosmos"};

    public static int numberOutputSkins = 0;


    public static void main(String[] args) {
        SkinsPlayer skinsPlayer = new SkinsPlayer();
    }

    public SkinsPlayer() {
        final JFrame frame = new JFrame("Внешний вид игроков");

        System.out.println("Выбор игроков");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 320, 380);
        frame.setLayout(null);

        JButton buttonOk = new JButton("OK");
        buttonOk.setBounds(60, 310, 60, 20);
        frame.add(buttonOk);

        JButton buttonNo = new JButton("Нет");
        buttonNo.setBounds(200, 310, 60, 20);
        frame.add(buttonNo);

        Container pan = createRadioButtonGroup();

        Container contentPane = frame.getContentPane();
        contentPane.add(pan, BorderLayout.WEST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                System.out.println("Ok");
                System.out.println(numberOutputSkins);
                frame.setVisible(false);
                Ship.initShips();                       //vse zanovo  s novoy rasstanovkoy
                FieldsImages.repaintFieldsImage();
                // LegendGame lg =new LegendGame();
                LegendGame.legendGame.ClearLegendGame();
            }
        });

        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                System.out.println("No");
                numberOutputSkins = 0;
                frame.setVisible(false);
            }
        });
    }

    public Container createRadioButtonGroup() {
        final JButton buttonImg = new JButton();

        JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(panel2);

        panel3.setLayout(null);
        Border border = BorderFactory.createTitledBorder("Внешний вид кораблей");
        panel1.setBorder(border);
        panel1.setBounds(2, 2, 280, 100);

        Border border1 = BorderFactory.createTitledBorder("Просмотр");
        scrollPane.setBorder(border1);
        scrollPane.setBounds(2, 100, 280, 190);

        ButtonGroup group = new ButtonGroup();

        for (int i = 0, n = skinsNumber.length; i < n; i++) {

            JRadioButton aRadioButton = new JRadioButton(skinsNumber[i]);
            group.add(aRadioButton);
            panel1.add(aRadioButton);

            aRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String nameSkins = ((JRadioButton) e.getSource()).getText();
                    Skins skins = Skins.valueOf(nameSkins);
                    ImageIcon imc = null;
                    System.out.println(skins);
                    switch (skins) {
                        case Мультяшки:
                            imc = new ImageIcon(ClassLoader.getSystemResource(
                                    skinsFilePath[0]));
                            break;
                        case Военные_корабли:
                            imc = new ImageIcon(ClassLoader.getSystemResource(
                                    skinsFilePath[1]));
                            break;
                        case Пираты:
                            imc = new ImageIcon(ClassLoader.getSystemResource(
                                    skinsFilePath[2]));
                            break;
                        case Космос:
                            imc = new ImageIcon(ClassLoader.getSystemResource(
                                    skinsFilePath[3]));
                            break;
                    }

                    buttonImg.setIcon(imc);
                    panel2.add(buttonImg);
                    scrollPane.setBorder(BorderFactory.createTitledBorder("" + nameSkins));
                    panel2.setBounds(2, 100, 280, 190);

                    numberOutputSkins = Skins.valueOf(nameSkins).ordinal();
                }
            });
        }
        panel3.setBounds(10, 10, 300, 290);
        panel3.add(panel1);
        panel3.add(scrollPane);
        return panel3;
    }
}

