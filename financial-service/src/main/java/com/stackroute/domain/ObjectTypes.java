package com.stackroute.domain;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectTypes {
    private String Object;
    private String content;


}
