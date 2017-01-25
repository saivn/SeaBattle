package model; /**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 13.04.16
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field implements Serializable {
    //dla ruchnoy rasstonovki
    public char[][] cells;
    static List<String> rowTitleCells = Arrays.asList("А", "Б", "В", "Г", "Д", "Е",
            "Ж", "З", "И", "К");
    public static final int SIZE_FIELD_X = 10;
    public static final int SIZE_FIELD_Y = 10;

    public static Field pcField;
    public static Field humanField;
    public static ArrayList<Point> listEmptyFieldsHuman = new ArrayList<Point>();

    public static enum NameField {Промах, Попал, Утопил}

    ;

    public Field() {
        cells = new char[SIZE_FIELD_X][SIZE_FIELD_Y];
        for (int i = 0; i < SIZE_FIELD_X; i++) {
            for (int j = 0; j < SIZE_FIELD_Y; j++) {
                cells[i][j] = '.';
                Point point = new Point(i, j);
                listEmptyFieldsHuman.add(point);
            }
        }
    }

    public static void printListEmptyFieldsHuman() {
        for (int i = 0; i < listEmptyFieldsHuman.size(); i++) {
            System.out.println(listEmptyFieldsHuman.get(i));
        }

    }

    public static void printCells(char[][] cells) {
        int maxI = SIZE_FIELD_X; //getSizeFieldX();
        int maxJ = SIZE_FIELD_Y; //getSizeFieldY();

        System.out.printf(" \t");
        for (int i = 0; i < rowTitleCells.size(); i++) {
            System.out.printf("%s ", rowTitleCells.get(i));
        }
        System.out.println();
        for (int i = 0; i < maxI; i++) {
            System.out.printf("%d\t", i + 1);
            for (int j = 0; j < maxJ; j++) {
                System.out.printf("%s ", cells[i][j]);
            }
            System.out.println();
        }
        System.out.println("$$$$$");
    }

    //for rc strike only zero cells
    public static void markerListZeroFieldsHuman(double dy, double dx) {
        int length = listEmptyFieldsHuman.size();
        int indexDeleting = 0;

        for (int i = 0; i < length; i++) {
            if ((listEmptyFieldsHuman.get(i).getX() == dx) &&
                    (listEmptyFieldsHuman.get(i).getY() == dy)
                    ) {
                indexDeleting = i;
                break;
            }
        }
        listEmptyFieldsHuman.remove(indexDeleting);
    }

    public static void markerCellsForStrike(String resultStrike, int numberCurrentDrown,
                                            Field field, int dy, int dx, int typeP) {

        Ship ship = (typeP == 0) ? Ship.humanShips[numberCurrentDrown] : Ship.pcShips[numberCurrentDrown];

        if (resultStrike.equals("Промах")) {
            field.cells[dy][dx] = '*';
        }
        if (resultStrike.equals("Попал")) {
            field.cells[dy][dx] = 'O';
        }
        if (resultStrike.equals("Утопил")) {
            for (int i = 0; i < ship.getLengthShip(); i++) {
                Point point = ship.getListPointShip().get(i);
                field.cells[point.y][point.x] = 'R';
            }
        }

    }

}


