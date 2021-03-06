package ru.mgilivanov.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mgilivanov.project.exception.ApplicationException;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.Result;

import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Transactional
public class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result error(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new Result().setCode(Result.VALIDATION_ERROR_CODE).setMessage(errorMessage);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result error(HttpMessageNotReadableException e) {
        return new Result().setCode(Result.BAD_JSON_CODE).setMessage(Result.BAD_JSON_MESSAGE);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public Result error(BusinessLogicException e) {
        return new Result().setCode(e.getCode()).setMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public Result error(ApplicationException e) {
        return new Result().setCode(e.getCode()).setMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result error(TimeoutException e) {
        return new Result().setCode(Result.TIMEOUT_CODE).setMessage(Result.TIMEOUT_MESSAGE);
    }


}
