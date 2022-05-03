package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class StaffCount implements Serializable {
    private String staffType;
    private int count;

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffCount that = (StaffCount) o;
        return count == that.count && Objects.equals(staffType, that.staffType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffType, count);
    }

    @Override
    public String toString() {
        return "StaffCount{" +
                "staffType='" + staffType + '\'' +
                ", count=" + count +
                '}';
    }
}
