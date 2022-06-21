package com.shop.sample.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.shop.sample.exception.PaymentCompleteException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricePolicyId")
    private PricePolicy pricePolicy;

    private int paymentPrice;
    private LocalDateTime paymentTime;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(PricePolicy pricePolicy, @Singular List<OrderItem> orderItems){
        this.status = OrderStatus.WAIT;
        this.pricePolicy = pricePolicy;
        for (OrderItem orderItem : orderItems) {
            this.addOrderItem(orderItem);
        }
    }

    public void addOrderItem(OrderItem orderItem){
        if(this.status == OrderStatus.ORDER) throw new PaymentCompleteException("결제된 주문은 수정이 불가합니다.");
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(){
        if(this.status == OrderStatus.ORDER) throw new PaymentCompleteException("결제된 주문은 수정이 불가합니다.");
        orderItems.removeAll(this.orderItems);
    }

    public int getOrderPrice(){
        return orderItems.stream().mapToInt(OrderItem::getFullPrice).sum();
    }

    public int getFare(){
        int fare = this.pricePolicy.getShippingFee();
        if(getOrderPrice() >= this.pricePolicy.getFreeBaseAmount() ) fare = 0;
        return fare;
    }

    public int getTotalPrice(){
        return getOrderPrice()+getFare();
    }

    public void cancel(){
        if(this.status == OrderStatus.ORDER) for (OrderItem orderItem : orderItems) { orderItem.cancel(); }
        this.status = OrderStatus.CANCEL;
    }

    public void completePayment(){
        if(this.status == OrderStatus.ORDER) throw new PaymentCompleteException("결제완료된 주문입니다.");
        this.paymentPrice = getTotalPrice();
        this.status = OrderStatus.ORDER;
        this.paymentTime = LocalDateTime.now();
        for(OrderItem orderItem : orderItems){
            orderItem.completePyment();
        }
    }

}
