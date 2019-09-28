package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
//This class contains the fields of the activity stream with two more fields as id and timestamp.
public class Activities {

    private String id;
    private long timestamp;
    private String actor;
    private String verb;

    private ActivityObject object;

}
