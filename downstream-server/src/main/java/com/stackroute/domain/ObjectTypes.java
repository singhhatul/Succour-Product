package com.stackroute.domain;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@UserDefinedType
public class ObjectTypes {
    private String Object;
    private String content;


}
