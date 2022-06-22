package kr.co._29cm.homework.exception;

public class NotFoundDataException extends RuntimeException{

    public NotFoundDataException(){
        super();
    }

    public NotFoundDataException(String message){
        super(message);
    }

    public NotFoundDataException(String message, Throwable cause){
        super(message, cause);
    }

    public NotFoundDataException(Throwable cause){
        super(cause);
    }
}
