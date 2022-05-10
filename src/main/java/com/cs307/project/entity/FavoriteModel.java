package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteModel implements Serializable {
    String productModel;
    int quantity;

    public FavoriteModel(String productModel, int quantity){
        this.productModel = productModel;
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteModel that = (FavoriteModel) o;
        return quantity == that.quantity && Objects.equals(productModel, that.productModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productModel, quantity);
    }

    @Override
    public String toString() {
        return "FavoriteModel{" +
                "productModel='" + productModel + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
