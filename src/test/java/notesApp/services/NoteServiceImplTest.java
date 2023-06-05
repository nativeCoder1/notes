package notesApp.services;

import notesApp.data.models.Note;
import notesApp.data.repositories.NoteRepository;
import notesApp.dtos.requests.*;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class NoteServiceImplTest {
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;
    private CreateNoteRequest createNoteRequest;
    private EditNoteRequest editNoteRequest;
    private DeleteNoteRequest deleteNoteRequest;

    @BeforeEach
    void setUp(){
        createNoteRequest = new CreateNoteRequest();
        editNoteRequest = new EditNoteRequest();
        deleteNoteRequest = new DeleteNoteRequest();
    }

    @Test
    @DisplayName("Test that a registered user can create a note")
    void testThatANoteCanBeCreated(){
       UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
       userRegistrationRequest.setUserName("habeeb");
       userRegistrationRequest.setEmail("habeeb@gmail.com");
       userRegistrationRequest.setPassword("hab4sure");
       userService.register(userRegistrationRequest);

       createNoteRequest.setUserName(userRegistrationRequest.getUserName());
       createNoteRequest.setTitle("This is a title");
       createNoteRequest.setContent("This is a content");

       CreateNoteResponse response = noteService.createNote(createNoteRequest);
       assertEquals(200, response.getStatusCode());

    }
    @Test
    @DisplayName("Test that a user can edit a note")
    void testThatANoteCanBeEdited(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUserName("habb");
        userRegistrationRequest.setEmail("habb@gmail.com");
        userRegistrationRequest.setPassword("hab4sure");
        userService.register(userRegistrationRequest);

        createNoteRequest.setUserName(userRegistrationRequest.getUserName());
        createNoteRequest.setTitle("This is a title");
        createNoteRequest.setContent("This is a content");

        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertEquals(200, response.getStatusCode());

        editNoteRequest.setNoteId(response.getNote().getId());
        editNoteRequest.setTitle("Edited note title");
        editNoteRequest.setContent("Edited note content");
        editNoteRequest.setUserName(createNoteRequest.getUserName());
        EditNoteResponse editNoteResponse = noteService.editNote(editNoteRequest);
        assertEquals("Edited note title", editNoteResponse.getNote().getTitle());
        assertEquals("Edited note content", editNoteResponse.getNote().getContent());
    }
    @Test
    @DisplayName("Test that a user can delete a note")
    void testThatANoteCanBeDeleted(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUserName("habbeebb");
        userRegistrationRequest.setEmail("habbeebb@gmail.com");
        userRegistrationRequest.setPassword("hab4sure");
        userService.register(userRegistrationRequest);


        createNoteRequest.setUserName(userRegistrationRequest.getUserName());
        createNoteRequest.setTitle("This is a newww title");
        createNoteRequest.setContent("This is a neww content");

        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertEquals(200, response.getStatusCode());

        DeleteNoteRequest deleteNoteRequest1 = new DeleteNoteRequest();
        deleteNoteRequest1.setNoteId(response.getNote().getId());
        deleteNoteRequest1.setUserName(userRegistrationRequest.getUserName());
        noteService.deleteNote(deleteNoteRequest1);

        FindUserNoteByTitleRequest findUserNoteByTitleRequest = new FindUserNoteByTitleRequest();
        findUserNoteByTitleRequest.setUserName(userRegistrationRequest.getUserName());
        findUserNoteByTitleRequest.setNoteTitle("This is a neww title");
        assertEquals(0, userService.getUserNotesByTitle(findUserNoteByTitleRequest).size());
    }
    @Test
    @DisplayName("Test that user can get note(s) by title")
    void testThatNoteCanBeGottenByTiTle(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUserName("Mellow");
        userRegistrationRequest.setEmail("mellow@gmail.com");
        userRegistrationRequest.setPassword("hab4sure");
        userService.register(userRegistrationRequest);


        createNoteRequest.setUserName(userRegistrationRequest.getUserName());
        createNoteRequest.setTitle("This is a neww title");
        createNoteRequest.setContent("This is a neww content");

        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertEquals(200, response.getStatusCode());

        FindUserNoteByTitleRequest findUserNoteByTitleRequest = new FindUserNoteByTitleRequest();
        findUserNoteByTitleRequest.setUserName(userRegistrationRequest.getUserName());
        findUserNoteByTitleRequest.setNoteTitle(createNoteRequest.getTitle());
        List<Note> foundNote = userService.getUserNotesByTitle(findUserNoteByTitleRequest);
        assertEquals("This is a neww title", foundNote.get(0).getTitle());
        assertEquals("This is a neww content", foundNote.get(0).getContent());
    }
}
