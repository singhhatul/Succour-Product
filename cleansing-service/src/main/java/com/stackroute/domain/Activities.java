package com.stackroute.domain;

import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Activities {
    private String id;
    private long timestamp;
    private String actor;
    private String verb;
    private ActivityObject object;
}