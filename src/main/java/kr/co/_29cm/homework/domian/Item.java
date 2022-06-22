package kr.co._29cm.homework.domian;

import javax.persistence.*;

import kr.co._29cm.homework.exception.NotEnoughPriceException;
import kr.co._29cm.homework.exception.SoldOutException;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        if(price < 0) throw new NotEnoughPriceException("가격을 0미만으로 생성할 수 없습니다.");
        if(stockQuantity < 0) throw new SoldOutException("재고를 0미만으로 생성할 수 없습니다.");
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
        this.itemStatus = ItemStatus.SOLD_OUT;
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int tempStock = this.stockQuantity - quantity;
        if(tempStock == 0) soldOut();
        if(tempStock < 0) throw new SoldOutException("주문한 상품수량이 재고수량보다 많습니다. 남은 재고수량은 "+this.stockQuantity+" 개 입니다.");
        this.stockQuantity = tempStock;
    }

}
