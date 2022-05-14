package com.cs307.project.controller;

import com.cs307.project.entity.*;
import com.cs307.project.service.IService;
import com.cs307.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class APIController extends BaseController{
    @Autowired
    private IService iService;

    @RequestMapping("menu")
    public String getMenu(){
        return "Change the sidebar selection to invoke the corresponding API";
    }

    @RequestMapping("q6api")
    public JsonResult<List<StaffCount>> getAllStaffCount(){
        List<StaffCount> sc = iService.getAllStaffCount();
        return new JsonResult<>(ok, sc);
    }

    @RequestMapping("q7api")
    public JsonResult<Integer> getContractCount(){
        Integer cnt = iService.getContractCount();
        return new JsonResult<>(ok, cnt);
    }

    @RequestMapping("q8api")
    public JsonResult<Integer> getOrderCount(){
        Integer cnt = iService.getOrderCount();
        return new JsonResult<>(ok, cnt);
    }

    @RequestMapping("q9api")
    public JsonResult<Integer> getNeverSoldProductCount(){
        Integer cnt = iService.getNeverSoldProductCount();
        return new JsonResult<>(ok, cnt);
    }

    @RequestMapping("q10api")
    public JsonResult<FavoriteModel> getFavoriteProductModel(){
        FavoriteModel fm = iService.getFavoriteProductModel();
        return new JsonResult<>(ok, fm);
    }

    @RequestMapping("q11api")
    public JsonResult<List<AvgStockByCenter>> getAvgStockByCenter(){
        List<AvgStockByCenter> asbc = iService.getAvgStockByCenter();
        return new JsonResult<>(ok, asbc);
    }

    @RequestMapping("q12api")
    public JsonResult<List<ProductStock>> getProductByNumber(String number){
        List<ProductStock> ps = iService.getProductByNumber(number);
        return new JsonResult<>(ok, ps);
    }

    @RequestMapping("q13api")
    public JsonResult<String> getContractInfo(String number){
        Contract contract = iService.getContractInfo(number);
        return new JsonResult<>(ok, contract.toString());
    }
}
