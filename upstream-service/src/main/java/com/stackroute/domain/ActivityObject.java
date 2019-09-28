package com.stackroute.domain;

import lombok.*;
//import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//This class contains the two fields of the object of the comming activity.
//the objectType and the content.
public class ActivityObject {
    private String objectType;
    private String content;

}
