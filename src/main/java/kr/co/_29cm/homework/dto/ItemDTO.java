package kr.co._29cm.homework.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co._29cm.homework.domian.Item;
import kr.co._29cm.homework.domian.ItemStatus;
import kr.co._29cm.homework.domian.Shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {

    private Long item_id;
    private String shop_name;
    private String name;
    private int price;
    private int stock;
    private String status;

    public ItemDTO(Item item){
        this.item_id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stock = item.getStockQuantity();
        this.shop_name = item.getShop().getName();
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

    public Item toEntity(Shop shop){
        return Item.builder()
        .shop(shop)
        .name(this.name)
        .price(this.price)
        .stockQuantity(this.stock)
        .itemStatus(toItemStatus(this.status))
        .build();
    }

    private ItemStatus toItemStatus(String itemStatus) {
        if(itemStatus == null) itemStatus = "판매대기";
        switch(itemStatus){
            case "품절":
                return ItemStatus.SOLD_OUT;
            case "일시품절":
                return ItemStatus.TEMP_OUT;
            case "판매중":
                return ItemStatus.SALE;
            case "판매대기":
                return ItemStatus.WAIT;
            default:
                return ItemStatus.WAIT;
        }
    }

}
