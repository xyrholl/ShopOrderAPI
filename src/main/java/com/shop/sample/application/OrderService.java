package com.shop.sample.application;

import com.shop.sample.dao.OrderItemRepository;
import com.shop.sample.dao.OrderRepository;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.Order;
import com.shop.sample.domian.OrderItem;
import com.shop.sample.domian.PricePolicy;
import com.shop.sample.dto.OrderDTO;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.exception.NotFoundDataException;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PricePolicyService pricePolicyService;
    private final ItemService itemService;

    @Transactional
    public List<OrderDTO> orders(){
        List<Order> orders = orderRepository.findAll();
        if(orders.size() <= 0) throw new NotFoundDataException("주문내역이 없습니다.");
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    private List<OrderItem> toOrderItems(OrderItemDTO... orderItemDTOs){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            Item findItem = itemService.findOne(orderItemDTO.getItemId());
            OrderItem orderItem = orderItemDTO.toEntity(findItem, orderItemDTO);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public Order findOne(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> new NotFoundDataException("존재하지 않는 주문입니다."));
    }

    @Transactional
    public OrderDTO toOrderDTO(Long orderId){
        return new OrderDTO(findOne(orderId));
    }

    @Transactional
    public Long order(OrderItemDTO... orderItemDTOs){
        List<OrderItem> orderItems = toOrderItems(orderItemDTOs);
        PricePolicy pricePolicy = pricePolicyService.findPolicy(1L);
        Order order = Order.builder().pricePolicy(pricePolicy).orderItems(orderItems).build();
        return orderRepository.save(order).getId();
    }

    @Transactional
    public void orderCancel(Long orderId){
        Order order = findOne(orderId);
        order.cancel();
    }

    @Transactional
    public void orderEdit(Long orderId, OrderItemDTO... orderItemDTOs) {
        Order order = findOne(orderId);
        order.removeOrderItem();
        List<OrderItem> orderItems = toOrderItems(orderItemDTOs);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
    }

}
