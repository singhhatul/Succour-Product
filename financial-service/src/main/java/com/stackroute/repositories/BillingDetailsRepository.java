package com.stackroute.repositories;

import com.stackroute.domain.BillingDetails;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsRepository extends Neo4jRepository<BillingDetails , String> {
}
