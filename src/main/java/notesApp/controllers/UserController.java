package notesApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import notesApp.dtos.requests.*;
import notesApp.services.UserService;
import notesApp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.ZonedDateTime;


@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegistrationRequest registrationRequest,
                                                HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(userService.register(registrationRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest loginRequest,
                                             HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(userService.login(loginRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/notes/{userName}")
    public ResponseEntity<ApiResponse> getUserNotes(@PathVariable String userName, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data(userService.getAllNotes(userName))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/noteTitle")
    public ResponseEntity<ApiResponse> findNotesByTitle(@RequestBody FindUserNoteByTitleRequest findUserNoteByTitleRequest,
                                                        HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data( userService.getUserNotesByTitle(findUserNoteByTitleRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/forgot_password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
                                                      HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data( userService.forgotPassword(forgotPasswordRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/email")
    public ResponseEntity<ApiResponse> sendEmail(@RequestBody EmailRequest emailRequest,
                                                 HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .data( userService.sendEmail(emailRequest))
                .statusCode(HttpStatus.OK)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}