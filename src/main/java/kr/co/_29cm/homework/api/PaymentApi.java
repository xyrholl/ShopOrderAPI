package kr.co._29cm.homework.api;

import kr.co._29cm.homework.application.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co._29cm.homework.model.APIMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentApi {

    private final PaymentService paymentService;

    /**
     * 결제 완료
     */
    @PutMapping("/payment/{orderId}")
    public ResponseEntity<APIMessage> payment(@PathVariable("orderId") Long orderId){
        paymentService.completePaymentOrder(orderId);
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .message(orderId + " 결제가 완료되었습니다.")
            .build()
        );
    }
    
}
