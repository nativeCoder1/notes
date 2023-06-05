package notesApp.services;

import lombok.RequiredArgsConstructor;
import notesApp.dtos.requests.*;
import notesApp.dtos.responses.EmailResponse;
import notesApp.dtos.responses.ForgotPasswordResponse;
import notesApp.exceptions.*;
import notesApp.data.models.Note;
import notesApp.data.models.AppUser;
import notesApp.data.repositories.UserRepository;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;
import notesApp.validations.Validation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest registrationRequest){
        AppUser builtUser = buildUser(registrationRequest);
        userRepository.save(builtUser);
        UserRegistrationResponse registrationResponse = buildResponse();
        return registrationResponse;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        AppUser foundUser = userRepository.findByEmail(loginRequest.getEmail().toLowerCase());
        if (foundUser == null){
            userLoginResponse.setStatusCode(400);
            userLoginResponse.setMessage("Authentication failed");
            throw new UserLoginException("User not found");
        }
        authenticateUser(loginRequest, userLoginResponse, foundUser);
        return userLoginResponse;
    }
    @Override
    public void addNote(String userName, Note note) {
        AppUser foundUser = userRepository.findByUserName(userName);
        foundUser.getNotes().add(note);
        userRepository.save(foundUser);
    }

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        AppUser foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        ForgotPasswordResponse response = new ForgotPasswordResponse();
        if (!foundUser.getToken().equals(request.getToken())){
            throw new UserNotFoundException("Invalid user");
        }
        if (request.getConfirmPassword().equals(request.getNewPassword())){
            foundUser.setPassword(hashPassword(request.getConfirmPassword()));
            userRepository.save(foundUser);
            response.setMessage("Password reset successful");
            response.setStatusCode(201);
        }else{
            response.setMessage("Password reset fail");
            response.setStatusCode(401);
        }
        return response;
    }

    @Override
    public void deleteNote(String userName, Note note){
        AppUser foundUser = userRepository.findByUserName(userName);
        foundUser.getNotes().remove(note);
        userRepository.save(foundUser);
    }

    @Override
    public AppUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<Note> getUserNotesByTitle(FindUserNoteByTitleRequest findUserNoteByTitleRequest) {
        AppUser foundUser = userRepository.findByUserName(findUserNoteByTitleRequest.getUserName());
        String title = findUserNoteByTitleRequest.getNoteTitle();
        List<Note> foundNotes = foundUser.getNotes();
        List<Note> newNotes = findNotes(title, foundNotes);
        return newNotes;
    }

    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        AppUser user = userRepository.findByEmail(emailRequest.getEmail());
        if (user == null) throw new UserNotFoundException("Email doesn't exist");
        String token = generateToken();
        emailService.send(emailRequest.getEmail(), "Use this token to reset your password + \n"+token);
        user.setToken(token);
        userRepository.save(user);
        EmailResponse response = new EmailResponse();
        response.setMessage("Email sent successfully");
        response.setEmail(emailRequest.getEmail());
        response.setStatusCode(200);
        return response;
    }
    private String generateToken(){
        StringBuilder token = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 9; i++){
            int num = random.nextInt(9);
            token.append(num);
        }
        return token.toString();
    }
    private List<Note> findNotes(String title, List<Note> foundNotes) {
        List<Note> newNotes = new ArrayList<>();
        for (Note note: foundNotes){
            if (note.getTitle().contains(title)){
                newNotes.add(note);
            }
        }
        return newNotes;
    }

    @Override
    public List<Note> getAllNotes(String userName) {
        AppUser foundUser = userRepository.findByUserName(userName);
        return foundUser.getNotes();
    }


    private void authenticateUser(UserLoginRequest loginRequest, UserLoginResponse userLoginResponse, AppUser foundUser) {
        if (confirmPassword(loginRequest.getPassword(), foundUser.getPassword())){
            userLoginResponse.setStatusCode(200);
            userLoginResponse.setUserName(foundUser.getUserName());
            userLoginResponse.setMessage("You're logged in");
        }else {
            userLoginResponse.setStatusCode(400);
            userLoginResponse.setMessage("Authentication failed");
            throw new UserLoginException("Incorrect password");
        }
    }

    private AppUser buildUser(UserRegistrationRequest registrationRequest){
        AppUser savedUser = userRepository.findByUserName(registrationRequest.getUserName());
        if (savedUser != null) {
            throw new ExistingUserException("User already exists!");
        }
        AppUser foundUser = userRepository.findByEmail(registrationRequest.getEmail().toLowerCase());
        if (foundUser != null) {
            throw new ExistingUserException("User already exists!");
        }

        AppUser user = new AppUser();
        validateUserRegistrationDetails(registrationRequest);
        user.setUserName(registrationRequest.getUserName());
        user.setEmail(registrationRequest.getEmail().toLowerCase());
        user.setPassword(hashPassword(registrationRequest.getPassword()));
        return user;
    }

    private void validateUserRegistrationDetails(UserRegistrationRequest registrationRequest) {
        if (!Validation.validateUserName(registrationRequest.getUserName()))throw new InvalidUserNameException("Empty user name");
        if (!Validation.validateMail(registrationRequest.getEmail())) throw new InvalidMailException("Invalid mail");
        if (!Validation.validatePassword(registrationRequest.getPassword()))throw new InvalidPasswordException("Invalid password");
    }

    private UserRegistrationResponse buildResponse(){
        UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
        registrationResponse.setStatusCode(200);
        registrationResponse.setMessage("Registration successful. Proceed to login page");
        return registrationResponse;
    }
    private static String hashPassword(String userPassword){
        String password = userPassword;
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private static boolean confirmPassword(String candidate, String password){
        return BCrypt.checkpw(candidate, password);
    }

}
