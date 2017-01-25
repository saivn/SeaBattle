package model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: sasha
 * Date: 15.06.16
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class PersonFromTableRecords implements Serializable {
    private String name;
    private Integer age;
    private Integer amount;
    private String date;

    public PersonFromTableRecords(String name, Integer age, Integer amount) {
        this.name = name;
        this.amount = amount;
        this.age = age;

        Calendar calendar = Calendar.getInstance();
        String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) +
                "/" + String.valueOf(calendar.get(Calendar.MONTH)) +
                "/" + String.valueOf(calendar.get(Calendar.YEAR));

        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
