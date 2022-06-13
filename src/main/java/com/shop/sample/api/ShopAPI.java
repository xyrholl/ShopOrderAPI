package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sample.dto.APIMessage;
import com.shop.sample.dto.ShopDTO;
import com.shop.sample.dto.Status;
import com.shop.sample.service.ShopService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class ShopAPI {
    
    private final ShopService shopService;

    /**
     * 상점 조회
     */
    @GetMapping("/shops")
    public ResponseEntity<APIMessage> shopsInfo(){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .build()
        );
    }

    /**
     * 상점 상품조회
     */
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<?> shopItems(@PathVariable("shopId") Long shopId){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .status(Status.OK)
            .result_data(shopService.findListItemDTOs(shopId))
            .build()
        );
    }

    /**
     * 상점 생성
     */
    @PostMapping("/shop")
    public ResponseEntity<APIMessage> shopAdd(@RequestBody ShopDTO shopDTO) {
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .result_data(shopService.create(shopDTO))
            .build()
        );
    }
    


}
