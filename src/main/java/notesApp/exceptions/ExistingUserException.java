package notesApp.exceptions;

public class ExistingUserException extends UserRegistrationException {

    public ExistingUserException(String message) {
        super(message);
    }
}
