package notesApp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<?> globalExceptionHandler(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
