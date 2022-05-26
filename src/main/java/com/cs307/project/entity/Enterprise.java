package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class Enterprise implements Serializable {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String supplyCenter;
    private String industry;

    public Integer getId() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Enterprise(int id, String name, String country, String city, String supplyCenter, String industry) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.supplyCenter = supplyCenter;
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enterprise)) return false;
        Enterprise that = (Enterprise) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getSupplyCenter(), that.getSupplyCenter()) && Objects.equals(getIndustry(), that.getIndustry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCountry(), getCity(), getSupplyCenter(), getIndustry());
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
