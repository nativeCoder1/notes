package notesApp.exceptions;

public class InvalidUserNameException extends UserRegistrationException {
    public InvalidUserNameException(String message) {
        super(message);
    }
}
