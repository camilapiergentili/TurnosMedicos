package ar.com.dontar.demo.exception;

public class EmptyMedicalRecordException extends RuntimeException {
    public EmptyMedicalRecordException(String message) {
        super(message);
    }
}
