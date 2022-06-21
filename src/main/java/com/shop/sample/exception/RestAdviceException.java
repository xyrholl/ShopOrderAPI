package com.shop.sample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @ExceptionHandler
    public ResponseEntity<APIMessage> noHandlerFound(NoHandlerFoundException e){
        return ResponseEntity.badRequest().body(
            APIMessage.builder()
            .status(Status.Bad_Request)
            .message(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIMessage> methodNotsupprot(HttpRequestMethodNotSupportedException e){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
            APIMessage.builder()
            .status(Status.Method_Not_Allowed)
            .message(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIMessage> paymentException(PaymentCompleteException e){
        return ResponseEntity.status(HttpStatus.OK).body(
            APIMessage.builder()
            .status(Status.OK)
            .message(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIMessage> soldOutException(SoldOutException e){
        return ResponseEntity.status(HttpStatus.OK).body(
            APIMessage.builder()
            .status(Status.OK)
            .message(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIMessage> messageNotReadable(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            APIMessage.builder()
            .status(Status.Bad_Request)
            .message(e.getMessage())
            .build() 
        );
    }
    
}
