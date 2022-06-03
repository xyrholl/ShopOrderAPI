package com.shop.sample.domian;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private String id;
    private String name;
    private List<Order> orders = new ArrayList<>();

}
