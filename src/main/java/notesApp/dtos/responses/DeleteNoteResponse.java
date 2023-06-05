package notesApp.dtos.responses;

import lombok.Data;

@Data
public class DeleteNoteResponse {
    private int statusCode;
    private String message;
}
