package kr.co._29cm.homework.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co._29cm.homework.domian.Shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ShopDTO {

    private Long shop_id;
    private String name;

    public ShopDTO(Shop shop){
        this.shop_id = shop.getId();
        this.name = shop.getName();
    }

    public Shop toEntity(){
        return Shop.builder()
        .name(this.name)
        .build();
    }

}
