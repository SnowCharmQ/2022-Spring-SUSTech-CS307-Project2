package com.cs307.project.service;

import com.cs307.project.entity.PlaceOrder;
import com.cs307.project.entity.StaffCount;
import com.cs307.project.entity.StockIn;

import java.util.Date;
import java.util.List;

public interface IService {
    void stockIn(StockIn stockIn);

    void placeOrder(PlaceOrder placeOrder);

    void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate);

    void deleteOrder(String contract, int salesmanID, int seq);

    List<StaffCount> getAllStaffCount();

    Integer getOrderCount();
}
