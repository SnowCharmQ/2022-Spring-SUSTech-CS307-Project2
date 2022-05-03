package com.cs307.project.service;

import com.cs307.project.entity.OrderCount;
import com.cs307.project.entity.StaffCount;
import com.cs307.project.entity.StockIn;

import java.util.Date;

public interface IService {
    void stockIn(StockIn stockIn);

    void placeOrder();

    void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate);

    void deleteOrder();

    StaffCount getAllStaffCount();

    OrderCount getOrderCount();
}
