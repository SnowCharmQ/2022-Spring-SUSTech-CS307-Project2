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

import java.util.*;
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
        stockChanged = true;
        product_cache.remove(stockIn.getProductModel());
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
        synchronized (this) {
            Integer stock = selectMapper.selectStockByModel(stockIn.getSupplyCenter(), stockIn.getProductModel());
            if (stock == null)
                insertMapper.insertStockInfo(stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getQuantity());
            else
                updateMapper.updateStockInfo(stockIn.getSupplyCenter(), stockIn.getProductModel(), stock + stockIn.getQuantity());
            insertMapper.insertStock(stockIn.getId(), stockIn.getSupplyCenter(), stockIn.getProductModel(), stockIn.getSupplyStaff(), stockIn.getDate(), stockIn.getPurchasePrice(), stockIn.getQuantity());
        }
    }

    @Override
    public void placeOrder(PlaceOrder placeOrder) {
        orderChanged = true;
        contractChanged = true;
        stockChanged = true;
        productChanged = true;
        contract_cache.remove(placeOrder.getContractNum());

        Enterprise enterprise = selectMapper.selectEnterpriseByName(placeOrder.getEnterprise());
        String supplyCenter = enterprise.getSupplyCenter();
        Staff staff = selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum());
        if (staff == null || !staff.getType().equals("Salesman"))
            throw new SalesmanWrongTypeException("The type of the staff is not \"Salesman\"");
        synchronized (this) {
            Integer stock = selectMapper.selectStockByModel(supplyCenter, placeOrder.getProductModel());
            if (stock == null || stock < placeOrder.getQuantity())
                throw new OrderQuantityOverflowException("The stock quantity is not enough");
            int quantity = stock - placeOrder.getQuantity();
            updateMapper.updateStockInfo(supplyCenter, placeOrder.getProductModel(), quantity);
            insertMapper.insertOrder(placeOrder.getContractNum(), placeOrder.getEnterprise(), placeOrder.getProductModel(), placeOrder.getQuantity(), placeOrder.getContractManager(), placeOrder.getContractDate(), placeOrder.getEstimatedDeliveryDate(), placeOrder.getLodgementDate(), placeOrder.getSalesmanNum(), placeOrder.getContractType());
            //insertMapper.insertContract(placeOrder.getContractNum());
        }
    }

    @Override
    public void updateOrder(String contractNum, String productModel, String salesmanNum, int quantity, Date estimatedDeliveryDate, Date lodgementDate) {
        orderChanged = true;
        productChanged = true;
        favoriteChanged = true;
        stockChanged = true;
        contract_cache.remove(contractNum);

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
        synchronized (this) {
            Integer stock = selectMapper.selectStockByModel(supplyCenter, productModel);
            if (stock + placeOrder.getQuantity() - quantity < 0)
                throw new OrderQuantityOverflowException("The stock quantity is not enough");
            updateMapper.updateStockInfo(supplyCenter, productModel, stock + placeOrder.getQuantity() - quantity);
            if (quantity == 0) deleteMapper.deleteOrderBySalesman(contractNum, productModel, salesmanNum);
            else
                updateMapper.updateOrder(contractNum, productModel, salesmanNum, quantity, estimatedDeliveryDate, lodgementDate);
        }
    }

    @Override
    public void deleteOrder(String contract, String salesman, int seq) {
        orderChanged = true;
        productChanged = true;
        stockChanged = true;
        favoriteChanged = true;
        contract_cache.remove(contract);
        synchronized (this) {
            List<PlaceOrder> orders = selectMapper.selectOrderByContractNum(contract, salesman);
            if (orders == null || orders.size() < seq) throw new OrderNotFoundException("No order of the seq");
            PlaceOrder order = orders.get(seq - 1);
            String supply_center = selectMapper.selectEnterpriseByName(order.getEnterprise()).getSupplyCenter();
            int stock = selectMapper.selectStockByModel(supply_center, order.getProductModel()) + order.getQuantity();
            updateMapper.updateStockInfo(supply_center, order.getProductModel(), stock);
            deleteMapper.deleteOrder(order.getContractNum(), order.getEnterprise(), order.getProductModel(), order.getQuantity(), order.getContractManager(), order.getContractDate(), order.getEstimatedDeliveryDate(), order.getLodgementDate(), order.getSalesmanNum(), order.getContractType());
        }
    }


    @Autowired
    private RedisService redisService;

    boolean staffChanged = true;
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
        if (staffCountString == null || staffChanged) {
            synchronized (this) {
                try {
                    staffCountString = redisService.get(STAFF_COUNT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (staffCountString == null || staffChanged) {
                    System.out.println("============from database===========");
                    staffCounts = selectMapper.selectAllStaffCount();
                    String jsonString = JSON.toJSONString(staffCounts);
                    redisService.set(STAFF_COUNT, jsonString);
                    staffChanged = false;
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
                if (order == null || orderChanged) {
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
                if (contract == null || contractChanged) {
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
    public static final String NEVER_SOLD_PRODUCT_COUNT = "never_sold_count";

    public Integer getNeverSoldProductCount() {
        String never = null;
        int neverSoldCount;
        try {
            never = redisService.get(NEVER_SOLD_PRODUCT_COUNT);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        if (never == null || productChanged) {
            synchronized (this) {
                try {
                    never = redisService.get(NEVER_SOLD_PRODUCT_COUNT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (never == null || productChanged) {
                    System.out.println("=========from database==========");
                    neverSoldCount = selectMapper.getNeverSoldProductCount();
                    never = JSON.toJSONString(neverSoldCount);
                    redisService.set(NEVER_SOLD_PRODUCT_COUNT, never);
                    productChanged = false;
                    redisService.expire(NEVER_SOLD_PRODUCT_COUNT, 500);
                } else neverSoldCount = Integer.parseInt(never);
            }
        } else neverSoldCount = Integer.parseInt(never);
        return neverSoldCount;
        //return selectMapper.getNeverSoldProductCount();
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


    boolean favoriteChanged = true;
    public static final String FAVORITE_PRODUCT = "favorite_product";

    @Override
    public FavoriteModel getFavoriteProductModel() {
        String favorite = null;
        FavoriteModel model;
        try {
            favorite = redisService.get(FAVORITE_PRODUCT);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        if (favorite == null || favoriteChanged) {
            synchronized (this) {
                try {
                    favorite = redisService.get(FAVORITE_PRODUCT);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (favorite == null || favoriteChanged) {
                    System.out.println("============from database===========");
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
                    model = new FavoriteModel(productModel, max);
                    favorite = JSON.toJSONString(model);
                    redisService.set(FAVORITE_PRODUCT, favorite);
                    favoriteChanged = false;
                    redisService.expire(FAVORITE_PRODUCT, 500);
                } else model = JSON.parseObject(favorite, FavoriteModel.class);
            }
        } else model = JSON.parseObject(favorite, FavoriteModel.class);
        return model;
    }

    HashSet<String> product_cache = new HashSet<>();
    public static final String PRODUCT_BY_NUMBER = "product_by_number: ";

    @Override
    public List<ProductStock> getProductByNumber(String number) {
        String product = null;
        List<ProductStock> productStocks;
        try {
            product = redisService.get(PRODUCT_BY_NUMBER + number);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        if (product == null || !product_cache.contains(number)) {
            synchronized (this) {
                try {
                    product = redisService.get(PRODUCT_BY_NUMBER + number);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (product == null || !product_cache.contains(number)) {
                    System.out.println("===========from database===========");
                    productStocks = selectMapper.selectProductByModel(number);
                    product = JSON.toJSONString(productStocks);
                    redisService.set(PRODUCT_BY_NUMBER + number, product);
                    product_cache.add(number);
                    redisService.expire(PRODUCT_BY_NUMBER + number, 500);
                } else productStocks = JSONArray.parseArray(product, ProductStock.class);
            }
        } else productStocks = JSONArray.parseArray(product, ProductStock.class);

        return productStocks;
    }

    HashSet<String> contract_cache = new HashSet<>();
    public static final String CONTRACT_INFO = "contract_info: ";

    @Override
    public Contract getContractInfo(String contract_number) {
        String info = null;
        Contract contract;
        try {
            info = redisService.get(CONTRACT_INFO + contract_number);
        } catch (Exception e) {
            System.out.println("connection to redis failed");
        }
        if (info == null || !contract_cache.contains(CONTRACT_INFO)) {
            synchronized (this) {
                try {
                    info = redisService.get(CONTRACT_INFO + contract_number);
                } catch (Exception e) {
                    System.out.println("connection to redis failed");
                }
                if (info == null || !contract_cache.contains(CONTRACT_INFO)) {
                    System.out.println("========from database==============");
                    List<PlaceOrder> orders = selectMapper.selectContract(contract_number);
                    contract = selectMapper.getContractInfo(contract_number);
                    List<Contract.OrderInContract> orderlists = orders.stream().map(new Function<PlaceOrder, Contract.OrderInContract>() {
                        @Override
                        public Contract.OrderInContract apply(PlaceOrder placeOrder) {
                            Contract.OrderInContract order = new Contract.OrderInContract(placeOrder.getProductModel(), selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum()).getName(), placeOrder.getQuantity(), selectMapper.selectModelByModel(placeOrder.getProductModel()).getUnitPrice(), placeOrder.getEstimatedDeliveryDate(), placeOrder.getLodgementDate());
                            return order;
                        }
                    }).collect(Collectors.toList());
                    contract = new Contract(contract_number, selectMapper.selectStaffByNumber(contract.getContract_manager()).getName(), contract.getEnterprise(), selectMapper.selectEnterpriseByName(contract.getEnterprise()).getSupplyCenter(), orderlists);
                    info = JSON.toJSONString(contract);
                    redisService.set(CONTRACT_INFO + contract_number, info);
                    contract_cache.add(contract_number);
                    redisService.expire(CONTRACT_INFO + contract_number, 500);
                } else contract = JSON.parseObject(info, Contract.class);
            }
        } else contract = JSON.parseObject(info, Contract.class);

        return contract;


//        List<PlaceOrder> orders = selectMapper.selectContract(contract_number);
//        Contract contract = selectMapper.getContractInfo(contract_number);
//        List<Contract.OrderInContract> orderlists = orders.stream().map(new Function<PlaceOrder, Contract.OrderInContract>() {
//            @Override
//            public Contract.OrderInContract apply(PlaceOrder placeOrder) {
//                Contract.OrderInContract order = new Contract.OrderInContract(placeOrder.getProductModel(), selectMapper.selectStaffByNumber(placeOrder.getSalesmanNum()).getName(), placeOrder.getQuantity(), selectMapper.selectModelByModel(placeOrder.getProductModel()).getUnitPrice(), placeOrder.getEstimatedDeliveryDate(), placeOrder.getLodgementDate());
//                return order;
//            }
//        }).collect(Collectors.toList());
//        return new Contract(contract_number, selectMapper.selectStaffByNumber(contract.getContract_manager()).getName(), contract.getEnterprise(), selectMapper.selectEnterpriseByName(contract.getEnterprise()).getSupplyCenter(), orderlists);
    }

    @Override
    public PlaceOrder getOrder(String sorting, String key, String page) {
        int offset = (!Objects.equals(page, "")) ? (Integer.parseInt(page) - 1) : 0;
        if (Objects.equals(key, "")) {
            switch (sorting) {
                case "0":
                    return selectMapper.selectOrder(offset);
                case "1":
                    return selectMapper.selectOrderQuantityAsc(offset);
                case "2":
                    return selectMapper.selectOrderQuantityDesc(offset);
                case "3":
                    return selectMapper.selectOrderDateAsc(offset);
                case "4":
                    return selectMapper.selectOrderDateDesc(offset);
                default:
                    return null;
            }
        } else {
            key = key.toUpperCase();
            switch (sorting) {
                case "0":
                    return selectMapper.selectOrderByNumber(key, offset);
                case "1":
                    return selectMapper.selectOrderQuantityAscByNumber(key, offset);
                case "2":
                    return selectMapper.selectOrderQuantityDescByNumber(key, offset);
                case "3":
                    return selectMapper.selectOrderDateAscByNumber(key, offset);
                case "4":
                    return selectMapper.selectOrderDateDescByNumber(key, offset);
                default:
                    return null;
            }
        }
    }
}