package notesApp.dtos.responses;

import lombok.Data;
import notesApp.data.models.Note;

@Data
public class EditNoteResponse {
    private int statusCode;
    private String message;
    private Note note;
}
