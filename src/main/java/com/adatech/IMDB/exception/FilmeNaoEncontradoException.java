package com.adatech.IMDB.exception;

public class FilmeNaoEncontradoException extends RuntimeException{
    public FilmeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
