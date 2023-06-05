package notesApp.services;

import notesApp.data.models.AppUser;
import notesApp.data.models.Note;
import notesApp.dtos.requests.*;
import notesApp.dtos.responses.EmailResponse;
import notesApp.dtos.responses.ForgotPasswordResponse;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserRegistrationResponse register(UserRegistrationRequest registrationRequest);
    UserLoginResponse login(UserLoginRequest loginRequest);
    void addNote(String userName, Note note);
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);
    List<Note> getAllNotes(String userName);
    void deleteNote(String userName, Note note);
    AppUser findByUserName(String userName);
    List<Note> getUserNotesByTitle(FindUserNoteByTitleRequest findUserNoteByTitleRequest);
    EmailResponse sendEmail(EmailRequest emailRequest);
}
