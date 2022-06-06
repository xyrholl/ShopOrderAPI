package com.shop.sample.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "orderId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    public int getTotalPrice(){
//        return orderItems.stream().mapToInt(OrderItem::getFullPrice).sum();
//    }
//
//    public void cancel(){
//        this.status = OrderStatus.CANCEL;
//        for(OrderItem orderItem: orderItems){
//            orderItem.cancel();
//        }
//    }

}
