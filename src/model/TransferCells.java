package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TransferCells {
    int xPoint;
    int yPoint;
    int widthCells;
    int heightCells;

    public TransferCells(int xPoint, int yPoint, int widthCells, int heightCells) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.widthCells = widthCells;
        this.heightCells = heightCells;
    }

    //snachalo nagali pressedCells, potom tekushiy ReleasedCells
    public void setFieldInHandPlacingShipReleased(TransferCells pressedCells, int numberShip) {
        int dx = xPoint;
        int dy = yPoint;
        clearNumberListPointShip(numberShip);
        clearPressedCells(pressedCells); //clear ishodnuye cells
        clearListEmptyFieldsHuman();

        if (widthCells > heightCells) {
            for (int i = 0; i < widthCells; i++) {
                setNumberListPointShip(numberShip, dy, dx);
                Field.humanField.cells[dy][dx++] = 'X';
            }
        } else {
            for (int i = 0; i < heightCells; i++) {
                setNumberListPointShip(numberShip, dy, dx);
                Field.humanField.cells[dy++][dx] = 'X';
            }
        }
        setListEmptyFieldsHuman();

        //   System.out.println("stalo ----" + xPoint + " " + yPoint + " w " + widthCells + " h "+heightCells);
        //   Field.printCells(Field.humanField.cells);
        //   Ship.printAllListPointShipHuman();
        //   System.out.println("Spisok pustih POSLE  --");
        //   Field.printListEmptyFieldsHuman();
    }

    void clearPressedCells(TransferCells pressedCells) {
        //clear po horizon
        int dx = pressedCells.xPoint;
        int dy = pressedCells.yPoint;
        if (pressedCells.widthCells > pressedCells.heightCells) {
            for (int i = 0; i < pressedCells.widthCells; i++) {
                Field.humanField.cells[dy][dx++] = '.';
            }
        } else {
            //clear po vertical
            for (int i = 0; i < pressedCells.heightCells; i++) {
                Field.humanField.cells[dy++][dx] = '.';
            }
        }
    }

    void clearNumberListPointShip(int numberShip) {
        ArrayList shipList = Ship.humanShips[numberShip].getListPointShip();
        for (Iterator<Integer> iterator = shipList.iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
    }

    void setNumberListPointShip(int numberShip, int x, int y) {
        ArrayList<Point> listPointShip = Ship.humanShips[numberShip].getListPointShip();
        Point point = new Point();
        point.setLocation(y, x);
        listPointShip.add(point);
    }

    void clearListEmptyFieldsHuman() {
        ArrayList<Point> listEmptyFieldsHuman = Field.listEmptyFieldsHuman;
        for (Iterator<Point> iterator = listEmptyFieldsHuman.iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
    }

    void setListEmptyFieldsHuman() {
        for (int i = 0; i < Field.SIZE_FIELD_X; i++) {
            for (int j = 0; j < Field.SIZE_FIELD_Y; j++) {
                if (Field.humanField.cells[i][j] == '.') {
                    Point point = new Point(i, j);
                    Field.listEmptyFieldsHuman.add(point);
                }
            }
        }
    }
}
