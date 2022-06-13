package com.shop.sample.domian;

import javax.persistence.*;

import com.shop.sample.exception.NotEnoughPriceException;
import com.shop.sample.exception.NotEnoughQuantityException;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "itemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    private String name;
    private int price;
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Builder
    public Item(Shop shop, @NonNull String name, int price, int stockQuantity, ItemStatus itemStatus){
        if(price < 0) throw new NotEnoughPriceException("가격을 0이하로 생성할 수 없습니다.");
        if(stockQuantity < 0) throw new NotEnoughQuantityException("재고를 0이하로 생성할 수 없습니다.");
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemStatus = itemStatus;
    }

    public void setShop(Shop shop){
        this.shop = shop;
    }

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
        if(tempStock == 0) temporarilyOutOfStock();
        if(tempStock < 0) throw new NotEnoughQuantityException("재고가 소진되어 수량이 모자랍니다.");
        this.stockQuantity = tempStock;
    }

}
