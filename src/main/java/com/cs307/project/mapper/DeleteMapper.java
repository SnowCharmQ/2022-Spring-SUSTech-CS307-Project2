package com.cs307.project.mapper;

public interface DeleteMapper {
    void deleteOrderBySalesman(String contractNum, String productModel, String salesmanNum);
    void deleteStock();
}
