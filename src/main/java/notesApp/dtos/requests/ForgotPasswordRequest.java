package notesApp.dtos.requests;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String newPassword;
    private String confirmPassword;
    private String token;
}
