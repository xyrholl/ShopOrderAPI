package com.shop.sample.service;

import com.shop.sample.domian.Member;
import com.shop.sample.model.MemberDTO;
import com.shop.sample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDTO create(MemberDTO memberDTO) {
        Member member = memberDTO.toEntity(memberDTO.getPassWord());
        return memberDTO.toDTO(memberRepository.save(member));
    }
}
