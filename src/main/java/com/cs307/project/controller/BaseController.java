package com.cs307.project.controller;

import com.cs307.project.controller.ex.*;
import com.cs307.project.service.ex.*;
import com.cs307.project.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int ok = 200;

    protected final String getUsernameFromSession(HttpSession session){
        if (session == null || session.getAttribute("username") == null) throw new UserNotFoundException("You have not login in!");
        return session.getAttribute("username").toString();
    }

    @ExceptionHandler({UserException.class, ServiceException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
        }else if (e instanceof NoPrivilegeException){
            result.setState(5000);
        }else if (e instanceof MismatchSupplyCenterException){
            result.setState(6000);
        }else if (e instanceof ModelNotFoundException){
            result.setState(6001);
        } else if (e instanceof OrderNotFoundException) {
            result.setState(6002);
        } else if (e instanceof OrderQuantityOverflowException) {
            result.setState(6003);
        } else if (e instanceof SalesmanWrongTypeException) {
            result.setState(6004);
        } else if (e instanceof StaffNotFoundException) {
            result.setState(6005);
        } else if (e instanceof SupplyCenterNotFoundException) {
            result.setState(6006);
        }
        return result;
    }
}
