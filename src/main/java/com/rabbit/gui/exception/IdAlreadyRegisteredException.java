package com.rabbit.gui.exception;

public class IdAlreadyRegisteredException extends RuntimeException {

    public IdAlreadyRegisteredException() {
        super();
    }

    public IdAlreadyRegisteredException(String message) {
        super(message);
    }
    
}
