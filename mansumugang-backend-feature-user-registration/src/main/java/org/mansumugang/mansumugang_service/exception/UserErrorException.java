package org.mansumugang.mansumugang_service.exception;

import lombok.Getter;
import org.mansumugang.mansumugang_service.constant.ErrorType;

@Getter
public class UserErrorException extends RuntimeException{
    private final ErrorType errorType;
    public UserErrorException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
