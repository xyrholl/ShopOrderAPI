package com.shop.sample.repository;

import com.shop.sample.domian.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
