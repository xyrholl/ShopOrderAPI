package com.shop.sample.repository;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.Shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByShopId(Long shopId);
}
