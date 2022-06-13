package com.shop.sample.dao;

import com.shop.sample.domian.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByShopId(Long shopId);
}
