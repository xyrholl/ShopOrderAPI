package com.shop.sample.exception;

public class PaymentCompleteException extends RuntimeException {

    public PaymentCompleteException(){
        super();
    }

    public PaymentCompleteException(String message){
        super(message);
    }

    public PaymentCompleteException(String message, Throwable cause){
        super(message, cause);
    }

    public PaymentCompleteException(Throwable cause){
        super(cause);
    }
    
}
