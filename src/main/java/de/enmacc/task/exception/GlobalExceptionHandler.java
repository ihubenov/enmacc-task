package de.enmacc.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.Collections.singletonList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
        ErrorResponse response = new ErrorResponse(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContractAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleContractAlreadyExistsException(ContractAlreadyExistsException ex) {
        String errorMessage = String.format("A contract between companies %s and %s already exists",
                ex.getCompanyA(), ex.getCompanyB());
        ErrorResponse response = new ErrorResponse(singletonList(errorMessage));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
