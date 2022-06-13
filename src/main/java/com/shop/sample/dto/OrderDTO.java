package com.shop.sample.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.shop.sample.domian.Order;
import com.shop.sample.domian.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private List<OrderItemDTO> itemDTOs;
    private String status;
    private LocalDateTime orderTime;

    public OrderDTO(Order order){
        this.id = order.getId();
        this.itemDTOs = order.getOrderItems().stream().map(OrderItemDTO::new).collect(Collectors.toList());
        this.orderTime = order.getOrderTime();
        orderStatus(order.getStatus());
    }

    private void orderStatus(OrderStatus orderStatus) {
        switch(orderStatus){
            case CANCEL:
                this.status = "주문취소";
                break;
            case WAIT:
                this.status = "주문대기";
                break;
            case ORDER:
                this.status = "주문완료";
                break;
            default:
                break;
        }
    }
    
}
