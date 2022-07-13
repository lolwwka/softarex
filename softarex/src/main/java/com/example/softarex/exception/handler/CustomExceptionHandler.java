package com.example.softarex.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.softarex.dto.ErrorMessage;
import com.example.softarex.exception.custom.EmailInUseException;
import com.example.softarex.exception.custom.IncorrectIdException;
import com.example.softarex.exception.custom.IncorrectMailException;
import com.example.softarex.exception.custom.IncorrectUserPassException;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<Object> handleEmailException(EmailInUseException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<Object> handleUiException(IncorrectIdException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IncorrectMailException.class)
    public ResponseEntity<Object> handleMailException(IncorrectMailException exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectUserPassException.class)
    public ResponseEntity<Object> handleIncorrectPassException(IncorrectUserPassException exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> handleMessageServerException(MessagingException exception){
        ErrorMessage errorMessage = new ErrorMessage("Problems with mail service");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorMessage error = new ErrorMessage("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
