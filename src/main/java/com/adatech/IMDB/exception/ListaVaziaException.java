package com.adatech.IMDB.exception;

public class ListaVaziaException extends RuntimeException {
    public ListaVaziaException(String mensagem) {
        super(mensagem);
    }
}
