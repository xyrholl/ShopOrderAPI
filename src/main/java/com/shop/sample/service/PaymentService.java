package com.shop.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.domian.Order;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Transactional
    public void completePymentOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundDataException());
        order.completePyment();
    }
    
}
