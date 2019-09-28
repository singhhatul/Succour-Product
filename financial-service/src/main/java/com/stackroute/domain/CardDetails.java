package com.stackroute.domain;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NodeEntity(label = "CardDetails")
public class CardDetails{

    @Id
    private String card_no;
    private String card_name;
    private String card_type;
    private String transaction_id;
}

