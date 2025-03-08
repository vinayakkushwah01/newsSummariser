package com.newsSummeriser.exception;

public class DuplicateNewsException extends RuntimeException {
    public DuplicateNewsException(String message) {
        super(message);
    }
}

