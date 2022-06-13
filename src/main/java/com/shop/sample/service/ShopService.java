package com.shop.sample.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.domian.Shop;
import com.shop.sample.dto.ItemDTO;
import com.shop.sample.dto.ShopDTO;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ItemService itemService;

    public Shop findShop(Long shopId){
        return shopRepository.findById(shopId)
            .orElseThrow(()-> new NotFoundDataException("샵을 찾을 수 없습니다."));
    }

    public List<ItemDTO> findListItemDTOs(Long shopId){
        return itemService.findList(findShop(shopId)).stream().map(ItemDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void create(ShopDTO shopDTO){
        Shop shop = Shop.createShop(shopDTO);
        shopRepository.save(shop);
    }


    
}
