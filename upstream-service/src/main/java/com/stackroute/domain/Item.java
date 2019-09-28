package com.stackroute.domain;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    private String item_id;
    private String item_name;
    private int item_price;
    private int no_of_items;
    private String parent_id;
//    @Relationship(type = "has_a",direction = Relationship.INCOMING)
    private List<SellerDetails> sellerDetails=new ArrayList<>();
}
