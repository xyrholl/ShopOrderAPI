package kr.co._29cm.homework.domian;

import java.util.List;
import java.util.stream.Collectors;

import kr.co._29cm.homework.dao.ShopRepository;
import kr.co._29cm.homework.dto.ShopDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.dao.ItemRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ShopTest {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ItemRepository itemRepository;

    Item createItem(Shop shop){
        return Item.builder().name("책").shop(shop).price(1000).itemStatus(ItemStatus.SALE).stockQuantity(10).build();
    }

    Shop createShop(){
        return Shop.builder().name("샵").build();
    }

    @Transactional
    Shop 샵_생성(){
        //given
        Shop sample = Shop.builder().name("샵").build();
        shopRepository.save(sample);

        //when
        List<Shop> findShops = shopRepository.findAll();

        //then
        assertThat(findShops.size()).isEqualTo(1);
        assertThat(findShops.get(0).getName()).isEqualTo("샵");
        return findShops.get(0);
    }

    @Test
    @Transactional
    void 샵_상품_추가(){
        //given
        Shop shop = shopRepository.findAll().get(0);
        Item item = createItem(shop);
        itemRepository.save(item);
        shop.addItem(item);
        itemRepository.flush();
        //when
        List<Shop> findShops = shopRepository.findAll();

        //then
        assertThat(findShops.size()).isEqualTo(1);
        assertThat(findShops.get(0).getName()).isEqualTo("29CM");
        assertThat(findShops.get(0).getItems().size()).isEqualTo(20);
    }

    @Test
    @Transactional
    void 샵_DTO_테스트(){
        //given
        샵_상품_추가();
        List<Shop> shops = shopRepository.findAll();
        
        //when
        List<ShopDTO> shopDTOs = shops.stream().map(ShopDTO::new).collect(Collectors.toList());

        //then
        assertThat(shopDTOs.get(0).getClass()).isEqualTo(ShopDTO.class);
    }

    @Test
    @Transactional
    void 샵으로_상품리스트조회(){
        //given
        샵_상품_추가();

        //when
        List<Shop> shops = shopRepository.findAll();
        assertThat(shops.get(0).getId().getClass()).isEqualTo(Long.class);
        List<Item> items = itemRepository.findByShopId(shops.get(0).getId());
        
        //then
        assertThat(items.size()).isEqualTo(20);

    }

    @Test
    @Transactional
    void shop_빌더테스트(){
        //given
        
        //when
        Shop findShop = shopRepository.findAll().get(0);

        //then
        assertThat(findShop.getName()).isEqualTo("29CM");
        assertThat(findShop.getId()).isEqualTo(1);
    }

}
