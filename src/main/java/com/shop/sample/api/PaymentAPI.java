package com.shop.sample.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentAPI {

    @PutMapping("/payment/{orderId}")
    public void payment(@PathVariable("orderId") Long orderId){
    }
    
}
