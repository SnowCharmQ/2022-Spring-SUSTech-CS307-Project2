package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductStock implements Serializable {
    String supplyCenter;
    String productModel;
    String quantity;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStock that = (ProductStock) o;
        return Objects.equals(supplyCenter, that.supplyCenter) && Objects.equals(productModel, that.productModel) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplyCenter, productModel, quantity);
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "supplyCenter='" + supplyCenter + '\'' +
                ", productModel='" + productModel + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
