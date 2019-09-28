package com.stackroute.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Activity {
    private String id;
    private long timestamp;
    private String actor;
    private String verb;
    private ActivityObjects object;
    private double sentimentScore;
    private long sentimentTimeStamp;

}