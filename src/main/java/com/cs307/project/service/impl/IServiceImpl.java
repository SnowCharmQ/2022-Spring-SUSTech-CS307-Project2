package com.cs307.project.service.impl;

import com.cs307.project.entity.*;
import com.cs307.project.mapper.InsertMapper;
import com.cs307.project.mapper.SelectMapper;
import com.cs307.project.mapper.UpdateMapper;
import com.cs307.project.service.IService;
import com.cs307.project.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IServiceImpl implements IService {

    @Autowired
    private SelectMapper selectMapper;
    @Autowired
    private InsertMapper insertMapper;
    @Autowired
    private UpdateMapper updateMapper;

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
        Integer stock = selectMapper.selectModelStock(stockIn.getSupplyCenter(), stockIn.getProductModel());
        if (stock == null) insertMapper.insertStockInfo(stockIn.getSupplyCenter(),stockIn.getProductModel(),stockIn.getQuantity());
        else updateMapper.updateStockInfo(stockIn.getSupplyCenter(),stockIn.getProductModel(),stock + stockIn.getQuantity());
        insertMapper.insertStock(stockIn.getId(), stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getSupplyStaff(), stockIn.getDate(), stockIn.getPurchasePrice(), stockIn.getQuantity());
    }

    @Override
    public void placeOrder(PlaceOrder placeOrder) {
        String supplyCenter = selectMapper.selectEnterpriseByName(placeOrder.getEnterprise()).getSupplyCenter();
        Integer stock = selectMapper.selectModelStock(supplyCenter, placeOrder.getProductModel());
        if (stock == null || stock < placeOrder.getQuantity())
            throw new OrderQuantityOverflowException("The stock quantity is not enough");
        Staff staff = selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum());
        if (staff == null || !staff.getType().equals("Salesman"))
            throw new SalesmanWrongTypeException("The type of the supply staff is not \"Salesman\"");
        int quantity = stock - placeOrder.getQuantity();
        updateMapper.updateStockInfo(supplyCenter,placeOrder.getProductModel(), quantity);
        insertMapper.insertOrder(placeOrder.getContractNum(),placeOrder.getEnterprise(),placeOrder.getProductModel(),placeOrder.getQuantity(),placeOrder.getContractManager(),placeOrder.getContractDate(),placeOrder.getEstimatedDeliveryDate(),placeOrder.getLodgementDate(),placeOrder.getSalesmanNum(),placeOrder.getContractType());
        //insertMapper.insertOrder();
    }

    @Override
    public void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate) {

    }

    @Override
    public void deleteOrder() {

    }

    @Override
    public List<StaffCount> getAllStaffCount() {
        return selectMapper.selectAllStaffCount();
    }

    @Override
    public Integer getOrderCount() {
        return selectMapper.selectOrderCount();
    }
}