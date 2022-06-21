package com.shop.sample.application;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.sample.dao.OrderRepository;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.ItemStatus;
import com.shop.sample.domian.Order;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.exception.SoldOutException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;
    @Autowired PaymentService paymentService;
    @Autowired ItemService itemService;

    private void createOrders(){
        OrderItemDTO orderItemDTO = OrderItemDTO.builder().itemId(611019L).count(1).build();
        int count = 100;
        for (int i = 0; i < count; i++) {
            orderService.order(orderItemDTO);
        }
    }

    ExecutorService service = Executors.newFixedThreadPool(10);


    @Test
    void 결제완료_멀티쓰레드_SoldOutException() throws InterruptedException{
        //given
        createOrders();

        //when
        List<Order> orders = orderRepository.findAll();
        int count = 100;
        AtomicInteger successCount = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Long id = orders.get(i).getId();
            service.execute(()->{
                try {
                    paymentService.completePaymentOrder(id);
                    successCount.getAndIncrement();
                    latch.countDown();
                } catch (SoldOutException e) {
                    e.printStackTrace();
                }
            });
        }
        latch.await(5, TimeUnit.SECONDS);
        Item item =  itemService.findOne(611019L);

        //then
        assertThat(successCount.get()).isEqualTo(7);
        assertThat(item.getStockQuantity()).isEqualTo(0);
        assertThat(item.getItemStatus()).isEqualTo(ItemStatus.SOLD_OUT);
    }
}
