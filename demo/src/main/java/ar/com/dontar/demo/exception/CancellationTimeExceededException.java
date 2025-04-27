package ar.com.dontar.demo.exception;

public class CancellationTimeExceededException extends Exception {
    public CancellationTimeExceededException(String message) {
        super(message);
    }
}
