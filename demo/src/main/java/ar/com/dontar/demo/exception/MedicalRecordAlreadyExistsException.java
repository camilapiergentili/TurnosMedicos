package ar.com.dontar.demo.exception;

public class MedicalRecordAlreadyExistsException extends RuntimeException {
  public MedicalRecordAlreadyExistsException(String message) {
    super(message);
  }
}
