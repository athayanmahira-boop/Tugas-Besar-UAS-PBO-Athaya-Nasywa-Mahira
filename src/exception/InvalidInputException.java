package exception;

// Custom Exception untuk validasi input
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
