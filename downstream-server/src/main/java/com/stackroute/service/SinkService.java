package com.stackroute.service;

import com.stackroute.domain.Activities;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Finance;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface SinkService {
  public void saveActivities(List<Activities> activities);
  public void saveActivitiesFromDomain(List<Domain> activitiesWithDomain);
//  public void saveActivitiesFromDisaster(List<Domain> activitiesWithDomain);
  public List<Domain> retrieveProcessedData(String domainName, Timestamp timestamp, int limit);
  void saveActivitiesFromFinance(List<Finance> financeList);

  int retrieveCountByDomainName(String domainName);
  int retrieveCountByActor(String actor);
}
