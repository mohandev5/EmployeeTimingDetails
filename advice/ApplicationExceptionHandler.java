package com.task.employeeSystem.advice;

import com.task.employeeSystem.exception.UserNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String>InvalidCredentials(MethodArgumentNotValidException exception){
        Map<String,String>error = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(X->{
            error.put(X.getField(),X.getDefaultMessage());
        });
        return error;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNameNotFoundException.class)
    public Map<String,String>BusinessException(UserNameNotFoundException ex){
       Map<String,String> errormap = new HashMap<>();
       errormap.put("errorMessage:", ex.getMessage());
       return errormap;
    }
}
