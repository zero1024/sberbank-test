package com.sberbank.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@ControllerAdvice
class ExceptionHandlingController {


    /**
     * Ошибки консистентности базы
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorDto handleConflict(DataIntegrityViolationException e) {
        return ErrorDto.create(e.getMessage());
    }

    /**
     * Ошибки валидации RequestBody
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorDto handleBadRequest(MethodArgumentNotValidException e) {
        return ErrorDto.create(e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(toList()));

    }

    /**
     * Spring не в состоянии распарсить RequestBody
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorDto handleParseError(HttpMessageNotReadableException e) {
        return ErrorDto.create("Bad request");
    }

    public static class ErrorDto {

        private final List<String> messages;

        public static ErrorDto create(String msg) {
            return new ErrorDto(singletonList(msg));
        }

        public static ErrorDto create(List<String> messages) {
            return new ErrorDto(messages);
        }

        private ErrorDto(List<String> messages) {
            this.messages = messages;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

}
