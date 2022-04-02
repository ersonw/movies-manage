package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "show_pay")
@ToString(includeFieldNames = true)
@Setter
@Getter
public class ShowPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String domain;
    private String mchId;
    private String callbackUrl;
    private String notifyUrl;
    private String errorUrl;
    private String secretKey;
    private long ctime;
    private long utime;
}
