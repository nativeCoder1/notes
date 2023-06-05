package notesApp.dtos.responses;

import lombok.Data;

@Data
public class UserLoginResponse {
    private int statusCode;
    private String userName;
    private String message;
}
