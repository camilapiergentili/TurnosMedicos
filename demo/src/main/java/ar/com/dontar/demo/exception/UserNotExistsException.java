package ar.com.dontar.demo.exception;

public class UserNotExistsException extends Exception{
    public UserNotExistsException(String message){
        super(message);
    }
}
