package com.tranhuudat.nuclearshop.exception;

import com.tranhuudat.nuclearshop.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class, IllegalAccessException.class,
            DataIntegrityViolationException.class,NuclearShopException.class, UsernameNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest webRequest) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(webRequest.getContextPath())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()+"")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DisabledException.class, BadCredentialsException.class})
    public final ResponseEntity<ErrorResponse> handleSecurityException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getContextPath())
                .code(HttpStatus.BAD_REQUEST.value()+"")
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Invalid method argument at {}",LocalDateTime.now());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getContextPath())
                .code(status.value()+"")
                .status(status)
                .message("Invalid request body")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
        return new ResponseEntity(errorResponse, headers, status);
    }

}
