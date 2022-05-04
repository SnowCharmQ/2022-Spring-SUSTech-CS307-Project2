package com.cs307.project.mapper;

import java.util.Date;

public interface DeleteMapper {
    void deleteOrder(String contractNum, String enterprise, String productModel, int quantity, String contractManager, Date contractDate, Date estimatedDeliveryDate, Date lodgementDate, String salesmanNum, String contractType);

    void deleteOrderBySalesman(String contractNum, String productModel, String salesmanNum);

    void deleteStock();
}
