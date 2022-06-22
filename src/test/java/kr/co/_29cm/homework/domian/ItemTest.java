package kr.co._29cm.homework.domian;
import static org.assertj.core.api.Assertions.*;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.dao.ItemRepository;

@SpringBootTest
public class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Transactional
    void 상품수량0일때_품절로변경(){
        //given
        List<Item> findItems = itemRepository.findAll();
        Item findItem =findItems.get(0);
        int quantity = findItem.getStockQuantity();
        //when
        findItem.removeStock(quantity);

        //then
        List<Item> finalItems = itemRepository.findAll();

        assertThat(finalItems.get(0).getStockQuantity()).isEqualTo(0);
        assertThat(finalItems.get(0).getItemStatus()).isEqualTo(ItemStatus.SOLD_OUT);

    }
    
}
