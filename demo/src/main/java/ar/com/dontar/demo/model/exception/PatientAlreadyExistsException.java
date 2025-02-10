package ar.com.dontar.demo.model.exception;

public class PatientAlreadyExistsException extends Exception{
    public PatientAlreadyExistsException(String message){
        super(message);
    }
}
