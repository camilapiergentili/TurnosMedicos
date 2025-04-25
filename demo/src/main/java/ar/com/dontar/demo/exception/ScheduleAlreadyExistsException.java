package ar.com.dontar.demo.exception;

public class ScheduleAlreadyExistsException extends Exception {
    public ScheduleAlreadyExistsException(String message){
        super(message);
    }
}
