package fr.ggautier.recettes.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException exception,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request
    ) {
        final Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                final String field = ((FieldError) error).getField();
                final String message = error.getDefaultMessage();
                errors.put(field, message);
            });

        return handleExceptionInternal(exception, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
}