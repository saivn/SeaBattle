package model;

import view.TableRecordsView;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 15.06.16
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 * <p/>
 * readTable
 * writeTable
 * sortTable
 * wathPlace
 * Играем, добавляемся в таблицу.Сортируем таблицу по очкам. Оставляем только 5 записей.
 * Определяем на каком месте наша игра по очкам (может вообще нет)
 * По очкам это значит у победителя берем количество выстрелов и сравниваем с таблицей рекордов
 */
public class TableRecords {
    public static ArrayList<PersonFromTableRecords> listRecords = new ArrayList();
    private static String fileName = "src/model/dat/tablerecords.dat";
    public static final int TABLE_RECORDS_SIZE = 5;

    public static ArrayList<PersonFromTableRecords> readTable() {
        ArrayList<PersonFromTableRecords> listRecords = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            listRecords = (ArrayList<PersonFromTableRecords>) ois.readObject();
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            return new ArrayList<PersonFromTableRecords>();
        }
        return listRecords;
    }


    public static void writeTable(ArrayList<PersonFromTableRecords> listRecords) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(listRecords);
            System.out.println("Запись произведена");
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static void clearTable() {
        try {
            FileWriter fstream1 = new FileWriter(fileName);
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }

        for (int i = 0; i < listRecords.size(); i++) {
            listRecords.remove(0);
        }
    }


    public static void sortRecords(ArrayList<PersonFromTableRecords> listRecords) {
        Collections.sort(listRecords, new Comparator<PersonFromTableRecords>() {
            @Override
            public int compare(PersonFromTableRecords o1, PersonFromTableRecords o2) {
                return o1.getAmount().compareTo(o2.getAmount());
            }
        });

        if (listRecords.size() > TABLE_RECORDS_SIZE) {
            listRecords.remove(TABLE_RECORDS_SIZE);
        }

    }

    public static int whatPlace(PersonFromTableRecords person, ArrayList<PersonFromTableRecords> listRecords) {
        for (int i = 0; i < listRecords.size(); i++) {
            if (person.getAmount() == listRecords.get(i).getAmount()) {
                return i + 1;
            }
        }
        return 0;
    }

    public static void manager(int amount) {

        PersonFromTableRecords personNew = new PersonFromTableRecords("", 0, amount);
        listRecords = TableRecords.readTable();
        listRecords.add(personNew);
        TableRecords.sortRecords(listRecords);
        int place = TableRecords.whatPlace(personNew, listRecords);
        if (place != 0) {
            System.out.println("Вы заняли " + place + " в таблице рекордов");
            TableRecordsView.windowRegistraction(place);
        }
    }
}



