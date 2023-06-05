package notesApp.services;

import notesApp.data.models.AppUser;
import notesApp.exceptions.NoteException;
import notesApp.data.models.Note;
import notesApp.data.repositories.NoteRepository;
import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.DeleteNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;
import notesApp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Service
public class NoteServiceImpl implements NoteService{
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest){
        Note note = buildNote(createNoteRequest);
        Note savedNote = noteRepository.save(note);
        userService.addNote(createNoteRequest.getUserName(), savedNote);
        return getCreateNoteResponse(note);
    }

    private CreateNoteResponse getCreateNoteResponse(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setStatusCode(200);
        response.setMessage("Note created successfully");
        response.setNote(savedNote);
        response.setCreationDate(String.valueOf(LocalDate.now()));
        response.setCreationTime(String.valueOf(LocalTime.now()));
        return response;
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Note foundNote = noteRepository.findNoteById(deleteNoteRequest.getNoteId());
        if (foundNote == null) throw new NoteException("Note not found");
        AppUser user = userService.findByUserName(deleteNoteRequest.getUserName());
        if (user == null) throw new UserNotFoundException("User not found");
        userService.deleteNote(deleteNoteRequest.getUserName(), foundNote);
        noteRepository.delete(foundNote);
        DeleteNoteResponse deleteNoteResponse = buildDeleteNoteResponse();
        return deleteNoteResponse;
    }

    @Override
    public EditNoteResponse editNote(EditNoteRequest editNoteRequest) {
        Note foundNote = edit(editNoteRequest);
        Note editedNote = noteRepository.save(foundNote);
        AppUser user = userService.findByUserName(editNoteRequest.getUserName());
        if (user == null) throw new UserNotFoundException("User not found");
        List<Note> notes = userService.getAllNotes(editNoteRequest.getUserName());
        for (int i = 0; i < notes.size(); i++){
            if (Objects.equals(notes.get(i).getId(), foundNote.getId())){
                notes.set(i, editedNote);
            }
        }
        EditNoteResponse editNoteResponse = new EditNoteResponse();
        editNoteResponse.setStatusCode(200);
        editNoteResponse.setNote(editedNote);
        editNoteResponse.setMessage("Note edited successfully");
        return editNoteResponse;
    }

    private Note edit(EditNoteRequest editNoteRequest){
        Note foundNote = noteRepository.findNoteById(editNoteRequest.getNoteId());
        if (!Objects.equals(editNoteRequest.getTitle(), "")) foundNote.setTitle(editNoteRequest.getTitle());
        if (!Objects.equals(editNoteRequest.getContent(), "")) foundNote.setContent(editNoteRequest.getContent());
        foundNote.setEditStatus("Last edited");
        foundNote.setEditDate(String.valueOf(LocalDate.now()));
        foundNote.setEditTime(String.valueOf(LocalTime.now()));
        return foundNote;
    }
    private Note buildNote(CreateNoteRequest createNoteRequest){
        AppUser user = userService.findByUserName(createNoteRequest.getUserName());
        if (user == null) throw new UserNotFoundException("User not found");
        Note note = new Note();
        if (!Objects.equals(createNoteRequest.getTitle(), "")) {
            note.setTitle(createNoteRequest.getTitle());
        }else{
            throw new NoteException("Title cannot be empty");
        }
        if (!Objects.equals(createNoteRequest.getContent(), "")) {
            note.setContent(createNoteRequest.getContent());
        }else {
            throw new NoteException("Content cannot be empty");

        }
        note.setAppUser(user);
        return note;
    }
    private DeleteNoteResponse buildDeleteNoteResponse(){
        DeleteNoteResponse deleteNoteResponse = new DeleteNoteResponse();
        deleteNoteResponse.setStatusCode(200);
        deleteNoteResponse.setMessage("Note deleted succesfully");
        return deleteNoteResponse;
    }
}