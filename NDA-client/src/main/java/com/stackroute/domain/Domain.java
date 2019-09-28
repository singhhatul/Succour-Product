package com.stackroute.domain;
import lombok.*;
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
    private DomainObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;
    private String domainName;
}