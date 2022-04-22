package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "game_records")
@Cacheable
@ToString(includeFieldNames = true)
public class GameRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long uid;
    private long game;
    private long profit;
    private long balance;
    private long valid_bet;
    private long tax;
    private long recordTime;
    private String recordId;
    private String detailUrl;
}
