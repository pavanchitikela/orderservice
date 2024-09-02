package com.example.orderservice.service;//package com.example.orderservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @CircuitBreaker(name = "paymentServiceCircuitBreaker", fallbackMethod = "fallbackResponse")
    public Mono<String> callExternalService(Long orderId) {

        Mono<String> a = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:9090/pay")
                    .bodyValue(orderId)
                    .retrieve()
                    .bodyToMono(String.class);
        logger.info(String.valueOf(a));
        return a;
    }

    /**
     * Fallback method called when the circuit breaker is open.
     * @param ex Exception thrown
     * @return Fallback response
     */

    public Mono<String> fallbackResponse(Long orderId, Throwable ex) {
        logger.warn("Fallback called for order ID: {} due to: {}", orderId, ex.getMessage());
        return Mono.just("Payment service is currently unavailable, Please try again later");
    }

}