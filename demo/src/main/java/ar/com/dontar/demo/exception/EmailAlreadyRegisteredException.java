package ar.com.dontar.demo.exception;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String message){
        super(message);
    }
}
