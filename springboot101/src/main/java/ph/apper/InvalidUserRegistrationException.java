package ph.apper;

public class InvalidUserRegistrationException extends Exception {
    public InvalidUserRegistrationException(String message) {
        super(message);
    }

    public InvalidUserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
