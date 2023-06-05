package notesApp.exceptions;

public class InvalidMailException extends UserRegistrationException {

    public InvalidMailException(String message) {
        super(message);
    }
}
