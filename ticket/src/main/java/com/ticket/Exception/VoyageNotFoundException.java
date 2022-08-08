package com.ticket.Exception;

public class VoyageNotFoundException extends RuntimeException{
    public VoyageNotFoundException(String message) {
        super(message);
    }
}
