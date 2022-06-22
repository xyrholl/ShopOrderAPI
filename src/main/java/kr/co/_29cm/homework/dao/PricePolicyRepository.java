package kr.co._29cm.homework.dao;

import kr.co._29cm.homework.domian.PricePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePolicyRepository extends JpaRepository<PricePolicy, Long> {
}
