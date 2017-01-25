package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 08.04.16
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */

public class Ship implements Serializable {
    public static final int AMOUNT_SHIP = 10;
    public static Ship[] pcShips = new Ship[AMOUNT_SHIP];      //create ships
    public static Ship[] humanShips = new Ship[AMOUNT_SHIP];

    private int lengthShip;
    private int isZeroPointShipX;
    private int isZeroPointShipY;

    private int angle;
    public boolean isVertical;
    private boolean isCourse;
    private int countHit = 0;
    private static int numberCurrentDrown;

    private ArrayList<Point> listPointShip;
    public static final int AMOUNT_TYPE_SHIP = 4;
    public static final int[] AMOUNT_SHIP_FOR_DECK = {1, 2, 3, 4};
    public static final int[] LENGHT_DECK = {4, 3, 2, 1};


    public Ship(int length) {
        setLengthShip(length);
        setVertical();
        setCourse();
        setListPointShip();
        setAngle(0);
    }

    public static void initShips() {

        for (int amountPlayer = 0; amountPlayer < 2; amountPlayer++) {
            if (amountPlayer == 0) {
                Field.humanField = new Field();
            } else {
                Field.pcField = new Field();
            }
            Field field = (amountPlayer == 0) ? Field.humanField : Field.pcField;

            for (int typeShip = 0, amount = 0; typeShip < AMOUNT_TYPE_SHIP; typeShip++) {
                for (int j = 0; j < AMOUNT_SHIP_FOR_DECK[typeShip]; j++) {
                    if (amountPlayer == 0) {
                        Ship.humanShips[amount] = new Ship(LENGHT_DECK[typeShip]);
                        Ship.humanShips[amount++].initShip(amountPlayer, field);

                    } else {
                        Ship.pcShips[amount] = new Ship(LENGHT_DECK[typeShip]);
                        Ship.pcShips[amount++].initShip(amountPlayer, field);
                    }
                }
            }
        }
    }

    public void initShip(int typePlayer, Field field) {
        do {
            setShipRandomPosition();
        } while (!yesSetShip(field, typePlayer));
    }

    void setShipRandomPosition() {
        int Max;
        int Min;
        String course = "";
        String vertical = getVertical() ? "Vertical" : "Horizon";
        if (vertical.equals("Vertical")) {
            course = getCourse() ? "Down" : "Up";
        }
        if (vertical.equals("Horizon")) {
            course = getCourse() ? "Left" : "Right";
        }

        Random random1 = new Random();
        Random random2 = new Random();

        if ((vertical.equals("Vertical")) && (course.equals("Down"))) { //vert & vniz
            Max = Field.SIZE_FIELD_Y - 1;
            setZeroPointShipX(random1.nextInt(Max));
            Max = Field.SIZE_FIELD_Y - lengthShip;
            setZeroPointShipY(random2.nextInt(Max));

        }
        if ((vertical.equals("Vertical")) && (course.equals("Up"))) { //vert & vverh
            Max = Field.SIZE_FIELD_Y - 1;
            setZeroPointShipX(random1.nextInt(Max));
            Min = lengthShip - 1;
            setZeroPointShipY(Min + random2.nextInt(Max - Min + 1));//rnd=[Min,Max]

        }
        if ((vertical.equals("Horizon")) && (course.equals("Left"))) { //gor & left
            Max = Field.SIZE_FIELD_X - 1;
            Min = lengthShip - 1;
            setZeroPointShipX(Min + random2.nextInt(Max - Min + 1));//rnd=[Min,Max]);
            Max = Field.SIZE_FIELD_X - lengthShip;
            setZeroPointShipY(random2.nextInt(Max));
        }
        if ((vertical.equals("Horizon")) && (course.equals("Right"))) { //gor & right
            Max = Field.SIZE_FIELD_X - lengthShip;
            setZeroPointShipX(random1.nextInt(Max));
            Max = Field.SIZE_FIELD_X - 1;
            setZeroPointShipY(random2.nextInt(Max));
        }
    }

    boolean yesSetShip(Field field, int typePlayer) {
        int isIncDec;
        int courseY = 0, courseX = 0;
        int dx, dy, x, y;
        int lengthShip1 = getLengthShip();
        boolean vertical = getVertical();
        boolean course = getCourse();
        if (vertical) {
            isIncDec = (course) ? 1 : -1;  //up||down
            courseY = 1;
            courseX = 0;
        } else {
            isIncDec = (course) ? -1 : 1; //left||right
            courseY = 0;
            courseX = 1;
        }

        int y0 = getZeroPointShipY(); // isZeroPointShipY;
        int x0 = getZeroPointShipX(); // isZeroPointShipX;
        int[] aroundPoint = new int[]{-1, -1, 0, -1, 1, -1, 1, 0, 1, 1, 0, 1, -1, 1, -1, 0,};

        for (int i = 0; i < lengthShip1; i++) {
            if (field.cells[y0][x0] == 'X') return false;
            y = y0 + i * courseY * isIncDec;
            x = x0 + i * courseX * isIncDec;
            for (int j = 0; j < 16; j += 2) {
                dy = y + aroundPoint[j];
                dx = x + aroundPoint[j + 1];
                if ((dx >= 0) && (dx <= 9) && (dy >= 0) && (dy <= 9)) {
                    if (field.cells[dy][dx] == 'X') return false;
                }
            }
        }
        setShip(field, typePlayer);
        return true;
    }

    void setShip(Field field, int typePlayer) {
        int lengthShip1 = getLengthShip();
        Point[] pointsShip = new Point[lengthShip1];
        int isIncDec;
        int courseY = 0, courseX = 0;
        int y1, x1;
        int y0 = getZeroPointShipY();//isZeroPointShipY;
        int x0 = getZeroPointShipX();//isZeroPointShipX;
        boolean vertical = getVertical();
        boolean course = getCourse();

        if (vertical) {
            isIncDec = (course) ? 1 : -1;  //up||down
            courseY = 1;
            courseX = 0;
        } else {
            isIncDec = (course) ? -1 : 1; //left||right
            courseY = 0;
            courseX = 1;
        }

        ArrayList<Point> listPointShip = getListPointShip();
        for (int i = 0; i < lengthShip1; i++) {
            y1 = y0 + i * courseY * isIncDec;
            x1 = x0 + i * courseX * isIncDec;
            field.cells[y1][x1] = 'X';
            Point point = new Point();
            point.setLocation(x1, y1);
            listPointShip.add(point);
        }
    }

    public static boolean drowning(Ship[] ships, String typePlayer) {
        int checkSum = 0;
        for (int i = 0; i < LENGHT_DECK.length; i++) {
            checkSum += LENGHT_DECK[i];
        }
        return (countDrown(ships) == checkSum) ? true : false;
    }

    public static int countDrown(Ship[] ships) {
        int countDrownShips = 0;
        for (int i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            if (ship.getCountHit() >= ship.lengthShip) {
                countDrownShips += 1;
                //  System.out.println("Количество потопленных кораблей у " + typePlayer + "= "
                //         + countDrown + "из " + checkSum);
            }
        }
        return countDrownShips;
    }

    public static String checkDrownShip(Ship[] ships, Point shot) {
        String strCheckDrown = "Промах";
        for (int i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            for (int j = 0; j < ship.getLengthShip(); j++) {
                System.out.println("ListPointShip " + ship.getListPointShip().get(j));
                if (shot.equals(ship.getListPointShip().get(j))) {
                    ship.setCountHit(1);
                    if (ship.getCountHit() >= ship.lengthShip) {
                        strCheckDrown = "Утопил";
                        setNumberCurrentDrown(Ship.pcShips, shot);
                    } else {
                        strCheckDrown = "Попал";
                    }
                }
            }
        }
        return strCheckDrown;
    }

    static void markerAroundDrownedShip(Ship[] ships, Field field, int current) {
        Point[] around = new Point[]{
                new Point(-1, -1),
                new Point(0, -1),
                new Point(1, -1),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1),
                new Point(-1, 1),
                new Point(-1, 0)
        };
        Ship ship = ships[current];
        for (int i = 0; i < ship.getListPointShip().size(); i++) {
            Point pointDrown = ship.getListPointShip().get(i);
            for (int j = 0; j < around.length; j++) {
                int dx = (int) (around[j].getX() + pointDrown.getX());
                int dy = (int) (around[j].getY() + pointDrown.getY());
                if (((dx >= 0.0) && (dx <= 9.0)) &&
                        ((dy >= 0.0) && (dy <= 9.0)) &&
                        (field.cells[dy][dx] != 'O')
                        ) {
                    field.cells[dy][dx] = '*';
                }
            }
        }
    }


    public Point minCoordinate() {
        ArrayList<Point> listPointShip = getListPointShip();
        Point Min = new Point();

        int deck = this.getLengthShip();
        String course = "";
        String vertical = getVertical() ? "Vertical" : "Horizon";
        if (vertical.equals("Vertical")) {
            course = getCourse() ? "Down" : "Up";
        }
        if (vertical.equals("Horizon")) {
            course = getCourse() ? "Left" : "Right";
        }


        if ((vertical.equals("Horizon")) && (course.equals("Left"))) { //gor & left
            Min.setLocation(listPointShip.get(deck - 1).getX(), listPointShip.get(0).getY());
        }
        if ((vertical.equals("Horizon")) && (course.equals("Right"))) { //gor & left
            Min.setLocation(listPointShip.get(0).getX(), listPointShip.get(0).getY());
        }
        if ((vertical.equals("Vertical")) && (course.equals("Down"))) { //vert & vniz
            Min.setLocation(listPointShip.get(0).getX(), listPointShip.get(0).getY());
        }
        if ((vertical.equals("Vertical")) && (course.equals("Up"))) { //vert & up
            Min.setLocation(listPointShip.get(0).getX(), listPointShip.get(deck - 1).getY());
        }

        return Min;
    }

    public static void printAllListPointShipHuman() {
        for (int i = 0; i < humanShips.length; i++) {
            Ship humanShip = humanShips[i];
            System.out.println(humanShip.getListPointShip());
        }

    }
    /* *********************************** */

    public int getZeroPointShipX() {
        return isZeroPointShipX;
    }

    public void setZeroPointShipX(int zeroPointShipX) {
        this.isZeroPointShipX = zeroPointShipX;
    }

    public int getZeroPointShipY() {
        return isZeroPointShipY;
    }

    public void setZeroPointShipY(int zeroPointShipY) {
        this.isZeroPointShipY = zeroPointShipY;
    }

    public int getLengthShip() {
        return lengthShip;
    }

    public void setLengthShip(int lengthShip) {
        this.lengthShip = lengthShip;
    }

    public void setVertical() {
        Random random = new Random();
        this.isVertical = random.nextBoolean();
    }

    public void setCourse() {
        Random random = new Random();
        this.isCourse = random.nextBoolean();
    }

    public boolean getVertical() {
        return this.isVertical;
    }

    public boolean getCourse() {
        return this.isCourse;
    }

    public ArrayList<Point> getListPointShip() {
        return listPointShip;
    }

    public void setListPointShip() {
        this.listPointShip = new ArrayList<Point>();
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setCountHit(int cntHit) {
        countHit += cntHit;
    }

    public int getCountHit() {
        return countHit;
    }

    public static int getNumberCurrentDrown() {
        return numberCurrentDrown;
    }

    public static void setNumberCurrentDrown(Ship[] ships, Point shot) {
        int i = 0;
        int flag = 0;

        for (i = 0; i < ships.length; i++) {
            Ship ship = ships[i];
            for (int j = 0; j < ship.getLengthShip(); j++) {
                if (shot.equals(ship.getListPointShip().get(j))) {
                    flag = 1;
                }
            }
            if (flag == 1) break;
        }
        numberCurrentDrown = i;
    }

}
