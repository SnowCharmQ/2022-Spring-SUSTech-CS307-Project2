package com.cs307.project.controller;

import com.cs307.project.controller.ex.NoPrivilegeException;
import com.cs307.project.entity.*;
import com.cs307.project.service.IBaseService;
import com.cs307.project.service.IService;
import com.cs307.project.service.IUserService;
import com.cs307.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

    @RequestMapping("q3api")
    public JsonResult<Void> placeOrder(@RequestBody String str, HttpSession session){
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if(user.isCanInsert()) {
            str = str.substring(7);
            PlaceOrder order = new PlaceOrder();
            readPlaceOrder(order, str);
            iService.placeOrder(order);
            return new JsonResult<>(ok);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    private void readPlaceOrder(PlaceOrder placeOrder, String line) {
        String[] content = line.split("%09");
        placeOrder.setContractNum(content[0]);
        placeOrder.setEnterprise(content[1].replace('+', ' '));
        placeOrder.setProductModel(content[2]);
        placeOrder.setQuantity(Integer.parseInt(content[3]));
        placeOrder.setContractManager(content[4]);
        String[] dateArr = content[5].split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]) - 1;
        int day = Integer.parseInt(dateArr[2]);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date date = c.getTime();
        placeOrder.setContractDate(date);
        dateArr = content[6].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        date = c.getTime();
        placeOrder.setEstimatedDeliveryDate(date);
        dateArr = content[7].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        date = c.getTime();
        placeOrder.setLodgementDate(date);
        placeOrder.setSalesmanNum(content[8]);
        placeOrder.setContractType(content[9]);
    }

    @RequestMapping("q4api")
    public JsonResult<Void> updateOrder(@RequestBody String str, HttpSession session){
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanUpdate()){
            String[] content = str.split("%09");
            String[] dateArr = content[4].split("-");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);
            Calendar edc = Calendar.getInstance();
            edc.set(year, month, day);
            Date edd = edc.getTime();
            dateArr = content[5].split("-");
            year = Integer.parseInt(dateArr[0]);
            month = Integer.parseInt(dateArr[1]) - 1;
            day = Integer.parseInt(dateArr[2]);
            Calendar lc = Calendar.getInstance();
            lc.set(year, month, day);
            Date ld = lc.getTime();
            iService.updateOrder(content[0], content[1], content[2], Integer.parseInt(content[3]), edd, ld);
            return new JsonResult<>(ok);
        }else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q5api")
    public JsonResult<Void> deleteOrder(@RequestBody String str, HttpSession session){
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanDelete()){
            String[] content = str.split("%09");
            iService.deleteOrder(content[0], content[1], Integer.parseInt(content[2]));
            return new JsonResult<>(ok);
        }else throw new NoPrivilegeException("Permission Denied!");
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
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q10api")
    public JsonResult<FavoriteModel> getFavoriteProductModel(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            FavoriteModel fm = iService.getFavoriteProductModel();
            return new JsonResult<>(ok, fm);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q11api")
    public JsonResult<List<AvgStockByCenter>> getAvgStockByCenter(HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            List<AvgStockByCenter> asbc = iService.getAvgStockByCenter();
            return new JsonResult<>(ok, asbc);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q12api")
    public JsonResult<List<ProductStock>> getProductByNumber(String number, HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            List<ProductStock> ps = iService.getProductByNumber(number);
            return new JsonResult<>(ok, ps);
        } else throw new NoPrivilegeException("Permission Denied!");
    }

    @RequestMapping("q13api")
    public JsonResult<String> getContractInfo(String number, HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            Contract contract = iService.getContractInfo(number);
            return new JsonResult<>(ok, contract.toString());
        } else throw new NoPrivilegeException("Permission Denied!");
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
    public JsonResult<PlaceOrder> getOrder(String sorting, String key, String page, HttpSession session) {
        User user = iUserService.selectUser(getUsernameFromSession(session));
        if (user.isCanSelect()) {
            PlaceOrder order = iService.getOrder(sorting, key, page);
            return new JsonResult<>(ok, order);
        }else throw new NoPrivilegeException("Permission Denied!");
    }
}
