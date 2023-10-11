package com.peterson.helpdesk.domain.dtos;

import com.peterson.helpdesk.domain.enums.ErrorTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse<T> {
    private ErrorTypes errorType;
    private T message;
}
