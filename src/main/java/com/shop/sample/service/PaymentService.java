package com.shop.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.domian.Order;
import com.shop.sample.dto.OrderDTO;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Transactional
    public void completePymentOrder(OrderDTO orderDTO){
        Order order = orderRepository.findById(orderDTO.getId())
            .orElseThrow(() -> new NotFoundDataException());
        order.completePyment();
    }
    
}
