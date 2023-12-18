package com.peterson.helpdesk.exceptions;

import com.peterson.helpdesk.resources.exceptions.I18AbleException;

public class MailSendException extends I18AbleException {

    public MailSendException(String key, Object... args) {
        super(key, args);
    }
}
