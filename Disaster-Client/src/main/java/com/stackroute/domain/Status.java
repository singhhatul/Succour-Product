package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    int NegativeCount = 0;
    int PositiveCount = 0;
    int NeutralCount = 0;
}
