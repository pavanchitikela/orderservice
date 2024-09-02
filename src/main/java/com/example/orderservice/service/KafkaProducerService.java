package com.example.orderservice.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        for(int i =0; i<100000; i++){
            kafkaTemplate.send("new-topic", message+i);
        }

    }
}
