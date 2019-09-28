package com.stackroute.repository;

import com.stackroute.domain.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<Email,String> {
    // List<AlertMail> save(AlertMail alertMail);

}
