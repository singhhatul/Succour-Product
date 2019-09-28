package com.stackroute.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardDetails {
    private String card_no;
    private String card_name;
    private String card_type;
    private String transaction_id;
}
