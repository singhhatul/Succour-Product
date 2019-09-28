package com.stackroute.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.stereotype.Component;
/*
lombok performed.
* */
@Data
//Default constructor
@NoArgsConstructor
//parameterized constructor
@AllArgsConstructor
//toString()
//@ToString
@Getter
/*
getter and setter methods
*/
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = MailDto.class)
@Component
public class MailDto
{
    private String to;
    private String subject;
    private String body;
    private String username;

//    public static String setBody(String str) {
//        str="this is the sentimental score";
//        return str;
//    }
}
