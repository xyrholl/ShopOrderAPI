package com.shop.sample.domian;
import static org.assertj.core.api.Assertions.*;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.repository.ItemRepository;

@SpringBootTest
public class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    Item createItem(){
        return Item.builder().name("책").price(12000).stockQuantity(10).itemStatus(ItemStatus.SALE).build();
    }

    @Test
    @Transactional
    void 상품_재고0일때_변경시_일시품절로전환(){
        //given
        Item item = createItem();
        itemRepository.save(item);

        //when
        List<Item> findItems = itemRepository.findAll();
        findItems.get(0).removeStock(10);

        //then
        List<Item> finalItem = itemRepository.findAll();
        
        assertThat(finalItem.get(0).getStockQuantity()).isEqualTo(0);
        assertThat(finalItem.get(0).getItemStatus()).isEqualTo(ItemStatus.TEMP_OUT);
    }

    @Test
    @Transactional
    void 상품품절일때_수량0_변경(){
        //given
        Item item = createItem();
        itemRepository.save(item);

        //when
        List<Item> findItems = itemRepository.findAll();
        findItems.get(0).soldOut();

        //then
        List<Item> finalItem = itemRepository.findAll();

        assertThat(finalItem.get(0).getStockQuantity()).isEqualTo(0);
        assertThat(finalItem.get(0).getItemStatus()).isEqualTo(ItemStatus.SOLD_OUT);

    }
    
}
