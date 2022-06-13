package com.shop.sample.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.Shop;
import com.shop.sample.dto.ItemDTO;
import com.shop.sample.dto.ShopDTO;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    private Shop findShop(ShopDTO shopDTO){
        return shopRepository.findById(shopDTO.getId())
            .orElseThrow(()-> new NotFoundDataException("샵을 찾을 수 없습니다."));
    }

    private List<Item> findList(Shop shop){
        List<Item> list = itemRepository.findByShopId(shop.getId());
        if(list.size() <= 0) throw new NotFoundDataException("등록된 상품이 없습니다.");
        return list;
    }

    private Item findOne(ItemDTO itemDTO){
        return itemRepository.findById(itemDTO.getId())
            .orElseThrow(() -> new NotFoundDataException("상품을 찾을 수 없습니다."));
    }

    public ItemDTO findOneItemDTO(ItemDTO itemDTO){
        return new ItemDTO(findOne(itemDTO));
    }

    public List<ItemDTO> findListItemDTOs(ShopDTO shopDTO){
        return findList(findShop(shopDTO)).stream().map(ItemDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void create(ShopDTO shopDTO, ItemDTO itemDTO){
        Item item = Item.create(findShop(shopDTO), itemDTO);
        itemRepository.save(item);
    }


    
}
