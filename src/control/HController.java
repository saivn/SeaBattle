package control;

import model.Field;
import model.Ship;
import view.FieldsImages;
import view.ShipsImages;
import view.img.LegendGame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 13.05.16
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class HController extends MouseAdapter {

    private static String resultStrikeHuman;
    private static Point strikeHuman;

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getComponent().getX() / FieldsImages.SIZE_FIELDS_IMAGE;
        int y = e.getComponent().getY() / FieldsImages.SIZE_FIELDS_IMAGE;
        System.out.println("Вы стрельнули в ПК __ :" + x + "__" + y);


        setStrikeHuman(new Point(x, y));
        setResultStrikeHuman(Ship.checkDrownShip(Ship.pcShips, getStrikeHuman()));

        System.out.println("Ваш результат:" + getResultStrikeHuman());
        int numberCurrentDrown = Ship.getNumberCurrentDrown();
        Field.markerCellsForStrike(getResultStrikeHuman(), numberCurrentDrown, Field.pcField, y, x, 1);
        ShipsImages.imagesShotInShip(getResultStrikeHuman(), numberCurrentDrown, getStrikeHuman(), 1);
        // LegendGame lg =new LegendGame();
        LegendGame.legendGame.LegendGamePlayer2(getResultStrikeHuman(), Ship.countDrown(Ship.pcShips));
        Field.printCells(Field.pcField.cells);
    }


    public static String getResultStrikeHuman() {
        // if (resultStrikeHuman.equals("Промах")) return Field.NameField.Промах;
        // if (resultStrikeHuman.equals("Попал")) return Field.NameField.Попал;
        // if (resultStrikeHuman.equals("Утопил")) return Field.NameField.Утопил;
        // return null;
        return resultStrikeHuman;
    }

    public void setResultStrikeHuman(String resultStrikeHuman) {
        this.resultStrikeHuman = resultStrikeHuman;
    }

    public static Point getStrikeHuman() {
        return strikeHuman;
    }

    public static void setStrikeHuman(Point strikeHuman) {
        HController.strikeHuman = strikeHuman;
    }

}
