package com.peterson.helpdesk.exceptions;

import com.peterson.helpdesk.resources.exceptions.I18AbleException;

public class InvalidTokenRequestException extends I18AbleException {

    public InvalidTokenRequestException(String key, Object... args) {
        super(key, args);
    }
}
