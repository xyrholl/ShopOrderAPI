package com.shop.sample.service;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.Member;
import com.shop.sample.domian.Order;
import com.shop.sample.domian.OrderItem;
import com.shop.sample.dto.OrderDTO;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.MemberRepository;
import com.shop.sample.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    private List<OrderItem> createOrderItems(List<OrderItemDTO> orderItemDtos){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDtos) {
            Optional<Item> findItem = itemRepository.findById(orderItemDTO.getItemId());
            if(findItem.isPresent())orderItems.add(OrderItem.createOrderItem(findItem.get(), orderItemDTO.getCount()));
        }
        return orderItems;
    }

    @Transactional
    public void order_v2(OrderDTO orderDTO){
        Member findMember = memberRepository.findById(orderDTO.getMemberId())
            .orElseThrow(() -> new NotFoundDataException("존재하지않는 회원입니다."));
        List<OrderItem> orderItems = createOrderItems(orderDTO.getItemDTOs());
        Order order = Order.createOrder(findMember, orderItems.stream().toArray(OrderItem[]::new));
        orderRepository.save(order);
    }

    @Transactional
    public void order_v1(String memberId, Long itemId, int count){
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundDataException("상품이 존재하지 않습니다."));
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundDataException("존재하지않는 회원입니다."));

        OrderItem orderItem = OrderItem.createOrderItem(findItem, count);
        Order order = Order.createOrder(findMember, orderItem);
        orderRepository.save(order);
    }
    
}
