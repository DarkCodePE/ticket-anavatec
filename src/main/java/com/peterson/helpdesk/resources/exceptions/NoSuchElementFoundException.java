package com.peterson.helpdesk.resources.exceptions;

public class NoSuchElementFoundException extends I18AbleException{

    public NoSuchElementFoundException(String key, Object... args) {
        super(key, args);
    }
}