package kr.co._29cm.homework.domian;

import javax.persistence.*;

import kr.co._29cm.homework.exception.SoldOutException;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;
    private int count;

    @Builder
    public OrderItem(Item item, int count){
        if(count <= 0) throw new SoldOutException("주문 수량이 충분하지 않습니다.");
        if(item.getStockQuantity() < count) throw new SoldOutException("주문 수량이 재고 수량보다 많습니다.");
        this.item = item;
        this.count = count;
    }

    public void setOrder(Order order){
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
