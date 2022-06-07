package com.shop.sample.domian;

import javax.persistence.*;

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
        if(tempStock < 0) throw new NotEnoughQuantityException("재고가 소진되어 수량이 모자랍니다.");
        this.stockQuantity = tempStock;
    }

}
