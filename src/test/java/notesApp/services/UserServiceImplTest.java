package notesApp.services;

import notesApp.dtos.requests.*;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;
import notesApp.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private UserRegistrationRequest userRegistrationRequest;
    private UserRegistrationRequest userRegistrationRequestWithInvalidUserName;
    private UserRegistrationRequest userRegistrationRequestWithInvalidMail;
    private UserRegistrationRequest userRegistrationRequestWithInvalidPassword;

    private UserRegistrationRequest userRegistrationRequestWithExistingUserName;
    private UserRegistrationRequest userRegistrationRequestWithExistingMailAddress;
    private UserLoginRequest userLoginRequest;
    private UserLoginRequest userLoginRequestWithUnregisteredMailAddress;
    private UserLoginRequest userLoginRequestWithWrongPassword;

    private FindUserNoteByTitleRequest findUserNoteByTitleRequest;
    @BeforeEach
    void setUp() {
        userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequestWithInvalidUserName = new UserRegistrationRequest();
        userRegistrationRequestWithInvalidMail = new UserRegistrationRequest();
        userRegistrationRequestWithInvalidPassword = new UserRegistrationRequest();
        userRegistrationRequestWithExistingUserName = new UserRegistrationRequest();
        userRegistrationRequestWithExistingMailAddress = new UserRegistrationRequest();
        userLoginRequest = new UserLoginRequest();
        userLoginRequestWithUnregisteredMailAddress = new UserLoginRequest();
        userLoginRequestWithWrongPassword = new UserLoginRequest();
        findUserNoteByTitleRequest = new FindUserNoteByTitleRequest();

        userRegistrationRequest.setUserName("mac8ver26");
        userRegistrationRequest.setEmail("mac8ver@gmail.com");
        userRegistrationRequest.setPassword("mac8ver26");

        userRegistrationRequestWithInvalidUserName.setUserName("");
        userRegistrationRequestWithInvalidUserName.setEmail("mac8r.com");
        userRegistrationRequestWithInvalidUserName.setPassword("hab4sure");

        userRegistrationRequestWithInvalidMail.setUserName("mac8ver");
        userRegistrationRequestWithInvalidMail.setEmail("mac8r.com");
        userRegistrationRequestWithInvalidMail.setPassword("hab4sure");

        userRegistrationRequestWithInvalidPassword.setUserName("mac8ver");
        userRegistrationRequestWithInvalidPassword.setEmail("mac8r@gmail.com");
        userRegistrationRequestWithInvalidPassword.setPassword("habgmail");

        userRegistrationRequestWithExistingUserName.setUserName("mac8ver");
        userRegistrationRequestWithExistingUserName.setEmail("mac8r@gmail.com");
        userRegistrationRequestWithExistingUserName.setPassword("hab4gmail");

        userRegistrationRequestWithExistingMailAddress.setUserName("mac9ver");
        userRegistrationRequestWithExistingMailAddress.setEmail("mac8ver@gmail.com");
        userRegistrationRequestWithExistingMailAddress.setPassword("hab4gmail");

        userLoginRequest.setEmail(userRegistrationRequest.getEmail());
        userLoginRequest.setPassword(userRegistrationRequest.getPassword());

        userLoginRequestWithUnregisteredMailAddress.setEmail("unregistered@gmail.com");
        userLoginRequestWithUnregisteredMailAddress.setPassword("hab4gmail");

        userLoginRequestWithWrongPassword.setEmail("mac8ver@gmail.com");
        userLoginRequestWithWrongPassword.setPassword("wrongPassword");

        findUserNoteByTitleRequest.setNoteTitle("This is a title");
        findUserNoteByTitleRequest.setUserName("mac8ver26");
    }

    @Test
    @DisplayName("Test that user can register")
    void testRegister() {
        UserRegistrationResponse registrationResponse = userService.register(userRegistrationRequest);
        assertEquals(200, registrationResponse.getStatusCode());
    }
    @Test
    @DisplayName("Test that exception is thrown when registering with empty name")
    void testRegistrationWithInvalidUserName(){
        assertThrows(InvalidUserNameException.class, ()-> userService.register(userRegistrationRequestWithInvalidUserName));
    }
    @Test
    @DisplayName("Test that exception is thrown when registering with invalid mail")
    void testRegistrationWithInvalidMail(){
        assertThrows(InvalidMailException.class, ()-> userService.register(userRegistrationRequestWithInvalidMail));
    }
    @Test
    @DisplayName("Test that exception is thrown when registering with invalid password")
    void testRegistrationWithInvalidPassword(){
        assertThrows(InvalidPasswordException.class, ()-> userService.register(userRegistrationRequestWithInvalidPassword));
    }
    @Test
    @DisplayName("Test that an exception is thrown for existing userName")
    void testRegistrationWithExistingUserName(){
        assertThrows(ExistingUserException.class, ()-> userService.register(userRegistrationRequest));
    }
    @Test
    @DisplayName("Test that an exception is thrown for existing mail address")
    void testRegistrationWithExistingMailAddress(){
        assertThrows(ExistingUserException.class, ()-> userService.register(userRegistrationRequestWithExistingMailAddress));
    }
    @Test
    @DisplayName("Test that registered user can log in")
    void testThatRegisteredUserCanLogIn(){
        UserLoginResponse loginResponse = userService.login(userLoginRequest);
        assertEquals(200, loginResponse.getStatusCode());
    }
    @Test
    @DisplayName("Test that exception is thrown when using unregistered mail address to login")
    void testLoginWithUnregisteredMailAddress(){
        assertThrows(UserLoginException.class, () ->userService.login(userLoginRequestWithUnregisteredMailAddress));
    }
    @Test
    @DisplayName("Test that exception is thrown when using wrong password to login")
    void testLoginWithUnregisteredPassword(){
        assertThrows(UserLoginException.class, () ->userService.login(userLoginRequestWithWrongPassword));
    }
}