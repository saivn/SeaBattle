package model;

import control.HController;
import view.BezieTrajectoryStrike;
import view.FieldsImages;
import view.ShipsImages;
import view.ShowDrown;
import view.img.LegendGame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 13.05.16
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class Game {

//    private static String fileName = "src/model/dat/gameInf.sav";

    public static void mainGame() throws InterruptedException {
        int flagEndGame = 0;

        String whoWinner;
        HController hController = new HController();

        PC.setResultStrikePC("Промах");
        PC.setPointStrike(0, 0);
        PC.cntHit = 0;

        do {

            System.out.println("----Стреляет человек--------");
            hController.setResultStrikeHuman("Попал");
            FieldsImages.panel4.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(FieldsImages.imgCursor, new Point(0, 0), null));
            do {

                if (Ship.drowning(Ship.pcShips, "PC")) {
                    whoWinner = "Human";
                    System.out.println(whoWinner);
                    JOptionPane.showMessageDialog(null, "Победил человек!");
                    flagEndGame = 1;
                    break;
                }

                if (hController.getResultStrikeHuman().equals("Утопил")) {
                    Thread mTread = new ShowDrown();
                    mTread.start();
                    if (mTread.isAlive()) {

                        System.out.println("ZZZZZZZZZZZZZZZZZ");
                        hController.setResultStrikeHuman("Попал");
                    }
                    /*   NameInscription.alpha=55;
                    NameInscription nameInscription = new NameInscription(NameInscription.NameInscript.Утопил);
                    FieldsImages.panel2.add(nameInscription);
                    nameInscription.writeName();
                    while (!NameInscription.flagNameInscription) ;
                    NameInscription.flagNameInscription=false;
                    */
                }

            } while (hController.getResultStrikeHuman().equals("Попал") ||
                    hController.getResultStrikeHuman().equals("Утопил")
                    );
            FieldsImages.panel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Field.printCells(Field.humanField.cells);

            //PC
            System.out.println("----Стреляет ПК--------");


            if (flagEndGame == 0)
                do {
                    Point currentPointStrike = PC.strike();                               //strelaet  PC
                    System.out.println("Результат выстрела ПК" + PC.getResultStrikePC());
                    int numberCurrentDrown = Ship.getNumberCurrentDrown();
                    Color color = new Color(196, 103, 8);
                    if (Ship.humanShips[numberCurrentDrown].getLengthShip() == PC.cntHit) {
                        PC.setResultStrikePC("Утопил");
                        Ship.markerAroundDrownedShip(Ship.humanShips, Field.humanField, numberCurrentDrown);
                        PC.cntHit = 0;
                        int set = Ship.humanShips[numberCurrentDrown].getLengthShip();
                        Ship.humanShips[numberCurrentDrown].setCountHit(set);
                        color = new Color(230, 11, 11);
                        //String rez = PC.getResultStrikePC();
                        //NameInscription nameInscription = new NameInscription(NameInscription.NameInscript.Утопил);
                        // nameInscription.writeName();

                    }
                    Field.markerCellsForStrike(PC.getResultStrikePC(), numberCurrentDrown,
                            Field.humanField, currentPointStrike.y, currentPointStrike.x, 0);

                    FieldsImages.fieldImages.repaint();

                    System.out.println("po nammm");
                    Field.printCells(Field.humanField.cells);

                    Point point = new Point(10, 10);
                    System.out.println("Trajectory ------->" + currentPointStrike);
                    Point point1 = new Point();
                    point1.setLocation(currentPointStrike.getX() * 40, currentPointStrike.getY() * 40);
                    BezieTrajectoryStrike trajectoryStrike = new BezieTrajectoryStrike(point, point1);
                    FieldsImages.panel1.add(trajectoryStrike);
                    trajectoryStrike.graphTrajectory();
                    while (!BezieTrajectoryStrike.flagBezie) ;
                    // LegendGame lg =new LegendGame();
                    LegendGame.legendGame.LegendGamePlayer1(PC.getResultStrikePC(), Ship.countDrown(Ship.humanShips));

                    BezieTrajectoryStrike.flagBezie = false;
                    ShipsImages.imagesShotInShip(PC.getResultStrikePC(), numberCurrentDrown, currentPointStrike, 0);

                    if (Ship.drowning(Ship.humanShips, "PC")) {
                        whoWinner = "PC";
                        System.out.println("Победил " + whoWinner);
                        JOptionPane.showMessageDialog(null, "Победил ПК!");
                        flagEndGame = 1;
                        break;
                    }

                } while (PC.getResultStrikePC().equals("Попал") ||
                        PC.getResultStrikePC().equals("Утопил")
                        );

        } while (flagEndGame == 0);

        TableRecords.manager(LegendGame.cntPlayer2);

    }
}
