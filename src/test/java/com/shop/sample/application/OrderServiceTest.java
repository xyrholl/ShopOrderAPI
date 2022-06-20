package com.shop.sample.application;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.sample.dao.ItemRepository;
import com.shop.sample.dao.OrderRepository;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.Order;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.exception.SoldOutException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired PaymentService paymentService;

    @Autowired ItemRepository itemRepository;
    @Autowired OrderRepository orderRepository;

    private Item findItem(){
        return itemRepository.findByShopId(1L).get(0);
    }
    ExecutorService service = Executors.newFixedThreadPool(10);

    @Test
    void 주문생성_쓰레드_테스트() throws InterruptedException{
        OrderItemDTO orderItemDTO = OrderItemDTO.builder().itemId(213341L).count(10).build();

        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            service.execute(()->{
                orderService.order(orderItemDTO);
                latch.countDown();
            });
        }
        latch.await();

        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(100);
    }


    @Test
    void 결제완료_멀티쓰레드_SoldOutException() throws InterruptedException {
        주문생성_쓰레드_테스트();
        List<Order> orders = orderRepository.findAll();

        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Long id = orders.get(i).getId();
            service.execute(()->{
                try {
                    paymentService.completePaymentOrder(id);
                    latch.countDown();
                } catch (SoldOutException e) {
                    e.printStackTrace();
                }

            });
        }
        latch.await();
        

        
    }

}
