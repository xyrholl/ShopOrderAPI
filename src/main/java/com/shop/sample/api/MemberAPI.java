package com.shop.sample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberAPI {

    @PostMapping("/member/create")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/members")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(null);
    }
    
}
