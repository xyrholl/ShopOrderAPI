package kr.co._29cm.homework.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.domian.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderService orderService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void completePaymentOrder(Long orderId){
        Order order = orderService.findOne(orderId);
        order.completePayment();
    }
    
}
