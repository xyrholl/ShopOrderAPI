package com.shop.sample.repository;

import com.shop.sample.domian.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
