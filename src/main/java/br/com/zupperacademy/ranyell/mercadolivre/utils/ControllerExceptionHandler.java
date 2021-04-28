package br.com.zupperacademy.ranyell.mercadolivre.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> validation(MethodArgumentNotValidException e) {
        var erros = new ArrayList<ValidationError>();
        for(FieldError erro : e.getBindingResult().getFieldErrors()) {
            erros.add(new ValidationError(erro.getField(), erro.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(erros);
    }
}
