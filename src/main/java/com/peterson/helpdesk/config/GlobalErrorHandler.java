package com.peterson.helpdesk.config;

import com.peterson.helpdesk.domain.dtos.ErrorMessageResponse;
import com.peterson.helpdesk.domain.enums.ErrorTypes;
import com.peterson.helpdesk.exceptions.InvalidTokenRequestException;
import com.peterson.helpdesk.resources.exceptions.NoSuchElementFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j(topic = "GLOBAL_ERROR_HANDLER")
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    public static final String TRACE = "trace";

    private final MessageSource messageSource;

    public GlobalErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResponse<String> handleNoSuchElementFoundException(NoSuchElementFoundException e) {
        String message =
                messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new ErrorMessageResponse<>(ErrorTypes.VALIDATION, message);
    }
    @ExceptionHandler({InvalidTokenRequestException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessageResponse<String> onInvalidTokenRequestException(InvalidTokenRequestException e) {
        String message =
                messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new ErrorMessageResponse<>(ErrorTypes.INVALID_ARGUMENT, message);
    }

}
