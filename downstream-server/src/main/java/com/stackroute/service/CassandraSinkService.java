package com.stackroute.service;

import com.stackroute.domain.Activities;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Finance;
import com.stackroute.repository.DomainRepository;
import com.stackroute.repository.FinalDataSuccourCassandraRepository;
import com.stackroute.repository.FinancialFraudRepository;
import com.stackroute.repository.SuccourCassandraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CassandraSinkService implements SinkService {


    SuccourCassandraRepository succourCassandraRepository;
    FinalDataSuccourCassandraRepository finalDataSuccourCassandraRepository;
    DomainRepository domainRepository;
    FinancialFraudRepository financialFraudRepository;
    @Autowired
    public CassandraSinkService(SuccourCassandraRepository succourCassandraRepository,FinalDataSuccourCassandraRepository finalDataSuccourCassandraRepository, DomainRepository domainRepository,FinancialFraudRepository financialFraudRepository){
      this.succourCassandraRepository=succourCassandraRepository;
      this.finalDataSuccourCassandraRepository=finalDataSuccourCassandraRepository;
      this.domainRepository = domainRepository;
      this.financialFraudRepository=financialFraudRepository;
    }

    @Override
    public void saveActivities(List<Activities> activities) {
         Flux<Activities> demoFlux=succourCassandraRepository.saveAll(activities);
         demoFlux.subscribe();
    }

    @Override
    public void saveActivitiesFromDomain(List<Domain> activitiesWithDomain) {
        Flux<Domain> domainFlux=finalDataSuccourCassandraRepository.saveAll(activitiesWithDomain);
        domainFlux.subscribe();
//        finalDataSuccourCassandraRepository.saveToAnotherTable(null,"@CGI_Global",null,"Legal",null,1.0,"1568697300771","1568697300771","post");
    }

    @Override
    public List<Domain> retrieveProcessedData(String domainName, Timestamp timestamp, int limit) {
        log.debug("domainName: "+domainName);
        log.debug("domainName: "+timestamp);
        log.debug("limit: "+limit);
        return domainRepository.findActivityByDomain(domainName,timestamp, limit);
    }

    @Override
    public void saveActivitiesFromFinance(List<Finance> financeList) {
        Flux<Finance> domainFlux=financialFraudRepository.saveAll(financeList);
        domainFlux.subscribe();
    }

    @Override
    public int retrieveCountByDomainName(String domainName) {
        return this.domainRepository.findCountOfTweetsByDomainName(domainName);
    }

    @Override
    public int retrieveCountByActor(String actor) {
        return this.domainRepository.findCountOfTweetsByActor(actor);
    }


//
//    public Flux<Employe> getAllEmployees() {
//        Flux<Employe> employees =  employeeRepository.findAll();
//        return employees;
//    }

//    public Flux<Employe> getEmployeesFilterByAge(int age) {
//        return employeeRepository.findByAgeGreaterThan(age);
//    }
//
//    public Mono<Employe> getEmployeeById(int id) {
//        return employeeRepository.findById(id);
//    }
}
