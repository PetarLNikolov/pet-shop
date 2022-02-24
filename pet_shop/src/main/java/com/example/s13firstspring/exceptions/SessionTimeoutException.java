package com.example.s13firstspring.exceptions;

public class SessionTimeoutException extends RuntimeException{
    public SessionTimeoutException(String msg) {
        super(msg);
    }
}
