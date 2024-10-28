package com.anonymous63.crs.exceptions.handler;

import com.anonymous63.crs.dtos.ErrorDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.payloads.response.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<List<ErrorDto>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorDto> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorDto(error.getField(), error.getDefaultMessage()))
                .toList();

        return APIResponse.<List<ErrorDto>>builder()
                .status(false)
                .message("Validation Failed")
                .errors(errorList)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<ErrorDto> handleDuplicateResourceException(DuplicateResourceException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .message("Unable to get JWT Token")
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<ErrorDto> handleExpiredJwtException(ExpiredJwtException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .message("JWT token has expired")
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<ErrorDto> handleSignatureException(SignatureException ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .message("Invalid JWT signature")
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse<ErrorDto> handleException(Exception ex) {
        return APIResponse.<ErrorDto>builder()
                .status(false)
                .message("Something went wrong")
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

}
