package guru.springfamework.controller.v1;

import guru.springfamework.service.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("resource not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({java.lang.NumberFormatException.class})
    public ResponseEntity<Object> HandleNumberFormatException(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("Invalid id", new HttpHeaders(), HttpStatus.NOT_FOUND);

    }
}
