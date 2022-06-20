package com.shop.sample.domian;

import com.shop.sample.dao.ItemRepository;
import com.shop.sample.dao.OrderRepository;
import com.shop.sample.dao.PricePolicyRepository;
import com.shop.sample.exception.SoldOutException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PricePolicyRepository pricePolicyRepository;

    @Test
    void 단일_상품주문() {
        //given
        List<Item> findItems = itemRepository.findAll();
        Item findItem = findItems.get(0);

        //when
        OrderItem orderItem = OrderItem.builder().item(findItem).count(2).build();
        PricePolicy pricePolicy = pricePolicyRepository.findAll().get(0);
        Order order = Order.builder().pricePolicy(pricePolicy).orderItem(orderItem).build();
        orderRepository.save(order);

        //then
        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getStatus()).isEqualTo(OrderStatus.WAIT);
        assertThat(orders.get(0).getOrderItems().get(0).getItem().getName()).isEqualTo(findItem.getName());
        assertThat(orders.get(0).getTotalPrice()).isEqualTo(findItem.getPrice()*2+orders.get(0).getFare());

    }

    @Test
    void 다중_상품주문(){
        //given
        List<Item> findItems = itemRepository.findAll();
        Item findItemOne = findItems.get(1);
        Item findItemTwo = findItems.get(2);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().item(findItemOne).count(1).build());
        orderItems.add(OrderItem.builder().item(findItemTwo).count(1).build());

        
        //when
        PricePolicy pricePolicy = pricePolicyRepository.findAll().get(0);
        Order order = Order.builder().pricePolicy(pricePolicy).orderItems(orderItems).build();
        orderRepository.save(order);

        //then
        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getOrderItems().size()).isEqualTo(2);
        assertThat(orders.get(0).getFare()).isEqualTo(2500);
        assertThat(orders.get(0).getTotalPrice()).isEqualTo(52300);
    }

    private void constru(int id, String... str){
    }

    @Test
    void 가변인수생성자(){
        constru(1, new String[]{});
    }

    @Test
    void 결제완료시_재고수량초과(){
        //given
        단일_상품주문();
        List<Item> findItems = itemRepository.findAll();
        int quantity = findItems.get(0).getStockQuantity();
        findItems.get(0).removeStock(quantity-1);
        
        //when
        Order order = orderRepository.findAll().get(0);
        SoldOutException thrown = assertThrows(SoldOutException.class,
                () -> order.completePayment());

        //then
        assertEquals(thrown.getMessage(), "주문한 상품수량이 재고수량보다 많습니다. 남은 재고수량은 1 개 입니다.");
    }

    @Transactional
    void orderCancel(Order order){
        order.cancel();
    }

    @Transactional
    void completePayment(Order order){
        order.completePayment();
    }

    @Test
    void 단일상품_결제_전_취소(){
        //given
        단일_상품주문();
        Order order = orderRepository.findAll().get(0);

        //when
        orderCancel(order);

        //then
        Order findOrder = orderRepository.findAll().get(0);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(99);
    }

    @Test
    void 단일상품_결제_완료(){
        단일_상품주문();
        Order order = orderRepository.findAll().get(0);
        completePayment(order);

        Order findOrder = orderRepository.findAll().get(0);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(97);
    }

    @Test
    void 단일상품_결제_후_취소(){
        단일_상품주문();
        Order order = orderRepository.findAll().get(0);
        completePayment(order);

        Order cancelOrder = orderRepository.findAll().get(0);
        orderCancel(cancelOrder);

        Order findOrder = orderRepository.findAll().get(0);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(99);
    }

}