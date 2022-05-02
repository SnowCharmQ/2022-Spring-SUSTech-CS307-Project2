package com.cs307.project.entity;

import java.util.Date;
import java.util.Objects;

public class Order {
    private String contractNum;
    private String enterprise;
    private String productModel;
    private int quantity;
    private String contractManager;
    private Date contractDate;
    private Date estimatedDeliveryDate;
    private Date lodgementDate;
    private String salesmanNum;
    private String contractType;

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getContractManager() {
        return contractManager;
    }

    public void setContractManager(String contractManager) {
        this.contractManager = contractManager;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(Date lodgementDate) {
        this.lodgementDate = lodgementDate;
    }

    public String getSalesmanNum() {
        return salesmanNum;
    }

    public void setSalesmanNum(String salesmanNum) {
        this.salesmanNum = salesmanNum;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return quantity == that.quantity && Objects.equals(contractNum, that.contractNum) && Objects.equals(enterprise, that.enterprise) && Objects.equals(productModel, that.productModel) && Objects.equals(contractManager, that.contractManager) && Objects.equals(contractDate, that.contractDate) && Objects.equals(estimatedDeliveryDate, that.estimatedDeliveryDate) && Objects.equals(lodgementDate, that.lodgementDate) && Objects.equals(salesmanNum, that.salesmanNum) && Objects.equals(contractType, that.contractType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractNum, enterprise, productModel, quantity, contractManager, contractDate, estimatedDeliveryDate, lodgementDate, salesmanNum, contractType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "contractNum='" + contractNum + '\'' +
                ", enterprise='" + enterprise + '\'' +
                ", productModel='" + productModel + '\'' +
                ", quantity=" + quantity +
                ", contractManager='" + contractManager + '\'' +
                ", contractDate=" + contractDate +
                ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                ", lodgementDate=" + lodgementDate +
                ", salesmanNum='" + salesmanNum + '\'' +
                ", contractType='" + contractType + '\'' +
                '}';
    }
}
