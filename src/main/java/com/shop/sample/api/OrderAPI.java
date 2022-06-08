package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sample.dto.APIMessage;
import com.shop.sample.dto.OrderDTO;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.dto.Status;
import com.shop.sample.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderAPI {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<APIMessage> info(){
        return ResponseEntity
        .ok()
        .body(APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.orders())
            .build()
        );
    }

    @PostMapping("/order")
    public ResponseEntity<APIMessage> order(@RequestBody OrderItemDTO... orderItemDTOs){
        return ResponseEntity
        .ok()
        .body(
            null
        );
    }

    @PutMapping("/order/{orderId}")
    public void edit(@PathVariable("orderId") Long orderId){
    }

    @PutMapping("/order/{orderId}/cancel")
    public void orderCancel(@PathVariable("orderId") Long orderId){
    }

}
