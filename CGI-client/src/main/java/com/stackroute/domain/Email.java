package com.stackroute.domain;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Email {
    public String to;
    public String Subject;
    public String text;

}