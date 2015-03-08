package com.railwaycompany.business.services.exceptions;

public class HasNoEmptySeatsException extends Exception {

    public HasNoEmptySeatsException() {
    }

    public HasNoEmptySeatsException(String message) {
        super(message);
    }
}
