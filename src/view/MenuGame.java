package view;

import model.Field;
import model.PersonFromTableRecords;
import model.Ship;
import model.TableRecords;
import view.img.LegendGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 21.06.16
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class MenuGame extends JFrame {


    public JMenuBar createMenuGame() {
        final FileDialog fileDialogSave = new FileDialog(this, "Записать как", FileDialog.SAVE);
        final FileDialog fileDialogLoad = new FileDialog(this, "Загрузить", FileDialog.LOAD);

        Font font = new Font("Verdana", Font.PLAIN, 12);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Игра");
        JMenu menuSkins = new JMenu("Настройки");
        JMenu menuRecords = new JMenu("Рекорды");
        JMenu menuHelp = new JMenu("Помощь");
        //---------------menuGame-----------------
        JMenuItem itemGameNew = new JMenuItem("Новая");
        JMenuItem itemGameLoad = new JMenuItem("Загрузить");
        JMenuItem itemGameSave = new JMenuItem("Записать");
        JMenuItem itemGameClose = new JMenuItem("Закрыть");
        menuGame.add(itemGameNew);
        menuGame.add(new JSeparator());
        menuGame.add(itemGameLoad);
        menuGame.add(itemGameSave);
        menuGame.add(new JSeparator());
        menuGame.add(itemGameClose);
        //----------------menuSkins-----------------
        JMenuItem itemSkinsPlayer = new JMenuItem("Внешний вид игроков");
        menuSkins.add(itemSkinsPlayer);
        //---------------menuRecords------------------
        JMenuItem itemTableRecordsOpen = new JMenuItem("Открыть");
        JMenuItem itemTableRecordsClear = new JMenuItem("Очистить");
        menuRecords.add(itemTableRecordsOpen);
        menuRecords.add(itemTableRecordsClear);
        //---------------menuHelp---------------------
        JMenuItem itemMenuHelpDocumens = new JMenuItem("Доки");
        JMenuItem itemMenuHelpAbout = new JMenuItem("Обо мне");
        menuHelp.add(itemMenuHelpDocumens);
        menuHelp.add(itemMenuHelpAbout);
        //--------------------------------------------
        menuBar.add(menuGame);
        menuBar.add(menuSkins);
        menuBar.add(menuRecords);
        menuBar.add(menuHelp);


        itemGameNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Новая");
                Ship.initShips();
                FieldsImages.fieldImages.repaintFieldsImage();
            }
        });


        itemGameLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Загрузить");

                fileDialogLoad.show();
                loadGame(fileDialogLoad);

                FieldsImages.fieldImages.repaintFieldsImage();

                repaintJB(FieldsImages.jb, Field.humanField.cells, 0);
                repaintJB(FieldsImages.jb, Field.pcField.cells, 1);


            }
        });


        itemGameSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Записать");
                fileDialogSave.show();
                saveGame(fileDialogSave);

            }
        });
        itemGameClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                System.exit(0);
            }
        });

        itemTableRecordsOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<PersonFromTableRecords> list = TableRecords.readTable();
                TableRecordsView.viewTable(list);
            }
        });
        itemTableRecordsClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                TableRecords.clearTable();
                ArrayList<PersonFromTableRecords> list = TableRecords.readTable();
                TableRecordsView.viewTable(list);
            }
        });

        itemMenuHelpDocumens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                System.out.println("О пограмме");
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(200, 200, 350, 280);
                frame.setLayout(null);

                String string = "Вы играете в Морской Бой!\n\nПеремещение кораблей:\n\n";
                String string1 = "-вверх\n-вниз\n-вправо\n-влево\n-поворот - правая кнопка мыши";
                JTextArea multi = new JTextArea(string + string1);
                multi.setEditable(false);
                multi.setBackground(frame.getBackground());
                multi.setForeground(Color.black);
                multi.setBounds(10, 10, 200, 150);

                frame.add(multi);
                frame.setVisible(true);

            }
        });

        itemSkinsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.

                SkinsPlayer skinsPlayer = new SkinsPlayer();
            }
        });


        return menuBar;
    }

    void saveGame(FileDialog fileDialog) {
        ObjectOutputStream oos;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(
                    fileDialog.getDirectory() + fileDialog.getFile());

            oos = new ObjectOutputStream(fileOutputStream);
            oos.write(SkinsPlayer.numberOutputSkins);
            oos.write(LegendGame.cntPlayer1);
            oos.write(LegendGame.cntPlayer2);
            oos.writeObject(Field.humanField);
            oos.writeObject(Field.pcField);
            oos.writeObject(Ship.humanShips);
            oos.writeObject(Ship.pcShips);

            oos.flush();
            oos.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    void loadGame(FileDialog fileDialog) {
        ObjectInputStream ois;

        try {
            FileInputStream fileInputStream = new FileInputStream(
                    fileDialog.getDirectory() + fileDialog.getFile());

            //LegendGame lg = new LegendGame();
            ois = new ObjectInputStream(fileInputStream);

            SkinsPlayer.numberOutputSkins = ois.read();
            LegendGame.cntPlayer1 = ois.read();
            LegendGame.cntPlayer2 = ois.read();
            Field.humanField = (Field) ois.readObject();
            Field.pcField = (Field) ois.readObject();
            Ship.humanShips = (Ship[]) ois.readObject();
            Ship.pcShips = (Ship[]) ois.readObject();

            //  FieldsImages.jb = (JButton[][]) ois.readObject();

            ois.close();

        } catch (IOException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }

    }

    void repaintJB(JButton[][] jButtons, char[][] cells, int typeP) {
        int typePl = (typeP == 0) ? 0 : 1;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (cells[i][j] == '*') {
//                    ImageIcon imc = new ImageIcon(ClassLoader.getSystemResource(
//                    "view/img/promah1.jpg"));
                    ImageIcon imc = new ImageIcon("src/view/img/promah1.jpg");

                    jButtons[typePl][i * 10 + j].setIcon(imc);
                }

                if (cells[i][j] == 'O') {
                    ImageIcon imc = new ImageIcon("");
                    jButtons[typePl][i * 10 + j].setBackground(Color.ORANGE);
                    jButtons[typePl][i * 10 + j].setIcon(imc);
                }

                if (cells[i][j] == 'R') {
                    ImageIcon imc = new ImageIcon("");
                    jButtons[typePl][i * 10 + j].setBackground(Color.RED);
                    jButtons[typePl][i * 10 + j].setIcon(imc);
                }
            }
    }


}
