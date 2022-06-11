package com.shop.sample.model;

import com.shop.sample.domian.Member;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long id;
    private String userName;
    private String passWord;
    private String email;

    public Member toEntity(String encodePassword){
        return Member.builder()
                .email(email)
                .passWord(encodePassword)
                .userName(userName)
                .build();
    }

    public MemberDTO toDTO(Member member){
        this.userName = member.getUserName();
        this.email = member.getEmail();
        this.id = member.getId();
        return this;
    }



}
