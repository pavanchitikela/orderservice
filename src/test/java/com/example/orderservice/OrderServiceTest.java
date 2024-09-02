//package com.example.orderservice;
//
//import com.example.orderservice.service.OrderService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import reactor.core.publisher.Mono;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class OrderServiceTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    public void testPlaceOrderFallback() {
//        Mono<String> response = orderService.placeOrder(123L);
//        String result = response.block();
//        assertEquals("Payment service is currently unavailable, Please try again later", result);
//    }
//}
