package com.shop.sample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shop.sample.domian.Shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopDTO {

    private Long id;
    private String name;
    @Builder.Default
    private List<ItemDTO> items = new ArrayList<>();

    public ShopDTO(Shop shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.items = shop.getItems().stream().map(ItemDTO::new).collect(Collectors.toList());
    }

}
