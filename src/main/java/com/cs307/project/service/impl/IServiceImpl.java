package com.cs307.project.service.impl;

import com.cs307.project.entity.*;
import com.cs307.project.mapper.DeleteMapper;
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
    @Autowired
    private DeleteMapper deleteMapper;

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
            throw new SalesmanWrongTypeException("The type of the staff is not \"supply_staff\"");
        if (!stockIn.getSupplyCenter().equals(staff.getSupplyCenter()))
            throw new MismatchSupplyCenterException("The supply center and the supply center to which the supply staff belongs do not match");
        Integer stock = selectMapper.selectStockByModel(stockIn.getSupplyCenter(), stockIn.getProductModel());
        if (stock == null)
            insertMapper.insertStockInfo(stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getQuantity());
        else
            updateMapper.updateStockInfo(stockIn.getSupplyCenter(), stockIn.getProductModel(), stock + stockIn.getQuantity());
        insertMapper.insertStock(stockIn.getId(), stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getSupplyStaff(), stockIn.getDate(), stockIn.getPurchasePrice(), stockIn.getQuantity());
    }

    @Override
    public void placeOrder(PlaceOrder placeOrder) {
        Enterprise enterprise = selectMapper.selectEnterpriseByName(placeOrder.getEnterprise());
        String supplyCenter = enterprise.getSupplyCenter();
        Integer stock = selectMapper.selectStockByModel(supplyCenter, placeOrder.getProductModel());
        if (stock == null || stock < placeOrder.getQuantity())
            throw new OrderQuantityOverflowException("The stock quantity is not enough");
        Staff staff = selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum());
        if (staff == null || !staff.getType().equals("Salesman"))
            throw new SalesmanWrongTypeException("The type of the staff is not \"Salesman\"");
        int quantity = stock - placeOrder.getQuantity();
        updateMapper.updateStockInfo(supplyCenter, placeOrder.getProductModel(), quantity);
        insertMapper.insertOrder(placeOrder.getContractNum(), placeOrder.getEnterprise(), placeOrder.getProductModel(), placeOrder.getQuantity(), placeOrder.getContractManager(), placeOrder.getContractDate(), placeOrder.getEstimatedDeliveryDate(), placeOrder.getLodgementDate(), placeOrder.getSalesmanNum(), placeOrder.getContractType());
        insertMapper.insertContract(placeOrder.getContractNum());
    }

    @Override
    public void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate) {
        List<PlaceOrder> list = selectMapper.selectOrderBySalesman(salesmanNum);
        PlaceOrder placeOrder = null;
        for (PlaceOrder po : list) {
            if (po.getContractNum().equals(contractNum) && po.getProductModel().equals(productModel)) {
                placeOrder = po;
                break;
            }
        }
        if (placeOrder == null) throw new OrderNotFoundException("No matched order");
        Enterprise enterprise = selectMapper.selectEnterpriseByName(placeOrder.getEnterprise());
        String supplyCenter = enterprise.getSupplyCenter();
        Integer stock = selectMapper.selectStockByModel(supplyCenter, productModel);
        if (stock + placeOrder.getQuantity() - quantity < 0)
            throw new OrderQuantityOverflowException("The stock quantity is not enough");
        updateMapper.updateStockInfo(supplyCenter, productModel, stock + placeOrder.getQuantity() - quantity);
        if (quantity == 0) deleteMapper.deleteOrderBySalesman(contractNum, productModel, salesmanNum);
        else
            updateMapper.updateOrder(contractNum, productModel, salesmanNum, quantity, estimatedDeliveryDate, lodgementDate);
    }

    @Override
    public void deleteOrder(String contract, String salesman, int seq) {
        List<PlaceOrder> orders = selectMapper.selectOrderByContractNum(contract, salesman);
        if (orders == null || orders.size() < seq) throw new OrderNotFoundException("No order of the seq");
        PlaceOrder order = orders.get(seq - 1);
        String supply_center = selectMapper.selectEnterpriseByName(order.getEnterprise()).getSupplyCenter();
        int stock = selectMapper.selectStockByModel(supply_center, order.getProductModel()) + order.getQuantity();
        updateMapper.updateStockInfo(supply_center, order.getProductModel(), stock);
        deleteMapper.deleteOrder(order.getContractNum(), order.getEnterprise(), order.getProductModel(), order.getQuantity(), order.getContractManager(), order.getContractDate(), order.getEstimatedDeliveryDate(), order.getLodgementDate(), order.getSalesmanNum(), order.getContractType());
    }

    @Override
    public List<StaffCount> getAllStaffCount() {
        return selectMapper.selectAllStaffCount();
    }

    @Override
    public Integer getOrderCount() {
        return selectMapper.selectOrderCount();
    }

    public Integer getContractCount(){
        return selectMapper.selectContractCount();
    }
    public Integer getNeverSoldProductCount(){
        return selectMapper.getNeverSoldProductCount();
    }

    public List<AvgStockByCenter> getgetAvgStockByCenter(){
        return selectMapper.getAvgStockByCenter();
    }

    @Override
    public FavoriteModel getFavoriteProductModel() {//貌似有点问题
        List<StockQuantity> stockQuantityList = selectMapper.selectStockQuantity();
        List<OrderQuantity> orderQuantityList = selectMapper.selectOrderQuantity();
        String productModel = null;
        int max = 0;
        for (StockQuantity sq : stockQuantityList) {
            OrderQuantity oq = orderQuantityList.stream()
                    .filter(o -> o.getProductModel().equals(sq.getProductModel()))
                    .findFirst().get();
            int sales = sq.getSum() - oq.getSum();
            if (sales > max) {
                max = sales;
                productModel = oq.getProductModel();
            }
        }
        return new FavoriteModel(productModel, max);
    }
}