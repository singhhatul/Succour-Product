package com.stackroute.domain;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NodeEntity(label = "Ip_address")
public class IPAddress {
    private String ipv4;
    private String ipv6;
    @Id
    private String id;
}
