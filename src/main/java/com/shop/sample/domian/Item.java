package com.shop.sample.domian;

import javax.persistence.*;

import com.shop.sample.exception.NotEnoughQuantityException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "itemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member seller;

    private String name;
    private int price;
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    public void soldOut(){
        this.stockQuantity = 0;
        this.itemStatus = ItemStatus.SOLD_OUT;
    }

    public void temporarilyOutOfStock(){
        this.itemStatus = ItemStatus.TEMP_OUT;
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
