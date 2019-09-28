package com.stackroute.domain;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Email{
    private String to;
    private String Subject;
    private String text;
}