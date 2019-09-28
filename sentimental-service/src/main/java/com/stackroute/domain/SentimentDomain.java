package com.stackroute.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SentimentDomain {
    private String id;
    private long timestamp;
    private String Actor;
    private String verb;
    private SentimentDomainObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;


}
