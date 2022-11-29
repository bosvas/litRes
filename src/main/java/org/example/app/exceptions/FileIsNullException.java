package org.example.app.exceptions;

public class FileIsNullException extends Exception{

    private final String message;

    public FileIsNullException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }


}
