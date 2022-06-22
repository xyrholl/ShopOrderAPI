package kr.co._29cm.homework.dao;

import kr.co._29cm.homework.domian.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
