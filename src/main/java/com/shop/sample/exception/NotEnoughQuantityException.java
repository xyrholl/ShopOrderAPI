package com.shop.sample.exception;

public class NotEnoughQuantityException extends RuntimeException {
    
    public NotEnoughQuantityException(){
        super();
    }

    public NotEnoughQuantityException(String message){
        super(message);
    }

    public NotEnoughQuantityException(String message, Throwable cause){
        super(message, cause);
    }

    public NotEnoughQuantityException(Throwable cause){
        super(cause);
    }
}
