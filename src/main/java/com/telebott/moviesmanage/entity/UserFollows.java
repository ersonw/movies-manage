package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "user_follows")
@Cacheable
@ToString(includeFieldNames = true)
public class UserFollows {
    @Id
    @GeneratedValue
    private long id;
    private long uid;
    private long toUid;
    private long addTime;
}
