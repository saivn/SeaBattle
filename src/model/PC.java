package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 16.05.16
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class PC {
    private static String resultStrikePC;
    private static Point pointStrike;
    static int cntHit = 0;
    static Point firstPointHit;
    static ArrayList<Point> aroundPoint = new ArrayList<Point>
            (Arrays.asList(new Point(0, -1),
                    new Point(1, 0),
                    new Point(0, 1),
                    new Point(-1, 0)));


    public static Point strike() {
        double dx;
        double dy;
        int sizeListZeroFieldsHuman = Field.listEmptyFieldsHuman.size();
        Field field = Field.humanField;
        Random random = new Random();
        if (cntHit == 0) {
            do {
                int randomIndex = random.nextInt(sizeListZeroFieldsHuman); //vibral random iz ostavshihsa pustih kletok
                dx = Field.listEmptyFieldsHuman.get(randomIndex).getX();
                dy = Field.listEmptyFieldsHuman.get(randomIndex).getY();
                System.out.println("ПК стреляет dx " + dx + " dy " + dy);
                System.out.println("pole " + field.cells[(int) dy][(int) dx]);
                System.out.println(Field.listEmptyFieldsHuman);
            } while ((field.cells[(int) dy][(int) dx] == '*') ||
                    (field.cells[(int) dy][(int) dx] == 'O'));         //strelaem bez povtorov!
            setPointStrike((int) dx, (int) dy);
        } else {
            System.out.println("ПК еще раз стреляет");
            System.out.println("Точка попадания до " + getPointStrike());
            Point point = PC.strikeWhenHit(field);
            dx = point.getX();
            dy = point.getY();
        }

        switch (field.cells[((int) dy)][((int) dx)]) {
            case '.':
                System.out.println("PC - промах");
                //setResultStrikePC(Field.NameField.Промах);
                setResultStrikePC("Промах");
                break;
            case 'X':
                System.out.println("PC - попал");
                //setResultStrikePC(Field.NameField.Попал);
                setResultStrikePC("Попал");
                if (cntHit == 0) {
                    setFirstPointHit(new Point((int) dx, (int) dy));
                    Ship.setNumberCurrentDrown(Ship.humanShips, new Point((int) dx, (int) dy));
                }
                cntHit++;
                Ship.humanShips[0].setCountHit(1);
                setPointStrike((int) dx, (int) dy);
                break;

        }
        Field.markerListZeroFieldsHuman(dy, dx);
        System.out.println("----cells human---------");
        Field.printCells(field.cells);

        return (new Point((int) dx, (int) dy));
    }

    public static Point strikeWhenHit(Field field) {
        double dx = 0;
        double dy = 0;
        int flag = 0;
        Random random = new Random();
        int zeroPointInAroundPointArray = random.nextInt(aroundPoint.size());

        switch (cntHit) {
            case 1:
                do {
                    int i = zeroPointInAroundPointArray;
                    for (int j = 0; j < aroundPoint.size(); j++, i++) {
                        dx = aroundPoint.get(i).getX();
                        dy = aroundPoint.get(i).getY();
                        dx += getPointStrike().getX();
                        dy += getPointStrike().getY();
                        if (((dx >= 0.0) && (dx <= 9.0)) &&
                                ((dy >= 0.0) && (dy <= 9.0)) &&
                                ((field.cells[(int) dy][(int) dx] == '.') ||
                                        (field.cells[(int) dy][(int) dx] == 'X')
                                )
                                ) {
                            System.out.println("Verno!");
                            flag = 1;
                            break;
                        }
                        if (i == 3) i = -1;
                    }
                    if (flag == 0) {
                        setPointStrike((int) getFirstPointHit().getX(), (int) getFirstPointHit().getY());
                    }
                } while (flag == 0);

                break;
            default:
                double xFirstPoint = getFirstPointHit().getX();
                double yFirstPoint = getFirstPointHit().getY();
                double xPointStrike = getPointStrike().getX();
                double yPointStrike = getPointStrike().getY();

                if (xFirstPoint == xPointStrike) {
                    dx = xFirstPoint;
                    if (yFirstPoint - yPointStrike < 0) {
                        dy = yPointStrike + 1;
                        if ((dy > 9) || (Field.humanField.cells[(int) dy][(int) dx] == '*')) dy = yFirstPoint - 1;
                    } else {
                        dy = yPointStrike - 1;
                        if ((dy < 0) || (Field.humanField.cells[(int) dy][(int) dx] == '*')) dy = yFirstPoint + 1;
                    }
                } else {
                    dy = yFirstPoint;
                    if (xFirstPoint - xPointStrike < 0) {
                        dx = xPointStrike + 1;
                        if ((dx > 9) || (Field.humanField.cells[(int) dy][(int) dx] == '*')) dx = xFirstPoint - 1;
                    } else {
                        dx = xPointStrike - 1;
                        if ((dx < 0) || (Field.humanField.cells[(int) dy][(int) dx] == '*')) dx = xFirstPoint + 1;
                    }
                }
        }

        return new Point((int) dx, (int) dy);

    }

    public static void setPointStrike(int x, int y) {
        PC.pointStrike = new Point(x, y);
    }

    public static Point getPointStrike() {
        return pointStrike;
    }

    public static String getResultStrikePC() {
        return resultStrikePC;
    }

    public static void setResultStrikePC(String resultStrikePC) {
        PC.resultStrikePC = resultStrikePC;
    }

    public static Point getFirstPointHit() {
        return firstPointHit;
    }

    public static void setFirstPointHit(Point firstPointHit) {
        PC.firstPointHit = firstPointHit;
    }

}
