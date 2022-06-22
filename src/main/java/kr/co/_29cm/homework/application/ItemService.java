package kr.co._29cm.homework.application;

import java.util.List;
import java.util.stream.Collectors;

import kr.co._29cm.homework.domian.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.dao.ItemRepository;
import kr.co._29cm.homework.domian.Item;
import kr.co._29cm.homework.dto.ItemDTO;
import kr.co._29cm.homework.exception.NotFoundDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    List<Item> findShopItems(Shop shop){
        List<Item> findList = itemRepository.findByShopId(shop.getId());
        if(findList.size() <= 0) throw new NotFoundDataException("해당 상점에 등록된 상품이 없습니다.");
        return findList;
    }

    @Transactional
    public List<ItemDTO> findDTOList(){
        List<Item> findList = itemRepository.findAll();
        if(findList.size() <= 0 ) throw new NotFoundDataException("등록된 상품이 없습니다.");
        return findList.stream().map(ItemDTO::new).collect(Collectors.toList());
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundDataException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    public ItemDTO findOneItemDTO(Long itemId){
        return new ItemDTO(findOne(itemId));
    }

    @Transactional
    public Long create(Shop shop, ItemDTO itemDTO){
        Item item = itemDTO.toEntity(shop);
        return itemRepository.save(item).getId();
    }
    
}
