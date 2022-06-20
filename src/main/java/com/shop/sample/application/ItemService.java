package com.shop.sample.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.dao.ItemRepository;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.Shop;
import com.shop.sample.dto.ItemDTO;
import com.shop.sample.exception.NotFoundDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    List<Item> findShopItems(Shop shop){
        List<Item> findList = itemRepository.findByShopId(shop.getId());
        if(findList.size() <= 0) throw new NotFoundDataException("해당 상점에 등록된 상품이 없습니다.");
        return findList;
    }

    @Transactional
    public List<ItemDTO> findDTOList(){
        List<Item> findList = itemRepository.findAll();
        if(findList.size() <= 0 ) throw new NotFoundDataException("등록된 상품이 없습니다.");
        return findList.stream().map(ItemDTO::new).collect(Collectors.toList());
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundDataException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    public ItemDTO findOneItemDTO(Long itemId){
        return new ItemDTO(findOne(itemId));
    }

    @Transactional
    public Long create(Shop shop, ItemDTO itemDTO){
        Item item = itemDTO.toEntity(shop);
        return itemRepository.save(item).getId();
    }
    
}
