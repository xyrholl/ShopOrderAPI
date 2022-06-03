package com.shop.sample.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    
    private Long id;
    private Member member;
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getFullPrice).sum();
    }

    public void cancel(){
        this.status = OrderStatus.CANCEL;
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }

}
