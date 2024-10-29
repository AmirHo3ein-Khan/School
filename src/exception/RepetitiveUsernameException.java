package exception;

public class RepetitiveUsernameException extends RuntimeException{
    public RepetitiveUsernameException(String message) {
        super(message);
    }
}
