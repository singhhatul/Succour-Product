package com.stackroute.domain;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@UserDefinedType
//This class contains the fields of the object that is coming from activity object from financial domains.
public class ObjectTypes {
    private String Object;
    private String content;


}
