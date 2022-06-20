package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sample.application.ItemService;
import com.shop.sample.application.ShopService;
import com.shop.sample.dto.ItemDTO;
import com.shop.sample.model.APIMessage;
import com.shop.sample.model.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;
    private final ShopService shopService;

    /**
     * 상품 목록조회
     */
    @GetMapping("/items")
    public ResponseEntity<APIMessage> itemList(){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .result_data(itemService.findDTOList())
            .build()
        );
    }

    /**
     * 단일 상품조회
     */
    @GetMapping("/item/{itemId}")
    public ResponseEntity<APIMessage> itemInfo(@PathVariable("itemId") Long itemId){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .status(Status.OK)
            .result_data(itemService.findOneItemDTO(itemId))
            .build()
        );
    }

    /**
     * 상품 생성
     */
    @PostMapping("/item/{shopId}")
    public ResponseEntity<APIMessage> itemAdd(@PathVariable("shopId") Long shopId, @RequestBody ItemDTO itemDTO){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .status(Status.OK)
            .result_data(itemService.create(shopService.findShop(shopId), itemDTO))
            .build()
        );
    }

}
