package notesApp.dtos.responses;

import lombok.Data;

@Data
public class EmailResponse {
    private int statusCode;
    private String message;
    private String email;
}
