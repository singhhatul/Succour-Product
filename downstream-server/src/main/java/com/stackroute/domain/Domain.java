package com.stackroute.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Domain {

    private String id;
    private long timestamp;
    private String actor;
    private String verb;
    private ActivityObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;
    private String domainName;
    private LocalDateTime dateTime=LocalDateTime.now();

}


