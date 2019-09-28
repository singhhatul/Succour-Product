package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Activities {

    private String id;
    private long timestamp;
    private String actor;
    private String verb;

    private ActivityObject object;

}
