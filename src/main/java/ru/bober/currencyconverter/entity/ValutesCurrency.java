package ru.bober.currencyconverter.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "Currency")
public class ValutesCurrency {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="valute_id", nullable = false)
    private int valute_id;
    @Column(name = "char_code", nullable = false)
    private String char_code;
    @Column(name = "valutename", nullable = false)
    private String valutename;
    @Column(name = "value", nullable = false)
    private int value;
    @Column(name = "date", nullable = false)
    private Date date;
    public void setDate(String date) throws ParseException {
        String [] splittedDate = date.split("\\.");
        String normalDateString = splittedDate[2] + "-" + splittedDate[1] + "-" + splittedDate[0];
        Date normalDate = new SimpleDateFormat("yyyy-MM-dd").parse(normalDateString);
        java.sql.Date sqlDate = new java.sql.Date(normalDate.getTime());
        this.date = sqlDate;
    }


}
