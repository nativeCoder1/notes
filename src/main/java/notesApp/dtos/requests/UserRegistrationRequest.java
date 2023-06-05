package notesApp.dtos.requests;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String userName;
    private String email;
    private String password;
}