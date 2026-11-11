package com.grgks.product_catalog.core;

import com.grgks.product_catalog.core.exceptions.ProductNotFoundException;
import com.grgks.product_catalog.dto.ResponseMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Override parent's handleMethodArgumentNotValid instead of using @ExceptionHandler.
     * ResponseEntityExceptionHandler already handles MethodArgumentNotValidException internally.
     * In Spring Boot 4.x, using @ExceptionHandler for the same exception causes a conflict.
     * Overriding ensures a single handler and consistent behavior.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

//        System.err.println("Validation error occurred: {}" + " " + ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ResponseMessageDTO> handleNotFoundException(ProductNotFoundException e){
        return new ResponseEntity<>(ResponseMessageDTO.error(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
