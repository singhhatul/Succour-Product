package com.stackroute.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin("*")
@RestController

public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


//This method is to send data from the backend to the frontend mentioned the endpoint
    @PostMapping("/send")
    public String sendMessage(@RequestBody String s) {
        System.out.println(s);
        simpMessagingTemplate.convertAndSend("/topic/message", s);
        return s;
    }

}
