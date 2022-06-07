package com.shop.sample.domian;

import com.shop.sample.exception.NotEnoughQuantityException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderItemTest {

    Item itemCreate(){
        return Item.builder().id(1L).name("책").price(1000).itemStatus(ItemStatus.SALE).stockQuantity(10).build();
    }

    @Test
    void 주문수량0_테스트(){
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> OrderItem.createOrderItem(itemCreate(), 0));
        assertEquals(thrown.getMessage(), "주문 수량이 충분하지 않습니다.");
    }

    @Test
    void 주문수량초과_테스트(){
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> OrderItem.createOrderItem(itemCreate(), 11));
        assertEquals(thrown.getMessage(), "재고 수량이 충분하지 않습니다.");
    }

}