package com.shop.sample.dto;

import com.shop.sample.domian.Item;
import com.shop.sample.domian.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long id;
    private String name;
    private int price;
    private int stock;
    private String status;

    public ItemDTO(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stock = item.getStockQuantity();
        itemStatus(item.getItemStatus());
    }

    private void itemStatus(ItemStatus itemStatus) {
        switch(itemStatus){
            case SOLD_OUT:
                this.status = "품절";
                break;
            case TEMP_OUT:
                this.status = "일시품절";
                break;
            case SALE:
                this.status = "판매중";
                break;
            case WAIT:
                this.status = "판매대기";
                break;
            default:
                break;
        }
    }

}
