package com.telebott.moviesmanage.data;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class wRecords {
    private long count;
    private boolean hasMore;
    private wRecord list;
}
