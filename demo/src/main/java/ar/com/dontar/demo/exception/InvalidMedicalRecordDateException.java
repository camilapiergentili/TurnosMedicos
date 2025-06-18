package ar.com.dontar.demo.exception;

public class InvalidMedicalRecordDateException extends RuntimeException {
    public InvalidMedicalRecordDateException(String message) {
        super(message);
    }
}
