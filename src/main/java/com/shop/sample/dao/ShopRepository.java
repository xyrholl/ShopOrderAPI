package com.shop.sample.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.sample.domian.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{
    
}
