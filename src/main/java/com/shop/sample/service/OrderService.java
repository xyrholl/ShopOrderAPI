package com.shop.sample.service;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.Member;
import com.shop.sample.domian.Order;
import com.shop.sample.domian.OrderItem;
import com.shop.sample.exception.ItemNotFoundException;
import com.shop.sample.exception.MemberNotFoundException;
import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.MemberRepository;
import com.shop.sample.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void order(String memberId, Long itemId, int count){
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품이 존재하지 않습니다."));
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지않는 회원입니다."));

        OrderItem orderItem = OrderItem.createOrderItem(findItem, count);
        Order order =Order.createOrder(findMember, orderItem);
        orderRepository.save(order);
    }

    
}
