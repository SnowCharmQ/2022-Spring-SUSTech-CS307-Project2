package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderCount implements Serializable {
    private int count;

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
        OrderCount that = (OrderCount) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "OrderCount{" +
                "count=" + count +
                '}';
    }
}
