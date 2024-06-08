package com.pdv.vendas.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity tratarProdutoNaoEncontrado(ProdutoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErros(ex.getMessage()));
    }

    @ExceptionHandler(VendaNotFoundException.class)
    public ResponseEntity tratarVendaNaoEncontrado(VendaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErros(ex.getMessage()));
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity tratarClienteNaoEncontrado(ClienteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErros(ex.getMessage()));
    }

    private record DadosErroValidacao(String field, String message) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


    private record DadosErros(String message) {
    }


}
