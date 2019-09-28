package com.stackroute.repositories;

import com.stackroute.domain.CardDetails;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardDetailsRepository extends Neo4jRepository<CardDetails ,String> {
    @Query("Match(c:CardDetails)-[h:has_transaction]->(t:Transaction) where t.transaction_holder_name={transaction_holder_name} return c")
    List<CardDetails> findCardDetails(@Param("transaction_holder_name") String transaction_holder_name);
}
