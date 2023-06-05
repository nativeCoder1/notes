package notesApp.services;

import notesApp.data.models.Note;
import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.DeleteNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;

import java.time.LocalDate;
import java.util.List;

public interface NoteService {
    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);
    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);
    EditNoteResponse editNote(EditNoteRequest editNoteRequest);
}
