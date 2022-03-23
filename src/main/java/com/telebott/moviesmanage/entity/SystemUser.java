package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "system_user")
@Cacheable
@ToString(includeFieldNames = true)
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String salt;
    private int role;
    private String introduction;
    private String avatar;
    private String name;
    private long addTime;
    private long updateTime;
    private int status;
    @Transient
    private String token;
}
