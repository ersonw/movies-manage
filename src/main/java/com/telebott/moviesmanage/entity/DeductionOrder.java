package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "deduction_order")
@Cacheable
@ToString(includeFieldNames = true)
public class DeductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;
    private long uid;
    private String reason;
    private long addTime;
}
