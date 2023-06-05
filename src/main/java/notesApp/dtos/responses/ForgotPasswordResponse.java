package notesApp.dtos.responses;

import lombok.Data;

@Data
public class ForgotPasswordResponse {
    private int statusCode;
    private String message;
}
