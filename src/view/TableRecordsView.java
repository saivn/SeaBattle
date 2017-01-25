package view;

import model.PersonFromTableRecords;
import model.TableRecords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 15.06.16
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class TableRecordsView extends JFrame {
    public static void windowRegistraction(int amount) {

        //в самом простом виде следующие:
        JFrame form2 = new JFrame("Регистрация");
        form2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form2.setBounds(200, 200, 350, 280);

        GridBagLayout gbl = new GridBagLayout();
        form2.setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;

        String string = "Вы заняли " + amount + " место!\nЧтобы попасть в таблицу рекордов заполните поля";
        JTextArea multi = new JTextArea(string);
        multi.setEditable(false);
        multi.setBackground(form2.getBackground());
        multi.setForeground(Color.black);

        gbl.setConstraints(multi, c);
        form2.add(multi);

        JLabel label = new JLabel("Имя :");
        gbl.setConstraints(label, c);
        form2.add(label);

        final JTextField jtf = new JTextField(10); // Создаем поле для ввода текста 10 символов в ширину
        gbl.setConstraints(jtf, c);
        form2.add(jtf);

        JLabel label1 = new JLabel("Возраст :");
        gbl.setConstraints(label1, c);
        form2.add(label1);

        final JTextField jtf1 = new JTextField(10); // Создаем поле для ввода текста 10 символов в ширину
        final int amountPerson = amount; // Создаем поле для ввода текста 10 символов в ширину
        final JFrame formParent = form2;

        gbl.setConstraints(jtf1, c);
        form2.add(jtf1);

        JButton button = new JButton("OK");
        gbl.setConstraints(button, c);

        form2.add(button);
        form2.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jtf.getText();
                Integer age = Integer.parseInt(jtf1.getText());
                PersonFromTableRecords person = TableRecords.listRecords.get(amountPerson - 1);
                person.setName(name);
                person.setAge(age);
                TableRecords.listRecords.set(amountPerson - 1, person);
                TableRecords.writeTable(TableRecords.listRecords);

                ArrayList<PersonFromTableRecords> list = TableRecords.readTable();
                formParent.setVisible(false);
                viewTable(list);

                for (PersonFromTableRecords p : list)
                    System.out.printf("Имя: %s \t Очки: %d \t Дата: %s \n", p.getName(), p.getAmount(), p.getDate());
            }
        });

    }

    static void viewTable(ArrayList<PersonFromTableRecords> list) {
        JFrame form3 = new JFrame("Таблица рекордов");
        form3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form3.setBounds(200, 200, 350, 280);
        form3.setVisible(true);
        form3.setLayout(null);
        final JFrame formParent = form3;

        String outString = " Имя\t Очки\t Дата\n";
        int cnt = 1;
        for (PersonFromTableRecords p : list) {
            outString += cnt + ". " + p.getName() + "\t" + p.getAmount() + "\t" + p.getDate() + "\n";
            cnt++;
        }

        JTextArea multi = new JTextArea(outString);
        multi.setEditable(false);
        multi.setBackground(form3.getBackground());
        multi.setForeground(Color.black);
        multi.setBounds(10, 30, 340, 270);
        multi.setFont(new Font("", Font.HANGING_BASELINE, 12));

        JButton buttonOk = new JButton("OK");
        buttonOk.setBounds(60, 200, 60, 20);
        form3.add(buttonOk);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                formParent.setVisible(false);
            }
        });

        JButton buttonReset = new JButton("Сброс");
        buttonReset.setBounds(200, 200, 80, 20);
        form3.add(buttonReset);
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                formParent.setVisible(false);
                TableRecords.clearTable();
                ArrayList<PersonFromTableRecords> list = TableRecords.readTable();
                viewTable(list);
            }
        });
        form3.add(multi);
    }

}
