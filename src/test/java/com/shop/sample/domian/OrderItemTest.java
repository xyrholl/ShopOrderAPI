package com.shop.sample.domian;

import com.shop.sample.exception.NotEnoughQuantityException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderItemTest {

    Item itemCreate(){
        return Item.builder().name("책").price(1000).itemStatus(ItemStatus.SALE).stockQuantity(10).build();
    }

    @Test
    void 주문수량이_0인_경우(){
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> OrderItem.builder().item(itemCreate()).count(0).build());
        assertEquals(thrown.getMessage(), "주문 수량이 충분하지 않습니다.");
    }

    @Test
    void 주문수량이_재고수량보다_많은경우(){
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> OrderItem.builder().item(itemCreate()).count(11).build());
        assertEquals(thrown.getMessage(), "주문 수량이 재고 수량보다 많습니다.");
    }

}