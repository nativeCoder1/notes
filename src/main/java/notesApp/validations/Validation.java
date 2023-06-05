package notesApp.validations;

public class Validation {
    public static boolean validateMail(String mail){
        return mail.matches( "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
    }
    public static boolean validatePassword(String password){
        return password.matches("^(?=.*[0-9])(?=.*[a-z]).{8,20}$");
    }
    public static boolean validateUserName(String userName){
        return userName.length() != 0;
    }
}
