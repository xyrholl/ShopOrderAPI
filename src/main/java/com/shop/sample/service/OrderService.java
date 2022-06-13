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
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public List<OrderDTO> orders(/*MemberId*/){
        List<Order> orders = orderRepository.findAll(/*MemberId*/);
        if(orders.size() <= 0) throw new NotFoundDataException("주문내역이 없습니다.");
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    private List<OrderItem> createOrderItems(List<OrderItemDTO> orderItemDtos){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDtos) {
            Optional<Item> findItem = itemRepository.findById(orderItemDTO.getItemId());
            if(findItem.isPresent())orderItems.add(OrderItem.createOrderItem(findItem.get(), orderItemDTO.getCount()));
        }
        return orderItems;
    }

    private Order findOne(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> new NotFoundDataException("존재하지 않는 주문입니다."));
    }

    @Transactional
    public void order(OrderDTO orderDTO){
        Member findMember = memberRepository.findById(orderDTO.getOrderer())
            .orElseThrow(() -> new NotFoundDataException("존재하지 않는 회원입니다."));
        List<OrderItem> orderItems = createOrderItems(orderDTO.getItemDTOs());
        Order order = Order.createOrder(findMember, orderItems.stream().toArray(OrderItem[]::new));
        orderRepository.save(order);
    }

    @Transactional
    public void orderCancel(Long orderId){
        Order order = findOne(orderId);
        order.cancel();
    }

    @Transactional
    public void orderEdit(Long orderId, OrderDTO orderDTO) {
        Order order = findOne(orderId);
        List<Long> oldItemIds = order.getOrderItems().stream().mapToLong(i -> i.getId()).boxed().collect(Collectors.toList());
        itemRepository.deleteAllById(oldItemIds);
        List<OrderItem> newItems = createOrderItems(orderDTO.getItemDTOs());

    }

}
