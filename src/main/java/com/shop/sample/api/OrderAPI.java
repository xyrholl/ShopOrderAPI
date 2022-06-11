package com.shop.sample.api;

import com.shop.sample.domian.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.sample.model.APIMessage;
import com.shop.sample.model.OrderDTO;
import com.shop.sample.model.OrderItemDTO;
import com.shop.sample.model.Status;
import com.shop.sample.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderAPI {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<APIMessage> info(@SessionAttribute("member") Member member){
        return ResponseEntity
        .ok()
        .body(APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.orders(member))
            .build()
        );
    }

    @PostMapping("/order")
    public ResponseEntity<APIMessage> order(@SessionAttribute("member") Member member, @RequestBody OrderItemDTO... orderItemDTOs){
        return ResponseEntity
        .ok()
        .body(APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.order(member, orderItemDTOs))
            .build()
        );
    }

    @PutMapping("/order/{orderId}")
    public void edit(@PathVariable("orderId") Long orderId, @RequestBody OrderDTO orderDTO){
    }

    @PutMapping("/order/{orderId}/cancel")
    public void orderCancel(@PathVariable("orderId") Long orderId){
        orderService.orderCancel(orderId);
    }

}
