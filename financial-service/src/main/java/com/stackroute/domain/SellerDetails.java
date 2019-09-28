package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NodeEntity(label = "Seller")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",scope=SellerDetails.class)

public class SellerDetails {

    @Id
    private String seller_id;
    private String seller_name;
    private String seller_address;
    private String  seller_email;
    private String seller_phone;
}
