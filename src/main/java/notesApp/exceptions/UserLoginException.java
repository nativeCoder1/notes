package notesApp.exceptions;

public class UserLoginException extends RuntimeException{
    public UserLoginException(String message){
        super(message);
    }
}
