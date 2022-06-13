package com.shop.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.sample.domian.Shop;
import com.shop.sample.dto.ShopDTO;
import com.shop.sample.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public void create(ShopDTO shopDTO){
        Shop shop = Shop.createShop(shopDTO);
        shopRepository.save(shop);
    }
    
}
