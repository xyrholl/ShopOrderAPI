package com.shop.sample.domian;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.shop.sample.exception.NotEnoughQuantityException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    private Long id;
    private Member seller;

    private String name;
    private int price;
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    public void soldOut(){
        this.stockQuantity = 0;
        this.itemStatus = ItemStatus.SOLDOUT;
    }

    public void temporarilyOutOfStock(){
        this.itemStatus = ItemStatus.TEMPOUT;
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int tempStock = this.stockQuantity - quantity;
        if(tempStock < 0) throw new NotEnoughQuantityException();
        this.stockQuantity = tempStock;
    }

}
