package com.cs307.project.mapper;

import java.util.Date;

public interface InsertMapper {
    void insertOrder(String contractNum, String enterprise, String productModel, int quantity, String contractManager,  Date contractDate, Date estimatedDeliveryDate, Date lodgementDate, String salesmanNum,String contractType);

    void insertStock(int id, String supplyCenter, String productModel, String supplyStaff, Date date, int purchasePrice, int quantity);

    void insertStockInfo(String supplyCenter, String productModel, int quantity);

    /**
     * record all the contract exits
     * @param contractNum
     */
    void insertContract(String contractNum);
}