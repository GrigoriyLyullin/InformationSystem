package com.railwaycompany.business.services.exceptions;

public class InvalidInputDataException extends Exception {


    public InvalidInputDataException(String message) {
        super(message);
    }

    public InvalidInputDataException(Throwable cause) {
        super(cause);
    }

    public InvalidInputDataException(String message, Throwable e) {
        super(message, e);
    }
}
