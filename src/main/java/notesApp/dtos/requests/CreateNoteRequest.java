package notesApp.dtos.requests;

import lombok.Data;


@Data
public class CreateNoteRequest {
    private String userName;
    private String title;
    private String content;
    private String editStatus="";
}