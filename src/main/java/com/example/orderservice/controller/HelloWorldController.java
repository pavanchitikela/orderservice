package com.example.orderservice.controller;

import com.example.orderservice.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    KafkaProducerService producerService;

    @GetMapping("/publish")
    public String HelloWorld(@RequestParam("message") String message){
        producerService.send(message);
        return "message sent successfully";
    }
}
