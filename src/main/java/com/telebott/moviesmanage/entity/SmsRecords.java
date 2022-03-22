package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "sms_records")
@ToString(includeFieldNames = true)
@Setter
@Getter
public class SmsRecords {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String number;
    private long ctime;
    private int status;
    private String data;
}
