package com.cs307.project.entity;

import java.util.Objects;

public class Staff {
    private int id;
    private String name;
    private int age;
    private String number;
    private String supplyCenter;
    private String mobileNumber;
    private String type;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id && age == staff.age && Objects.equals(name, staff.name) && Objects.equals(number, staff.number) && Objects.equals(supplyCenter, staff.supplyCenter) && Objects.equals(mobileNumber, staff.mobileNumber) && Objects.equals(type, staff.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, number, supplyCenter, mobileNumber, type);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", number='" + number + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
