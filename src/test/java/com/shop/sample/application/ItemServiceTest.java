package com.shop.sample.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.dao.ItemRepository;
import com.shop.sample.dao.ShopRepository;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.Shop;
import com.shop.sample.dto.ItemDTO;
import com.shop.sample.exception.NotFoundDataException;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShopRepository shopRepository;

    private Shop createShop(){
        return Shop.builder().name("아이템서비스").build();
    }

    @Test
    @Transactional
    void 상품_생성_서비스() {
        ItemDTO given1 = ItemDTO.builder().name("생성상품").price(9900).stock(10).build();
        Shop given2 = shopRepository.save(createShop());
        
        Item when = given1.toEntity(given2);
        Item then = itemRepository.save(when);
        itemRepository.flush();

        assertThat(then.getName()).isEqualTo("생성상품");
        assertThat(then.getShop().getName()).isEqualTo("아이템서비스");
        assertThat(then.getPrice()).isEqualTo(9900);
        assertThat(then.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @Transactional
    void ItemDTO목록조회_서비스() {
        상품_생성_서비스();
        List<Item> whenList = itemRepository.findAll();
        if(whenList.size() <= 0) throw new NotFoundDataException();
        List<ItemDTO> thenList = whenList.stream().map(ItemDTO::new).collect(Collectors.toList());

        assertTrue(TestTransaction.isActive());
        assertThat(thenList.size()).isEqualTo(20);
        assertThat(thenList.get(0).getClass()).isEqualTo(ItemDTO.class);
    }

    private Item when(Long given){
        return itemRepository.findById(given)
            .orElseThrow(() -> new NotFoundDataException("상품을 찾을 수 없습니다."));
    }

    @Test
    void 상품조회_서비스() {
        Long given = 99L;
        NotFoundDataException then = assertThrows(NotFoundDataException.class, ()-> when(given));
        assertThat(then.getMessage()).isEqualTo("상품을 찾을 수 없습니다.");
    }

    @Test
    @Transactional
    void ItemDTO변환_서비스() {
        상품_생성_서비스();
        Item given = itemRepository.findAll().get(0);
        ItemDTO when = new ItemDTO(given);
        assertThat(when.getClass()).isEqualTo(ItemDTO.class);
    }

    private void whenList(List<Item> whenList){
        if(whenList.size() <= 0) throw new NotFoundDataException("해당 상점에 등록된 상품이 없습니다.");
    }

    @Test
    void 샵으로_상품목록조회() {
        Shop given = shopRepository.save(createShop());
        List<Item> whenList = itemRepository.findByShopId(given.getId());
        NotFoundDataException then = assertThrows(NotFoundDataException.class, ()-> whenList(whenList));
        assertThat(then.getMessage()).isEqualTo("해당 상점에 등록된 상품이 없습니다.");
    }
}
