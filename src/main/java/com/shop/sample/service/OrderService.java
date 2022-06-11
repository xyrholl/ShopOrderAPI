package com.shop.sample.service;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.Member;
import com.shop.sample.domian.Order;
import com.shop.sample.domian.OrderItem;
import com.shop.sample.model.OrderDTO;
import com.shop.sample.model.OrderItemDTO;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    private List<OrderItem> createOrderItems(OrderItemDTO... orderItemDTOs){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            Optional<Item> findItem = itemRepository.findById(orderItemDTO.getItemId());
            findItem.ifPresent(item -> orderItems.add(OrderItem.createOrderItem(item, orderItemDTO.getCount())));
        }
        return orderItems;
    }

    private Order findOne(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> new NotFoundDataException("존재하지 않는 주문입니다."));
    }

    // 주문 조회
    public List<OrderDTO> orders(Member member){
        List<Order> orders = orderRepository.findByMemberId(member.getId());
        if(orders.size() <= 0) throw new NotFoundDataException("주문내역이 없습니다.");
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    // 주문 생성
    @Transactional
    public Long order(Member member, OrderItemDTO... orderItemDTO){
        List<OrderItem> orderItems = createOrderItems(orderItemDTO);
        Order order = Order.createOrder(member, orderItems.toArray(OrderItem[]::new));
        return orderRepository.save(order).getId();
    }

    // 주문 취소
    @Transactional
    public void orderCancel(Long orderId){
        Order order = findOne(orderId);
        order.cancel();
    }

}
