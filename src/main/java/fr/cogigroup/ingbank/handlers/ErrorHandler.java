package fr.cogigroup.ingbank.handlers;

import fr.cogigroup.ingbank.exceptions.NoSuchAccountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ErrorHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<Object> handleNoSuchAccountException(final NoSuchAccountException ex, WebRequest request) {
        String bodyOfResponse = "No account matched provided Id";
        return ResponseEntity.badRequest().body(bodyOfResponse+ex.getMessage());
    }
}