package com.stackroute.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//This class contains all the fields of the object coming from various financial information sources.
public class Finance {
    private String id;
    private String actor;
    private String verb;
    ObjectTypes object;
    private double distance;
    private double timeStamp;
    private String message;
}
