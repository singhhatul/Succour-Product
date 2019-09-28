package com.stackroute.service;

import com.stackroute.domain.Email;
import com.stackroute.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private EmailRepository emailRepository;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository){
        this.emailRepository=emailRepository;
    }


    @Override
    public Email save(Email recieverMessage) {
        return emailRepository.save(recieverMessage);
    }

    }


