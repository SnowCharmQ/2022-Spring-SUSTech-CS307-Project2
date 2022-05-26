package com.cs307.project.controller;

import com.cs307.project.controller.ex.NoPrivilegeException;
import com.cs307.project.entity.*;
import com.cs307.project.service.IBaseService;
import com.cs307.project.service.IService;
import com.cs307.project.service.IUserService;
import com.cs307.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("api")
public class APIController extends BaseController {
    @Autowired
    private IService iService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IBaseService iBaseService;

    @RequestMapping("menu")
    public String getMenu() {
        return "Change the sidebar selection to invoke the corresponding API";
    }

    @RequestMapping("q6api")
    public JsonResult<List<StaffCount>> getAllStaffCount(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            List<StaffCount> sc = iService.getAllStaffCount();
            return new JsonResult<>(ok, sc);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q7api")
    public JsonResult<Integer> getContractCount(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            Integer cnt = iService.getContractCount();
            return new JsonResult<>(ok, cnt);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q8api")
    public JsonResult<Integer> getOrderCount(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            Integer cnt = iService.getOrderCount();
            return new JsonResult<>(ok, cnt);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q9api")
    public JsonResult<Integer> getNeverSoldProductCount(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            Integer cnt = iService.getNeverSoldProductCount();
            return new JsonResult<>(ok, cnt);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q10api")
    public JsonResult<FavoriteModel> getFavoriteProductModel(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            FavoriteModel fm = iService.getFavoriteProductModel();
            return new JsonResult<>(ok, fm);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q11api")
    public JsonResult<List<AvgStockByCenter>> getAvgStockByCenter(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            List<AvgStockByCenter> asbc = iService.getAvgStockByCenter();
            return new JsonResult<>(ok, asbc);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q12api")
    public JsonResult<List<ProductStock>> getProductByNumber(String number, HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            List<ProductStock> ps = iService.getProductByNumber(number);
            return new JsonResult<>(ok, ps);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q13api")
    public JsonResult<String> getContractInfo(String number, HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            Contract contract = iService.getContractInfo(number);
            return new JsonResult<>(ok, contract.toString());
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("center")
    public JsonResult<List<Center>> getCenter() {
        List<Center> list = iBaseService.selectCenter();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("enterprise")
    public JsonResult<List<Enterprise>> getEnterprise() {
        List<Enterprise> list = iBaseService.selectEnterprise();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("model")
    public JsonResult<List<Model>> getModel() {
        List<Model> list = iBaseService.selectModel();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("staff")
    public JsonResult<List<Staff>> getStaff() {
        List<Staff> list = iBaseService.selectStaff();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("order")
    public JsonResult<PlaceOrder> getOrder(String sorting, String key, String page){
        PlaceOrder order = iService.getOrder(sorting, key, page);
        return new JsonResult<>(ok, order);
    }
}
