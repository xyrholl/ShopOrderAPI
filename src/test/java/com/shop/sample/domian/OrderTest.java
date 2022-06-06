package com.shop.sample.domian;

import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.MemberRepository;
import com.shop.sample.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Member memberCreate(){
        return Member.builder().id("test_id").name("lee").build();
    }

    private Item itemCreate(){
        return Item.builder().id(1L).name("책").price(1000).itemStatus(ItemStatus.SALE).build();
    }


    @Test
    @Transactional
    void 상품주문() {
        //given
        Member member = memberCreate();
        Item item = itemCreate();

        memberRepository.save(member);
        itemRepository.save(item);

        Member findMember = memberRepository.findById("test_id").get();
        Item findItem = itemRepository.findById(1L).get();

        //when
        OrderItem orderItem = OrderItem.createOrderItem(findItem, 2);
        Order order = Order.createOrder(findMember, orderItem);
        orderRepository.save(order);

        //then
        List<Order> orders = orderRepository.findAll();

        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getMember().getName()).isEqualTo("lee");
        Assertions.assertThat(orders.get(0).getTotalPrice()).isEqualTo(2000);

    }

}