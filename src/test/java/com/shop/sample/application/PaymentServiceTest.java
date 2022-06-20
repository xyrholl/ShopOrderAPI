package com.shop.sample.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired PaymentService paymentService;

    ExecutorService service = Executors.newFixedThreadPool(10);

    @Test
    void 결제완료_멀티쓰레드_SoldOutException() {

    }
}
