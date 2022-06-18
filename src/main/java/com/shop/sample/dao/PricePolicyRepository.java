package com.shop.sample.dao;

import com.shop.sample.domian.PricePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePolicyRepository extends JpaRepository<PricePolicy, Long> {
}
