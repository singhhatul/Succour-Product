package com.stackroute.repository;

import com.stackroute.domain.Finance;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialFraudRepository extends ReactiveCassandraRepository<Finance,Integer> {
}
