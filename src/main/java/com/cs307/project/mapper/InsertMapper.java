package com.cs307.project.mapper;

import java.util.Date;

public interface InsertMapper {
    void insertOrder();

    void insertStock(int id, String supplyCenter, String productModel, String supplyStaff, Date date, int purchasePrice, int quantity);
}
