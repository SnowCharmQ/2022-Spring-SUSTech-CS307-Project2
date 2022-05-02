package com.cs307.project.service.ex;

public class SupplyCenterNotFoundException extends ServiceException{
    public SupplyCenterNotFoundException() {
        super();
    }

    public SupplyCenterNotFoundException(String message) {
        super(message);
    }

    public SupplyCenterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupplyCenterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SupplyCenterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
