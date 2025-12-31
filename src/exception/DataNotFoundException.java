package exception;

// Custom Exception jika data tidak ditemukan
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}

