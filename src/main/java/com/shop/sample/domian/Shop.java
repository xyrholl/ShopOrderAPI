package com.shop.sample.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.shop.sample.dto.ShopDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {
    
    @Id @GeneratedValue
    @Column(name = "shopId")
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Builder.Default
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public static Shop createShop(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.createTime = LocalDateTime.now();
        shop.name = shopDTO.getName();
        return shop;
    }

    public void addItem(Item item){
        items.add(item);
        item.setShop(this);
    }
    
}
