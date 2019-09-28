package com.stackroute.repository;

import com.stackroute.domain.Activities;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SuccourCassandraRepository extends ReactiveCassandraRepository<Activities, Integer> {


//    @AllowFiltering
//    Flux<Activities> findActivityByDomain(String name);
}
