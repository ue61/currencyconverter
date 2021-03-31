package ru.bober.currencyconverter.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;
    @Column(name = "user_name")
    private String name;
    @Column (name = "in_valute")
    private String inValute;
    @Column (name = "out_valute")
    private String outValute;
    @Column (name = "in_value")
    private int inValue;
    @Column (name = "out_value")
    private int outValue;
    @Column (name = "currency")
    private float currency;
    @Column (name = "date")
    private Date date;

}
