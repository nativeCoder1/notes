package notesApp.dtos.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EditNoteRequest {
    private Long noteId;
    private String userName;
    private String title;
    private String content;
}