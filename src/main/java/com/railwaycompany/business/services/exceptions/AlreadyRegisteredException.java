package com.railwaycompany.business.services.exceptions;

public class AlreadyRegisteredException extends Exception {
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
