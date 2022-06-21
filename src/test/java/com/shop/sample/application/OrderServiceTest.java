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

import com.shop.sample.dao.ItemRepository;
import com.shop.sample.dao.OrderRepository;
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

}
