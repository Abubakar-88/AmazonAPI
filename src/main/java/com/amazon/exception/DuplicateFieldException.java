package com.amazon.exception;

public class DuplicateFieldException extends RuntimeException{
    public DuplicateFieldException(String field, String value) {
        super(field + " with value "+ value + "is already in use,");
    }
}
