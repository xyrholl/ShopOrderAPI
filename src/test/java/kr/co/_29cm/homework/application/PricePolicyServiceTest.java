package kr.co._29cm.homework.application;

import static org.junit.jupiter.api.Assertions.assertThrows;

import kr.co._29cm.homework.domian.PricePolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co._29cm.homework.exception.NotFoundDataException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PricePolicyServiceTest {

    @Autowired PricePolicyService pricePolicyService;

    @Test
    void 가격정책_테스트() {

        NotFoundDataException thrown = assertThrows(NotFoundDataException.class, ()-> pricePolicyService.findPolicy(2L));
        assertThat(thrown.getMessage()).isEqualTo("해당 가격정책이 존재하지 않습니다.");

        PricePolicy pricePolicy = pricePolicyService.findPolicy(1L);
        assertThat(pricePolicy.getShippingFee()).isEqualTo(2500);
        assertThat(pricePolicy.getFreeBaseAmount()).isEqualTo(50000);
    }
}
