package com.cs307.project.controller.ex;

public class NoPrivilegeException extends UserException{
    public NoPrivilegeException() {
        super();
    }

    public NoPrivilegeException(String message) {
        super(message);
    }

    public NoPrivilegeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPrivilegeException(Throwable cause) {
        super(cause);
    }

    protected NoPrivilegeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
