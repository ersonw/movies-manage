package com.telebott.moviesmanage.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VideoData {
    private long length;
    private long bitrate;
    private String resolution;
}
