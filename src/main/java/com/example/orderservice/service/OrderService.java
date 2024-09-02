package com.example.orderservice.service;//package com.example.orderservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
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
        // Simulating a random failure
        //throw new RuntimeException("Service failed");
//        Mono<String> a = null;
//        try {


        Mono<String> a = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:9090/pay")
                    .bodyValue(orderId)
                    .retrieve()
                    .bodyToMono(String.class);
//        }catch(Exception e){
//            System.out.println("############");
//            throw new RuntimeException("Service failed");
//        }
        logger.info(String.valueOf(a));
        return a;
    }

    /**
     * Fallback method called when the circuit breaker is open.
     * @param ex Exception thrown
     * @return Fallback response
     */

    public Mono<String> fallbackResponse(Long orderId, Throwable ex){
        //logger.warn("Fallback called for order ID: {} due to: {}", orderId, ex.getMessage());
        return Mono.just("Payment service is currently unavailable, Please try again later");
    }




}
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class OrderService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
//
//    private RestTemplate restTemplate;
//
//    public OrderService() {
//        this.restTemplate = new RestTemplate();
//    }
//
//    @CircuitBreaker(name = "paymentServiceCircuitBreaker", fallbackMethod = "fallbackResponse")
//    public String callExternalService(Long orderId) {
//        // Set headers if needed (optional)
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Prepare the request entity (including headers and body)
//        HttpEntity<Long> requestEntity = new HttpEntity<>(orderId, headers);
//
//        // Make the POST request
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:9090/pay",
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
//
//        // Return the response body
//        return response.getBody();
//    }
//
//    /**
//     * Fallback method called when the circuit breaker is open or an exception is thrown.
//     * @param orderId The order ID that was passed to the original method.
//     * @param ex The exception that caused the fallback to be triggered.
//     * @return A fallback response indicating the service is unavailable.
//     */
//    public String fallbackResponse(Long orderId, Throwable ex) {
//        logger.warn("Fallback called for order ID: {} due to: {}", orderId, ex.getMessage());
//        return "Payment service is currently unavailable, please try again later.";
//    }
//}
