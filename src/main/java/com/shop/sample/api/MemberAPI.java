package com.shop.sample.api;

import com.shop.sample.model.APIMessage;
import com.shop.sample.model.MemberDTO;
import com.shop.sample.model.Status;
import com.shop.sample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberAPI {

    private final MemberService memberService;

    @PostMapping("/member/create")
    public ResponseEntity<APIMessage> create(MemberDTO memberDTO){
        return ResponseEntity
                .ok()
                .body(APIMessage.builder()
                        .status(Status.OK)
                        .result_data(memberService.create(memberDTO))
                        .build()
        );
    }

    @GetMapping("/members")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(null);
    }

}
