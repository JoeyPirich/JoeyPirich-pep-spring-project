package com.example.exception;

public class UsernameTakenException extends Exception {
    public UsernameTakenException() {
        super();
    }

    public UsernameTakenException(String m) {
        super(m);
    }
}
