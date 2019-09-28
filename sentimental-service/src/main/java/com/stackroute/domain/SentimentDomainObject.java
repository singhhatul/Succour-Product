package com.stackroute.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SentimentDomainObject {
    private String objectType;
    private String content;
}
