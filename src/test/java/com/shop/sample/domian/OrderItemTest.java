package com.shop.sample.domian;

import com.shop.sample.exception.NotEnoughQuantityException;
import com.shop.sample.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderItemTest {

    Item itemCreate(){
        return Item.builder().id(1L).name("책").price(1000).itemStatus(ItemStatus.SALE).build();
    }

    @Test
    void 주문아이템_수량0_생성테스트(){
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> OrderItem.createOrderItem(itemCreate(), 0));
        assertEquals(thrown.getMessage(), "주문수량이 충분하지 않습니다.");
    }

}