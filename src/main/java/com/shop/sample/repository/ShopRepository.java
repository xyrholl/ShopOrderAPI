package com.shop.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.sample.domian.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{
    
}
