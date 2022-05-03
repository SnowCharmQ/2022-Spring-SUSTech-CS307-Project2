package com.cs307.project.service.ex;

public class OrderQuantityOvereflowException extends ServiceException{
    public OrderQuantityOvereflowException() {
        super();
    }

    public OrderQuantityOvereflowException(String message) {
        super(message);
    }

    public OrderQuantityOvereflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderQuantityOvereflowException(Throwable cause) {
        super(cause);
    }

    public OrderQuantityOvereflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
