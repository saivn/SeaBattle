package view;

import model.Ship;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 12.05.16
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
        
public class ShipsImages{

  //  boolean isVertical;
   // int deck;
  //  int angle;
   // static ArrayList<ShipsImages> shipsImagesHuman = new ArrayList<ShipsImages>();
  //  static ArrayList<ShipsImages> shipsImagesPC= new ArrayList<ShipsImages>();

  public static void imagesShotInShip(String resultStrike, int numberCurrentDrown, Point point,
                                        int typePlayer){
      int dx = point.x ;
      int dy = point.y*10;
      int numbCell= dx+dy;
      int typeP = (typePlayer==0)? 0:1;

      Ship ship = (typeP==0)? Ship.humanShips[numberCurrentDrown] : Ship.pcShips[numberCurrentDrown];

      System.out.println("asd--"+ point);
      System.out.println("asd->" + resultStrike);
      /*ImageIcon imc;

      switch (resultStrike){
          case Промах:
              imc= new ImageIcon("src/view/img/promah1.jpg");
              FieldsImages.jb[typeP][numbCell].setIcon(imc);
              break;
          case Попал:
              imc= new ImageIcon("");
              FieldsImages.jb[typeP][numbCell].setBackground(Color.ORANGE);
              FieldsImages.jb[typeP][numbCell].setIcon(imc);
              break;
          case Утопил:
              System.out.println("текущий "+numberCurrentDrown);
              for (int i = 0; i < ship.getLengthShip(); i++) {
                  point=ship.getListPointShip().get(i);

                  System.out.print("точка "+point);
                  dx = point.x;
                  dy = point.y*10;
                  numbCell= dx+dy;

                  imc= new ImageIcon("");
                  FieldsImages.jb[typeP][numbCell].setBackground(Color.RED);
                  FieldsImages.jb[typeP][numbCell].setIcon(imc);
              }
              break;
      }
      */

      if (resultStrike.equals("Промах")){
//        ImageIcon imc= new ImageIcon(ClassLoader.getSystemResource(
//                "view/img/promah1.jpg"));
        ImageIcon imc= new ImageIcon("src/view/img/promah1.jpg");

        FieldsImages.jb[typeP][numbCell].setIcon(imc);
      }
      if (resultStrike.equals("Попал")){
        ImageIcon imc= new ImageIcon("");
        FieldsImages.jb[typeP][numbCell].setBackground(Color.ORANGE);
        FieldsImages.jb[typeP][numbCell].setIcon(imc);
      }
      if (resultStrike.equals("Утопил")){
          System.out.println("текущий "+numberCurrentDrown);
          for (int i = 0; i < ship.getLengthShip(); i++) {
               point=ship.getListPointShip().get(i);

               System.out.print("точка "+point);
               dx = point.x;
               dy = point.y*10;
               numbCell= dx+dy;

               ImageIcon imc= new ImageIcon("");
               FieldsImages.jb[typeP][numbCell].setBackground(Color.RED);
               FieldsImages.jb[typeP][numbCell].setIcon(imc);
          }
      }

  }

}
