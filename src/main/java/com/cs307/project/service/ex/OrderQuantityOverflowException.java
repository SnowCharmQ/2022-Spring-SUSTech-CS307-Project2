package com.cs307.project.service.ex;

public class OrderQuantityOverflowException extends ServiceException {
    public OrderQuantityOverflowException() {
        super();
    }

    public OrderQuantityOverflowException(String message) {
        super(message);
    }

    public OrderQuantityOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderQuantityOverflowException(Throwable cause) {
        super(cause);
    }

    public OrderQuantityOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}