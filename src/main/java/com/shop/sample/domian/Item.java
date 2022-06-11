package com.shop.sample.domian;

import javax.persistence.*;

import com.shop.sample.model.ItemDTO;
import com.shop.sample.exception.NotEnoughQuantityException;

import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
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

    public static Item create(Shop shop, ItemDTO itemDTO){
        Item item = new Item();
        item.name = itemDTO.getName();
        item.price = itemDTO.getPrice();
        item.stockQuantity = itemDTO.getStock();
        item.shop = shop;
        item.itemStatus = itemStatus(itemDTO.getStatus());
        return item;
    }

    private static ItemStatus itemStatus(String itemStatus) {
        switch(itemStatus){
            case "품절":
                return ItemStatus.SOLD_OUT;
            case "일시품절":
                return ItemStatus.TEMP_OUT;
            case "판매중":
                return ItemStatus.SALE;
            case "판매대기":
                return ItemStatus.WAIT;
            default:
                return ItemStatus.WAIT;
        }
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
