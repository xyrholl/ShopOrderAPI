package com.shop.sample.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shop.sample.model.APIMessage;
import com.shop.sample.model.Status;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestAdviceException {

    @ExceptionHandler
    public ResponseEntity<APIMessage> notFoundData(NotFoundDataException e){
        log.info(e.getMessage());
        return ResponseEntity.ok().body(
            APIMessage.builder()
            .status(Status.OK)
            .message(e.getMessage())
            .build()
        );
    }
    
}
