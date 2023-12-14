package com.anime.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AnimeExceptionHandler {
    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<Object> objectsResponseEntity(NaoEncontradoException encontradoException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(encontradoException.getMessage());
    }
}
