package com.pdv.vendas.exceptions;

public class VendaNotFoundException  extends RuntimeException{
    public VendaNotFoundException(String message){
        super (message);
    }
}
