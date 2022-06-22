package kr.co._29cm.homework.dao;

import kr.co._29cm.homework.domian.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
