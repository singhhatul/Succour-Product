package com.stackroute.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//This class contains all the fields of the activity object that are produced to the respective kafka topics

public class Domain {
    private String id;
    private long timestamp;
    private String actor;
    private String verb;
    private DomainObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;
    private String domainName;

}