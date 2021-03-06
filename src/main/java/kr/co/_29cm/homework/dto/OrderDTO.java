package kr.co._29cm.homework.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co._29cm.homework.domian.Order;
import kr.co._29cm.homework.domian.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private Long order_id;
    private List<OrderItemDTO> items;
    private int order_price;
    private int shipping_fee;
    private int total_price;
    private int payment_price;
    private String status;
    private LocalDateTime order_time;

    public OrderDTO(Order order){
        this.order_id = order.getId();
        this.items = order.getOrderItems().stream().map(OrderItemDTO::new).collect(Collectors.toList());
        this.order_time = order.getPaymentTime();
        this.order_price = order.getOrderPrice();
        this.shipping_fee = order.getFare();
        this.total_price = order.getTotalPrice();
        this.payment_price = order.getPaymentPrice();
        orderStatus(order.getStatus());
    }

    private void orderStatus(OrderStatus orderStatus) {
        switch(orderStatus){
            case CANCEL:
                this.status = "주문취소";
                break;
            case WAIT:
                this.status = "주문대기";
                break;
            case ORDER:
                this.status = "주문완료";
                break;
            default:
                break;
        }
    }
    
}
