package notesApp.dtos.requests;

import lombok.Data;

@Data
public class FindUserNoteByTitleRequest {
    private String noteTitle;
    private String userName;
}
