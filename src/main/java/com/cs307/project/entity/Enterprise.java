package com.cs307.project.entity;

import java.util.Objects;

public class Enterprise {
    private int id;
    private String name;
    private String country;
    private String supplyCenter;
    private String industry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(supplyCenter, that.supplyCenter) && Objects.equals(industry, that.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, supplyCenter, industry);
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
