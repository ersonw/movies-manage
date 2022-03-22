package com.telebott.moviesmanage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WebSocketData {
    private int code = 0;
    private String message = null;
    private String data = null;

}
