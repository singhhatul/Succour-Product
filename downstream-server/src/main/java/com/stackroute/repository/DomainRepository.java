package com.stackroute.repository;

import com.stackroute.domain.Domain;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.util.List;

public interface DomainRepository extends CassandraRepository<Domain,Integer> {
    @Query("Select * from domain where actor =  ?0 and datetime >= ?1 LIMIT ?2 ALLOW FILTERING;")
    public List<Domain> findActivityByDomain(String domainname, Timestamp datetime, int limit);

    @Query("select count(*) from domain where domainname = ?0 ALLOW FILTERING;")
    public int findCountOfTweetsByDomainName(String domainname);

    @Query("select count(*) from domain where actor = ?0 ALLOW FILTERING;")
    public int findCountOfTweetsByActor(String actor);

}
