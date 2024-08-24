package com.thiago.epost.exceptions;

import com.thiago.epost.exceptions.dto.ResponseErrorDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {
    private final MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ResponseErrorDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ResponseErrorDTO> responseErrorDTOList = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO(
                    message,
                    "Error",
                    HttpStatus.BAD_REQUEST.value(),
                    error.getField(),
                    LocalDateTime.now()
            );

            responseErrorDTOList.add(responseErrorDTO);
        });

        return new ResponseEntity<>(responseErrorDTOList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO(
                exception.getMessage(),
                "Error",
                HttpStatus.BAD_REQUEST.value(),
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseErrorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO(
                exception.getMessage(),
                "Error",
                HttpStatus.UNAUTHORIZED.value(),
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseErrorDTO, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleInvalidCredentialsException(ResourceNotFoundException exception) {
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO(
                exception.getMessage(),
                "Error",
                HttpStatus.NOT_FOUND.value(),
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseErrorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
