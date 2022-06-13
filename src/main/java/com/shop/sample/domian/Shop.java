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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {
    
    @Id @GeneratedValue
    @Column(name = "shopId")
    private Long id;
    private String name;
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Singular
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Shop(@NonNull String name){
        this.name = name;
    }

    public void addItem(Item item){
        items.add(item);
        item.setShop(this);
    }
    
}
