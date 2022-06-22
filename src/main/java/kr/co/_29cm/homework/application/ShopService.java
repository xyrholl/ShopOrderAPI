package kr.co._29cm.homework.application;

import java.util.List;
import java.util.stream.Collectors;

import kr.co._29cm.homework.domian.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.dao.ShopRepository;
import kr.co._29cm.homework.dto.ItemDTO;
import kr.co._29cm.homework.dto.ShopDTO;
import kr.co._29cm.homework.exception.NotFoundDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ItemService itemService;

    public List<ShopDTO> findShopDTOs(){
        return findShops().stream().map(ShopDTO::new).collect(Collectors.toList());
    }

    private List<Shop> findShops(){
        List<Shop> findList = shopRepository.findAll();
        if(findList.size() <= 0 ) throw new NotFoundDataException("등록된 상점이 없습니다.");
        return findList;
    }

    public Shop findShop(Long shopId){
        return shopRepository.findById(shopId)
            .orElseThrow(()-> new NotFoundDataException("샵을 찾을 수 없습니다."));
    }

    @Transactional
    public List<ItemDTO> findListItemDTOs(Long shopId){
        return itemService.findShopItems(findShop(shopId)).stream().map(ItemDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Long create(ShopDTO shopDTO){
        Shop shop = shopDTO.toEntity();
        return shopRepository.save(shop).getId();
    }
    
}
