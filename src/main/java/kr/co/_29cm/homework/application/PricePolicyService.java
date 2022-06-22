package kr.co._29cm.homework.application;

import kr.co._29cm.homework.dao.PricePolicyRepository;
import org.springframework.stereotype.Service;

import kr.co._29cm.homework.domian.PricePolicy;
import kr.co._29cm.homework.exception.NotFoundDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricePolicyService {

    private final PricePolicyRepository pricePolicyRepository;

    public PricePolicy findPolicy(Long id){
        return pricePolicyRepository.findById(id)
            .orElseThrow(()-> new NotFoundDataException("해당 가격정책이 존재하지 않습니다."));
    }
    
}
