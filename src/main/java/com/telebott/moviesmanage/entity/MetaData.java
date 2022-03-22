package com.telebott.moviesmanage.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MetaData {
    private long length2;
    private long length;
    private int fps;
    private long bitrate;
    private long time;
    private String resolution;
}
