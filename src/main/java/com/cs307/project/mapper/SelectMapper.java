package com.cs307.project.mapper;

import com.cs307.project.entity.*;
import javafx.util.Pair;

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
    Integer getNeverSoldProductCount();
    List<AvgStockByCenter> getAvgStockByCenter();
}