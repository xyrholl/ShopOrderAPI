package com.shop.sample.domian;

import javax.persistence.*;

import com.shop.sample.exception.NotEnoughQuantityException;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "orderItemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;
    private int count;

    public static OrderItem createOrderItem(Item item, int count){
        if(count <= 0) throw new NotEnoughQuantityException("주문 수량이 충분하지 않습니다.");
        if(item.getStockQuantity() < count) throw new NotEnoughQuantityException("주문 수량이 재고 수량보다 많습니다.");
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.count = count;
        return orderItem;
    }

    void setOrder(Order order){
        this.order = order;
    }

    public int getFullPrice(){
        return item.getPrice() * count;
    }

    public void cancel() {
        getItem().addStock(count);
    }

    public void completePyment(){
        item.removeStock(count);
    }
}
