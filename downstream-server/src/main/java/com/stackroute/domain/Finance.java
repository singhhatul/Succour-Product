package com.stackroute.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Finance {
    private String id;
    private String actor;
    private String verb;
    ObjectTypes object;
    private double distance;
    private double timeStamp;
    private String message;
}
