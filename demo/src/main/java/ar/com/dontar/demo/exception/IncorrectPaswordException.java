package ar.com.dontar.demo.exception;

public class IncorrectPaswordException extends Exception{
    public IncorrectPaswordException(String message){
        super(message);
    }
}
