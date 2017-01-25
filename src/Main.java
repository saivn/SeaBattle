/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 13.04.16
 * Time: 9:45
 * To change this template use File | Settings | File Templates.
 */

import model.Field;
import model.Game;
import model.Ship;
import view.FieldsImages;
import view.ShipsImages;


public class Main {


    public static void main(String[] args) throws InterruptedException {

        ShipsImages shipsimages = new ShipsImages();

        Ship.initShips();
        System.out.println("Human");

        Field.printCells(Field.humanField.cells);
        Ship.printAllListPointShipHuman();

        System.out.println("PC");
        Field.printCells(Field.pcField.cells);
        FieldsImages.fieldImages = new FieldsImages();

        Game game = new Game();
        game.mainGame();

        //System.out.println("GameOver, Pridurok!");
    }
}
