package com.shop.sample.domian;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    private Long id;
    private Order order;
    private Item item;
    private int perPrice;
    private int count;

    public int getFullPrice(){
        return getPerPrice() * getFullPrice();
    }

    public void cancel() {
        getItem().addStock(count);
    }
}
