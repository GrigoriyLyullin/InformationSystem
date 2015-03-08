package com.railwaycompany.business.services.exceptions;

public class TrainWithSuchNumberExistException extends Exception {

    public TrainWithSuchNumberExistException(String message) {
        super(message);
    }
}
