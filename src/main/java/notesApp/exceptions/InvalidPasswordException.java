package notesApp.exceptions;

public class InvalidPasswordException extends UserRegistrationException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
