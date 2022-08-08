package com.ticket.Exception;

public class IndividualMustBeLessThanFiveException extends RuntimeException {
    public IndividualMustBeLessThanFiveException(String message) {
        super(message);
    }
}
