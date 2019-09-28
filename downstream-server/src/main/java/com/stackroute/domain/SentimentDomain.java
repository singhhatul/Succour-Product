package com.stackroute.domain;

import lombok.*;

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
    ActivityObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;


}
