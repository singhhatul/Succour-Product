package com.stackroute.repositories;

import com.stackroute.domain.Item;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends Neo4jRepository<Item,String> {
}
