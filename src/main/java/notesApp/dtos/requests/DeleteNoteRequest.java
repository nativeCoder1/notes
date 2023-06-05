package notesApp.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private Long noteId;
    private String userName;
}