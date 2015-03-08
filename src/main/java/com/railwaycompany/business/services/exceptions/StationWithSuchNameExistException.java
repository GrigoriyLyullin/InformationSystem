package com.railwaycompany.business.services.exceptions;

public class StationWithSuchNameExistException extends Exception {

    public StationWithSuchNameExistException(String message) {
        super(message);
    }
}
