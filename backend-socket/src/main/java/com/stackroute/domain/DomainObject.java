package com.stackroute.domain;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//This class contains the fields of the object that is coming from activity object.
public class DomainObject {
    private String objectType;
    private String content;
}
