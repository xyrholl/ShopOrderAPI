package com.shop.sample.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.shop.sample.dto.OrderDTO;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "orderId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public static Order createOrder(Member member, OrderItem... orderItems){
        Order order = new Order();
        order.member = member;
        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.status = OrderStatus.WAIT;
        order.orderTime = LocalDateTime.now();
        return  order;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getFullPrice).sum();
    }

    public void cancel(){
        if(this.status == OrderStatus.ORDER) for (OrderItem orderItem : orderItems) { orderItem.cancel(); }
        this.status = OrderStatus.CANCEL;
    }

    public void completePyment(){
        this.status = OrderStatus.ORDER;
        for(OrderItem orderItem : orderItems){
            orderItem.completePyment();
        }
    }

}
