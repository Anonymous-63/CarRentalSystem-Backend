package com.anonymous63.crs.exceptions.handler;

import com.anonymous63.crs.dtos.ErrorDto;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.payloads.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle ResourceNotFoundException
     *
     * @param ex ResourceNotFoundException
     * @return APIResponse
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return APIResponse.<ErrorDto>builder()
                .status(false)
                .errors(Collections.singletonList(new ErrorDto("", ex.getMessage())))
                .build();
    }

}