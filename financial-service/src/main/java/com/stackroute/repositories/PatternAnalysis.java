package com.stackroute.repositories;


import com.stackroute.domain.IPAddress;
import com.stackroute.domain.TransactionDetails;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository

public interface PatternAnalysis extends Neo4jRepository<TransactionDetails,String> {

    //The below query is to check if the number of item is greater than 3 and its cost is greater than 10000
    @Query("Match(i:Item)-[c:consists_of]->(t:Transaction) where i.no_of_items>=3 AND i.item_price>=10000 return t ORDER BY t.transaction_id ")
    Collection<TransactionDetails> findByno_of_items(String transaction_holder_name);

    @Query("Match(i:Ip_address)-[is:is_used_for]->(t:Transaction) where t.transaction_id=i.id and t.transaction_holder_name={transaction_holder_name} return t")
    List<TransactionDetails> findTransactions(@Param("transaction_holder_name") String transaction_holder_name);


    @Query("Match(t:transaction) where t.transaction_holder_name={transaction_holder_name} return t")
    List<TransactionDetails> savedDetails(@Param("transaction_holder_name") String transaction_holder_name);

    @Query("Match(ip:Ip_address)-[is:is_used_for]->(t:Transaction) where t.transaction_id=ip.id and t.transaction_holder_name={transaction_holder_name} return ip")
    Collection<IPAddress> findByName(@Param("transaction_holder_name") String transaction_holder_name);
}
