package ar.com.dontar.demo.exception;

public class AppointmentNotExistsException extends Exception {
    public AppointmentNotExistsException(String message){
        super(message);
    }
}
