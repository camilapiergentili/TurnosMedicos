package ar.com.dontar.demo.model.exception;

public class IncorrectPaswordException extends Exception{
    public IncorrectPaswordException(String message){
        super(message);
    }
}
