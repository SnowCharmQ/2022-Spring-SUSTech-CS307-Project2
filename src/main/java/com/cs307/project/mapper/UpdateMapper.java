package com.cs307.project.mapper;

import java.util.Date;

public interface UpdateMapper {
    void updateOrder(String contractNum, String productModel, String salesmanNum, Integer quantity, Date estimatedDeliveryDate, Date lodgementDate);

    void updateStockInfo(String supplyCenter, String productModel, Integer quantity);

    void updateOrderType(String contractNum, int seq);
}