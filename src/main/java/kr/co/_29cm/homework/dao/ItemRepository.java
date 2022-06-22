package kr.co._29cm.homework.dao;

import kr.co._29cm.homework.domian.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByShopId(Long shopId);
}
