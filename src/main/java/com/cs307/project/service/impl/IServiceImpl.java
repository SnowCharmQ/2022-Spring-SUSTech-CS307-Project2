package com.cs307.project.service.impl;

import com.cs307.project.entity.*;
import com.cs307.project.mapper.InsertMapper;
import com.cs307.project.mapper.SelectMapper;
import com.cs307.project.service.IService;
import com.cs307.project.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IServiceImpl implements IService {

    @Autowired
    private SelectMapper selectMapper;
    @Autowired
    private InsertMapper insertMapper;

    @Override
    public void stockIn(StockIn stockIn) {
        String supplyCenter = stockIn.getSupplyCenter();
        Center center = selectMapper.selectCenterByName(supplyCenter);
        if (center == null) throw new SupplyCenterNotFoundException("Supply center does not exist");
        String productModel = stockIn.getProductModel();
        Model model = selectMapper.selectModelByModel(productModel);
        if (model == null) throw new ModelNotFoundException("Product does not exist");
        String number = stockIn.getSupplyStaff();
        Staff staff = selectMapper.selectStaffByNumber(number);
        if (staff == null) throw new StaffNotFoundException("Staff does not exist");
        if (!staff.getType().equals("Supply Staff"))
            throw new SalesmanWrongTypeException("The type of the supply staff is not \"supply_staff\"");
        if (!stockIn.getSupplyCenter().equals(staff.getSupplyCenter()))
            throw new MismatchSupplyCenterException("The supply center and the supply center to which the supply staff belongs do not match");
        insertMapper.insertStock(stockIn.getId(), stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getSupplyStaff(), stockIn.getDate(), stockIn.getPurchasePrice(), stockIn.getQuantity());
    }

    @Override
    public void placeOrder() {

    }

    @Override
    public void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate) {

    }

    @Override
    public void deleteOrder() {

    }

    @Override
    public StaffCount getAllStaffCount() {
        return selectMapper.selectAllStaffCount();
    }

    @Override
    public OrderCount getOrderCount() {
        return selectMapper.selectOrderCount();
    }
}
