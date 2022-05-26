package com.cs307.project.mapper;

import com.cs307.project.entity.*;

import java.util.List;

public interface SelectMapper {
    Staff selectStaffByNumber(String number);

    Center selectCenterByName(String name);

    Enterprise selectEnterpriseByName(String name);

    Model selectModelByModel(String model);

    List<StaffCount> selectAllStaffCount();

    Integer selectOrderCount();

    Integer selectContractCount();

    Integer selectStockByModel(String supply_center, String model);

    List<PlaceOrder> selectOrderBySalesman(String salesman);

    List<StockQuantity> selectStockQuantity();

    List<OrderQuantity> selectOrderQuantity();

    List<PlaceOrder> selectOrderByContractNum(String contract_num, String salesman);

    List<PlaceOrder> selectContract(String contract_num);

    Integer getNeverSoldProductCount();

    List<AvgStockByCenter> getAvgStockByCenter();

    List<ProductStock> selectProductByModel(String number);

    Contract getContractInfo(String contract_number);

    PlaceOrder selectOrder(int offset);

    PlaceOrder selectOrderDateAsc(int offset);

    PlaceOrder selectOrderDateDesc(int offset);

    PlaceOrder selectOrderQuantityAsc(int offset);

    PlaceOrder selectOrderQuantityDesc(int offset);

    PlaceOrder selectOrderByNumber(String number, int offset);

    PlaceOrder selectOrderDateAscByNumber(String number, int offset);

    PlaceOrder selectOrderDateDescByNumber(String number, int offset);

    PlaceOrder selectOrderQuantityAscByNumber(String number, int offset);

    PlaceOrder selectOrderQuantityDescByNumber(String number, int offset);
}