package notesApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.services.NoteService;
import notesApp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@CrossOrigin(origins = "https://notes-b6ol.onrender.com")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/create_note")
    public ResponseEntity<ApiResponse> createNote(@RequestBody CreateNoteRequest createNoteRequest,
                                                         HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(noteService.createNote(createNoteRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete_note")
    public ResponseEntity<ApiResponse> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest,
                                         HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(noteService.deleteNote(deleteNoteRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/patch_note")
    public ResponseEntity<ApiResponse> editNote(@RequestBody EditNoteRequest editNoteRequest,
                                                HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(noteService.editNote(editNoteRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}