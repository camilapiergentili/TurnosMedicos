package ar.com.dontar.demo.model.exception;

public class UserNoExistException extends Exception{
    public UserNoExistException(String message){
        super(message);
    }
}
