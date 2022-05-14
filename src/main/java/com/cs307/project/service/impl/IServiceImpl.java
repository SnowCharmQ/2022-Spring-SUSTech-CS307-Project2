package com.cs307.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cs307.project.entity.*;
import com.cs307.project.mapper.DeleteMapper;
import com.cs307.project.mapper.InsertMapper;
import com.cs307.project.mapper.SelectMapper;
import com.cs307.project.mapper.UpdateMapper;
import com.cs307.project.service.IService;
import com.cs307.project.service.ex.*;
import com.cs307.project.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        orderChanged = true;
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
        //insertMapper.insertContract(placeOrder.getContractNum());
    }

    @Override
    public void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate) {
        orderChanged = true;
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
        orderChanged = true;
        List<PlaceOrder> orders = selectMapper.selectOrderByContractNum(contract, salesman);
        if (orders == null || orders.size() < seq) throw new OrderNotFoundException("No order of the seq");
        PlaceOrder order = orders.get(seq - 1);
        String supply_center = selectMapper.selectEnterpriseByName(order.getEnterprise()).getSupplyCenter();
        int stock = selectMapper.selectStockByModel(supply_center, order.getProductModel()) + order.getQuantity();
        updateMapper.updateStockInfo(supply_center, order.getProductModel(), stock);
        deleteMapper.deleteOrder(order.getContractNum(), order.getEnterprise(), order.getProductModel(), order.getQuantity(), order.getContractManager(), order.getContractDate(), order.getEstimatedDeliveryDate(), order.getLodgementDate(), order.getSalesmanNum(), order.getContractType());
    }


    @Autowired
    private RedisService redisService;

    public static final String STAFF_COUNT = "staff_count";

    @Override
    public List<StaffCount> getAllStaffCount() {
//        return selectMapper.selectAllStaffCount();
        String staffCountString = null;
        try {
            staffCountString = redisService.get(STAFF_COUNT);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        List<StaffCount> staffCounts;
        if (staffCountString == null) {
            synchronized (this) {
                try {
                    staffCountString = redisService.get(STAFF_COUNT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (staffCountString == null) {
                    System.out.println("============from database===========");
                    staffCounts = selectMapper.selectAllStaffCount();
                    String jsonString = JSON.toJSONString(staffCounts);
                    redisService.set(STAFF_COUNT, jsonString);
                    redisService.expire(STAFF_COUNT, 500);
                } else staffCounts = JSONArray.parseArray(staffCountString, StaffCount.class);
            }
        } else {
            //System.out.println("==========from cache=============");
            staffCounts = JSONArray.parseArray(staffCountString, StaffCount.class);
        }
        return staffCounts;
    }

    boolean orderChanged = true;
    public static final String ORDER_COUNT = "order_count";
    @Override
    public Integer getOrderCount() {
        //return selectMapper.selectOrderCount();
        String order = null;
        try {
            order = redisService.get(ORDER_COUNT);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        int orderCount;
        if (order == null || orderChanged) {
            synchronized (this) {
                try {
                    order = redisService.get(ORDER_COUNT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (order == null) {
                    System.out.println("============from database===========");
                    orderCount = selectMapper.selectOrderCount();
                    String jsonString = JSON.toJSONString(orderCount);
                    redisService.set(ORDER_COUNT, jsonString);
                    orderChanged = false;
                    redisService.expire(ORDER_COUNT, 500);
                } else orderCount = Integer.parseInt(order);
            }
        } else {
            //System.out.println("==========from cache=============");
            orderCount = Integer.parseInt(order);
        }
        return orderCount;
    }

    boolean contractChanged = true;
    public static final String CONTRACT_COUNT = "contract_count";

    public Integer getContractCount() {
        //return selectMapper.selectContractCount();
        String contract = null;
        try {
            contract = redisService.get(CONTRACT_COUNT);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        int contractCount;
        if (contract == null || contractChanged) {
            synchronized (this) {
                try {
                    contract = redisService.get(CONTRACT_COUNT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (contract == null) {
                    System.out.println("============from database===========");
                    contractCount = selectMapper.selectContractCount();
                    String jsonString = JSON.toJSONString(contractCount);
                    redisService.set(CONTRACT_COUNT, jsonString);
                    contractChanged = false;
                    redisService.expire(CONTRACT_COUNT, 500);
                } else contractCount = Integer.parseInt(contract);
            }
        } else {
            //System.out.println("==========from cache=============");
            contractCount = Integer.parseInt(contract);
        }
        return contractCount;
    }

    boolean productChanged = true;
    public Integer getNeverSoldProductCount() {
        return selectMapper.getNeverSoldProductCount();
    }

    boolean stockChanged = true;
    public static final String AVG_STOCK = "avg_stock";
    public List<AvgStockByCenter> getAvgStockByCenter() {
        List<AvgStockByCenter> avgStockByCenters;
        String avgStock = null;
        try {
            avgStock = redisService.get(AVG_STOCK);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        if (avgStock == null || stockChanged) {
            synchronized (this) {
                try {
                    avgStock = redisService.get(AVG_STOCK);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (avgStock == null || stockChanged) {
                    System.out.println("============from database===========");
                    avgStockByCenters = selectMapper.getAvgStockByCenter();
                    String jsonString = JSON.toJSONString(avgStockByCenters);
                    redisService.set(AVG_STOCK, jsonString);
                    stockChanged = false;
                    redisService.expire(AVG_STOCK, 50);
                } else avgStockByCenters = JSONArray.parseArray(avgStock, AvgStockByCenter.class);
            }
        } else {
            //System.out.println("==========from cache=============");
            avgStockByCenters = JSONArray.parseArray(avgStock, AvgStockByCenter.class);
        }
        return avgStockByCenters;
    }

    @Override
    public FavoriteModel getFavoriteProductModel() {
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

    @Override
    public List<ProductStock> getProductByNumber(String number) {
        return selectMapper.selectProductByModel(number);
    }

    @Override
    public Contract getContractInfo(String contract_number) {
        List<PlaceOrder> orders = selectMapper.selectContract(contract_number);
        Contract contract = selectMapper.getContractInfo(contract_number);
        List<Contract.OrderInContract> orderlists = orders.stream().map(new Function<PlaceOrder, Contract.OrderInContract>() {
            @Override
            public Contract.OrderInContract apply(PlaceOrder placeOrder) {
                Contract.OrderInContract order = new Contract.OrderInContract(placeOrder.getProductModel(), selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum()).getName(), placeOrder.getQuantity(), selectMapper.selectModelByModel(placeOrder.getProductModel()).getUnitPrice(), placeOrder.getEstimatedDeliveryDate(), placeOrder.getLodgementDate());
                return order;
            }
        }).collect(Collectors.toList());
        return new Contract(contract_number, selectMapper.selectStaffByNumber(contract.getContract_manager()).getName(), contract.getEnterprise(), selectMapper.selectEnterpriseByName(contract.getEnterprise()).getSupplyCenter(), orderlists);
    }
}