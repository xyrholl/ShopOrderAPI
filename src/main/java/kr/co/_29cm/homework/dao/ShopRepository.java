package kr.co._29cm.homework.dao;

import kr.co._29cm.homework.domian.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>{
    
}
