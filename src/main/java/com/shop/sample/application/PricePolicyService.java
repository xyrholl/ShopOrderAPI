package com.shop.sample.application;

import org.springframework.stereotype.Service;

import com.shop.sample.dao.PricePolicyRepository;
import com.shop.sample.domian.PricePolicy;
import com.shop.sample.exception.NotFoundDataException;

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
