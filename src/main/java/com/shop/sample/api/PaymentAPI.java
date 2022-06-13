package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sample.dto.APIMessage;
import com.shop.sample.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentAPI {

    private final PaymentService paymentService;

    /**
     * 결제 완료
     */
    @PutMapping("/payment/{orderId}")
    public ResponseEntity<APIMessage> payment(@PathVariable("orderId") Long orderId){
        paymentService.completePymentOrder(orderId);
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .message(orderId + " 결제가 완료되었습니다.")
            .build()
        );
    }
    
}
