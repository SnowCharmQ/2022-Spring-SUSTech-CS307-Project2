package com.cs307.project.controller;

import com.cs307.project.controller.ex.PasswordNotMatchException;
import com.cs307.project.controller.ex.UserException;
import com.cs307.project.controller.ex.UserNotFoundException;
import com.cs307.project.controller.ex.UsernameDuplicatedException;
import com.cs307.project.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int ok = 200;

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }

    @ExceptionHandler(UserException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
        }
        return result;
    }
}
