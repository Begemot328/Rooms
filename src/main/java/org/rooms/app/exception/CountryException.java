package org.rooms.app.exception;

public class CountryException extends ProjectException{

    public CountryException(String message) {
        super(message);
    }

    public CountryException(String message, Exception e) {
        super(message, e);
    }

    public CountryException(Exception e) {
        super(e);
    }
}
