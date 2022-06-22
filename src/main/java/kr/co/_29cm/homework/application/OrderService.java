package kr.co._29cm.homework.application;

import kr.co._29cm.homework.dao.OrderRepository;
import kr.co._29cm.homework.domian.Item;
import kr.co._29cm.homework.domian.Order;
import kr.co._29cm.homework.domian.OrderItem;
import kr.co._29cm.homework.domian.PricePolicy;
import kr.co._29cm.homework.dto.OrderDTO;
import kr.co._29cm.homework.dto.OrderItemDTO;
import kr.co._29cm.homework.exception.NotFoundDataException;

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
            OrderItem orderItem = orderItemDTO.toEntity(findItem);
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
