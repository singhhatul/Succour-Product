package com.stackroute.domain;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@UserDefinedType
public class ActivityObject {
    private String objectType;
    private String content;

}
