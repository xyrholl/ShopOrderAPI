package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderAPI {

    @GetMapping("/order")
    public ResponseEntity<?> info(){
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/order")
    public ResponseEntity<?> order(){
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/order/{orderId}")
    public void edit(@PathVariable("orderId") Long orderId){
    }

    @PutMapping("/order/{orderId}/cancel")
    public void orderCancel(@PathVariable("orderId") Long orderId){
    }

}
