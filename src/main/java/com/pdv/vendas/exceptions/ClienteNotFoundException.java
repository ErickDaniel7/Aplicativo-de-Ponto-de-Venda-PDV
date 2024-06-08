package com.pdv.vendas.exceptions;

public class ClienteNotFoundException extends RuntimeException{

    public ClienteNotFoundException(String message){
        super(message);
    }
}
