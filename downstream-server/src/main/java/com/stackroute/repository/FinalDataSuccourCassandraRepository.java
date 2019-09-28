package com.stackroute.repository;

import com.stackroute.domain.ActivityObject;
import com.stackroute.domain.Domain;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface FinalDataSuccourCassandraRepository extends ReactiveCassandraRepository<Domain,Integer> {

}
