package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class StockQuantity implements Serializable {
    String productModel;
    int sum;

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockQuantity that = (StockQuantity) o;
        return sum == that.sum && Objects.equals(productModel, that.productModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productModel, sum);
    }

    @Override
    public String toString() {
        return "StockQuantity{" +
                "productModel='" + productModel + '\'' +
                ", sum=" + sum +
                '}';
    }
}
