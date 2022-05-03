package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class StockIn implements Serializable {
    private int id;
    private String supplyCenter;
    private String productModel;
    private String supplyStaff;
    private Date date;
    private int purchasePrice;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getSupplyStaff() {
        return supplyStaff;
    }

    public void setSupplyStaff(String supplyStaff) {
        this.supplyStaff = supplyStaff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockIn stockIn = (StockIn) o;
        return id == stockIn.id && purchasePrice == stockIn.purchasePrice && quantity == stockIn.quantity && Objects.equals(supplyCenter, stockIn.supplyCenter) && Objects.equals(productModel, stockIn.productModel) && Objects.equals(supplyStaff, stockIn.supplyStaff) && Objects.equals(date, stockIn.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplyCenter, productModel, supplyStaff, date, purchasePrice, quantity);
    }

    @Override
    public String toString() {
        return "StockIn{" +
                "id=" + id +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", productModel='" + productModel + '\'' +
                ", supplyStaff='" + supplyStaff + '\'' +
                ", date=" + date +
                ", purchasePrice=" + purchasePrice +
                ", quantity=" + quantity +
                '}';
    }
}
