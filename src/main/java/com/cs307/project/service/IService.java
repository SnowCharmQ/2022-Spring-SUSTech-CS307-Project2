package com.cs307.project.service;

import com.cs307.project.entity.StockIn;

public interface IService {
    void stockIn(StockIn stockIn);

    void placeOrder();

    void updateOrder();

    void deleteOrder();
}