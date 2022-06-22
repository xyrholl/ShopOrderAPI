package kr.co._29cm.homework.api;

import kr.co._29cm.homework.application.OrderService;
import kr.co._29cm.homework.dto.OrderItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co._29cm.homework.model.APIMessage;
import kr.co._29cm.homework.model.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    /**
     * 주문 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<APIMessage> info(){
        return ResponseEntity
        .ok()
        .body(APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.orders())
            .build()
        );
    }
    
    /**
     * 주문 생성
     * @param orderItemDTOs 상품번호, 수량
     * @return Long orderId
     * @exception SoldOutException 재고 부족시 발생
     */
    @PostMapping("/order")
    public ResponseEntity<APIMessage> order(@RequestBody OrderItemDTO... orderItemDTOs){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.order(orderItemDTOs))
            .build()
        );
    }

    /**
     * 주문 조회
     * @param orderId 주문번호
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<APIMessage> order(@PathVariable("orderId") Long orderId){
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .status(Status.OK)
            .result_data(orderService.toOrderDTO(orderId))
            .build()
        );
    }

    /**
     * 주문 수정
     */
    @PutMapping("/order/{orderId}")
    public ResponseEntity<APIMessage> edit(@PathVariable("orderId") Long orderId, @RequestBody OrderItemDTO... orderItemDTOs){
        orderService.orderEdit(orderId, orderItemDTOs);
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .message(orderId + " 주문이 수정되었습니다.")
            .build()
        );
    }

    /**
     * 주문 취소
     */
    @PutMapping("/order/{orderId}/cancel")
    public ResponseEntity<APIMessage> orderCancel(@PathVariable("orderId") Long orderId){
        orderService.orderCancel(orderId);
        return ResponseEntity
        .ok()
        .body(
            APIMessage.builder()
            .message(orderId + " 주문이 취소되었습니다.")
            .build()
        );
    }

}
