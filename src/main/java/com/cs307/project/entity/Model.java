package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class Model implements Serializable {
    private Integer id;
    private String number;
    private String model;
    private String name;
    private Integer unitPrice;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Model(Integer id, String number, String model, String name, Integer unitPrice) {
        this.id = id;
        this.number = number;
        this.model = model;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model1 = (Model) o;
        return id == model1.id && unitPrice == model1.unitPrice && Objects.equals(number, model1.number) && Objects.equals(model, model1.model) && Objects.equals(name, model1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, model, name, unitPrice);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
