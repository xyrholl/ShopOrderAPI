package com.shop.sample.domian;

import com.shop.sample.dto.OrderDTO;
import com.shop.sample.dto.OrderItemDTO;
import com.shop.sample.exception.NotEnoughQuantityException;
import com.shop.sample.exception.NotFoundDataException;
import com.shop.sample.repository.ItemRepository;
import com.shop.sample.repository.MemberRepository;
import com.shop.sample.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Member createMember(){
        return Member.builder().id("test_id").name("lee").build();
    }

    private List<Item> createItems(){
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).name("책").price(16000).itemStatus(ItemStatus.SALE).stockQuantity(3).build());
        items.add(Item.builder().id(2L).name("음료수").price(800).itemStatus(ItemStatus.SALE).stockQuantity(100).build());
        items.add(Item.builder().id(3L).name("키보드").price(220000).itemStatus(ItemStatus.SALE).stockQuantity(5).build());
        items.add(Item.builder().id(4L).name("모니터").price(450000).itemStatus(ItemStatus.SALE).stockQuantity(3).build());
        return items;
    }


    @Test
    void 단일_상품주문() {
        //given
        Member member = createMember();
        List<Item> items = createItems();
        memberRepository.save(member);
        itemRepository.saveAll(items);
        System.out.println();

        Member findMember = memberRepository.findById("test_id")
            .orElseThrow(() -> new NotFoundDataException("존재하지않는 회원입니다."));
        Item findItem = itemRepository.findById(1L)
            .orElseThrow(() -> new NotFoundDataException("상품이 존재하지 않습니다."));

        //when
        OrderItem orderItem = OrderItem.createOrderItem(findItem, 2);
        Order order = Order.createOrder(findMember, orderItem);
        orderRepository.save(order);

        //then
        List<Order> orders = orderRepository.findAll();
        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getMember().getName()).isEqualTo("lee");
        Assertions.assertThat(orders.get(0).getOrderItems().get(0).getItem().getName()).isEqualTo("책");
        Assertions.assertThat(orders.get(0).getTotalPrice()).isEqualTo(32000);

    }

    private List<OrderItem> createOrderItems(List<OrderItemDTO> orderItemDtos){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDtos) {
            Optional<Item> findItem = itemRepository.findById(orderItemDTO.getItemId());
            if(findItem.isPresent())orderItems.add(OrderItem.createOrderItem(findItem.get(), orderItemDTO.getCount()));
        }
        return orderItems;
    }

    @Test
    void 다중_상품주문(){
        //given
        Member member = createMember();
        memberRepository.save(member);
        List<Item> items = createItems();
        itemRepository.saveAll(items);
        OrderItemDTO item1 = new OrderItemDTO(1L, 1);
        OrderItemDTO item2 = new OrderItemDTO(2L, 22);
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        orderItemDTOs.add(item1);
        orderItemDTOs.add(item2);

        OrderDTO orderDTO = new OrderDTO("test_id", orderItemDTOs);
        
        //when
        Member findMember = memberRepository.findById(orderDTO.getMemberId())
            .orElseThrow(() -> new NotFoundDataException("존재하지않는 회원입니다."));
        List<OrderItem> orderItems = createOrderItems(orderDTO.getItemDTOs());
        Order order = Order.createOrder(findMember, orderItems.stream().toArray(OrderItem[]::new));
        orderRepository.save(order);

        //then
        List<Order> orders = orderRepository.findAll();
        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getOrderItems().size()).isEqualTo(2);
        Assertions.assertThat(orders.get(0).getTotalPrice()).isEqualTo(33600);
    }

    private void constru(int id, String... str){
    }

    @Test
    void 가변인수생성자(){
        constru(1, new String[]{});
    }

    @Test
    void 결제완료시_재고수량초과(){
        //given
        단일_상품주문();
        Item item =  itemRepository.findById(1L)
                .orElseThrow(()-> new NotFoundDataException("아이템이 없습니다."));
        item.removeStock(2);
        
        //when
        Order order = orderRepository.findAll().get(0);
        NotEnoughQuantityException thrown = assertThrows(NotEnoughQuantityException.class,
                () -> order.completePyment());

        //then
        assertEquals(thrown.getMessage(), "재고가 소진되어 수량이 모자랍니다.");
    }

}