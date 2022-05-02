package com.cs307.project.service.ex;

public class MismatchSupplyCenterException extends ServiceException{
    public MismatchSupplyCenterException() {
        super();
    }

    public MismatchSupplyCenterException(String message) {
        super(message);
    }

    public MismatchSupplyCenterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchSupplyCenterException(Throwable cause) {
        super(cause);
    }

    protected MismatchSupplyCenterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
