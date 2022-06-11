package com.shop.sample.domian;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "memberId")
    private Long id;
    private String userName;
    private String passWord;
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
