package com.adatech.IMDB.config;

import com.adatech.IMDB.exception.ErroPadrao;
import com.adatech.IMDB.exception.FilmeNaoEncontradoException;
import com.adatech.IMDB.exception.ListaVaziaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdviceRest {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErroPadrao erro = new ErroPadrao();
        erro.setCodigoErro("400");
        erro.setMensagem("Erro de validação nos parâmetros de entrada.");
        erro.setDataHora(LocalDateTime.now());
        // Monta um map com os erros de cada campo
        Map<String, String> errors = new HashMap<>();
        errors.put("erro", ex.getMessage());
        erro.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(FilmeNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> handleFilmeNaoEncontradoException(FilmeNaoEncontradoException ex) {
        ErroPadrao erro = new ErroPadrao();
        erro.setCodigoErro("404");
        erro.setMensagem("Erro de validação nos parâmetros de entrada.");
        erro.setDataHora(LocalDateTime.now());
        // Monta um map com os erros de cada campo
        Map<String, String> errors = new HashMap<>();
        errors.put("erro", ex.getMessage());
        erro.setErrors(errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ListaVaziaException.class)
    public ResponseEntity<ErroPadrao> handleListaVaziaException(ListaVaziaException ex) {
        ErroPadrao erro = new ErroPadrao();
        erro.setCodigoErro("404");
        erro.setMensagem("Erro de validação nos parâmetros de entrada.");
        erro.setDataHora(LocalDateTime.now());
        // Monta um map com os erros de cada campo
        Map<String, String> errors = new HashMap<>();
        errors.put("erro", ex.getMessage());
        erro.setErrors(errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> handleValidationException(MethodArgumentNotValidException ex) {
        ErroPadrao erro = new ErroPadrao();
        erro.setCodigoErro("400");
        erro.setMensagem("Falha na validação do body do Json.");
        erro.setDataHora(LocalDateTime.now());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> {
            errors.put(field.getField(), field.getDefaultMessage());
        });

        erro.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}


