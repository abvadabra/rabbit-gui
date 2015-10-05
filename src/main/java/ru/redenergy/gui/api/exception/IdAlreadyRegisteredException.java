package ru.redenergy.gui.api.exception;

public class IdAlreadyRegisteredException extends RuntimeException {

    public IdAlreadyRegisteredException() {
        super();
    }

    public IdAlreadyRegisteredException(String message) {
        super(message);
    }
    
}
