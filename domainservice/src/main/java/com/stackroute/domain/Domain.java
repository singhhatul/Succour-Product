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
    DomainObject object;
    private double sentimentScore;
    private long sentimentTimeStamp;
    private String domainName;

    @Override
    public String toString() {
        return "Domain{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", actor='" + actor + '\'' +
                ", verb='" + verb + '\'' +
                ", object=" + object +
                ", sentimentScore=" + sentimentScore +
                ", sentimentTimeStamp=" + sentimentTimeStamp +
                ", domainName='" + domainName + '\'' +
                '}';
    }
}


