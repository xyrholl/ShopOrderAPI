package com.shop.sample.dao;

import com.shop.sample.domian.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
