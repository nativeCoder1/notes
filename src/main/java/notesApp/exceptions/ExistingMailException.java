package notesApp.exceptions;

public class ExistingMailException extends UserRegistrationException {
    public ExistingMailException(String message) {
        super(message);
    }
}
