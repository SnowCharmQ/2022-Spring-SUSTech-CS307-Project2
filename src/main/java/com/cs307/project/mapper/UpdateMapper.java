package com.cs307.project.mapper;

import java.util.Date;

public interface UpdateMapper {
    void updateOrder(int quantity, Date estimatedDeliveryDate, Date lodgementDate);

    void updateStock();
}
