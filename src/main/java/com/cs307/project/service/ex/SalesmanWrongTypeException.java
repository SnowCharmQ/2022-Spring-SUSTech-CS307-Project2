package com.cs307.project.service.ex;

public class SalesmanWrongTypeException extends ServiceException{
    public SalesmanWrongTypeException() {
        super();
    }

    public SalesmanWrongTypeException(String message) {
        super(message);
    }

    public SalesmanWrongTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SalesmanWrongTypeException(Throwable cause) {
        super(cause);
    }

    protected SalesmanWrongTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
