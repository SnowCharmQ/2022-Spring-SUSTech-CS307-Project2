package com.cs307.project.entity;

import java.util.Objects;

public class Staff {
    private int id;
    private String name;
    private int age;
    private String gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Staff(int id, String name, int age, String gender, String number, String supplyCenter, String mobileNumber, String type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.number = number;
        this.supplyCenter = supplyCenter;
        this.mobileNumber = mobileNumber;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return getId() == staff.getId() && getAge() == staff.getAge() && Objects.equals(getName(), staff.getName()) && Objects.equals(gender, staff.gender) && Objects.equals(getNumber(), staff.getNumber()) && Objects.equals(getSupplyCenter(), staff.getSupplyCenter()) && Objects.equals(getMobileNumber(), staff.getMobileNumber()) && Objects.equals(getType(), staff.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), gender, getNumber(), getSupplyCenter(), getMobileNumber(), getType());
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", supplyCenter='" + supplyCenter + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
