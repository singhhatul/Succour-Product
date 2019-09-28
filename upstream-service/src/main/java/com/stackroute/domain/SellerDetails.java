package com.stackroute.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SellerDetails {
    private String seller_id;
    private String seller_name;
    private String seller_address;
    private String  seller_email;
    private String seller_phone;
}
