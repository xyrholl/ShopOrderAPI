package com.shop.sample.domian;

import com.shop.sample.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void builder_생성_DB_저장확인(){
        Member member = Member.builder().id("builder_test").name("gang").build();
        memberRepository.save(member);

        Member find = memberRepository.findById("builder_test").get();

        Assertions.assertThat(find.getName()).isEqualTo("gang");
    }



}