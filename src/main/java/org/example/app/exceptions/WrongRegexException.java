package org.example.app.exceptions;

public class WrongRegexException extends Exception{

    private final String message;

    public WrongRegexException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }

}
