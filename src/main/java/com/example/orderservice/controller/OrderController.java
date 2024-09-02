package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderService orderServ;

    @Autowired
    public OrderController(OrderService orderServ){
        this.orderServ = orderServ;
    }

    @GetMapping
    public String getOrders(){
        return "Orders";
    }

    @GetMapping("/test")
    public Mono<String> testCircuitBreaker(@RequestBody Long id) {
        return orderServ.callExternalService(id);
    }

}
