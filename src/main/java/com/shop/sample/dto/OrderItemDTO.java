package com.shop.sample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.sample.domian.Item;
import com.shop.sample.domian.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {

    private Long itemId;
    private String name;
    private int fullPrice;
    private int count;

    public OrderItemDTO(OrderItem orderItem){
        Item item = orderItem.getItem();
        this.itemId = item.getId();
        this.name = item.getName();
        this.fullPrice = orderItem.getFullPrice();
        this.count = orderItem.getCount();
    }

    public OrderItem toEntity(Item item){
        return OrderItem.builder()
        .item(item)
        .count(this.count)
        .build();
    }

}
