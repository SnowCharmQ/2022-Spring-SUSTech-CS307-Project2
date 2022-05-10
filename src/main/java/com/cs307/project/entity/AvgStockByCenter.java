package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class AvgStockByCenter implements Serializable {
    String center;
    float avg;

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvgStockByCenter)) return false;
        AvgStockByCenter that = (AvgStockByCenter) o;
        return Float.compare(that.getAvg(), getAvg()) == 0 && Objects.equals(getCenter(), that.getCenter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCenter(), getAvg());
    }

    @Override
    public String toString() {
        return String.format("AvgStockByCenter{" +
                "center='" + center + '\'' +
                ", avg = %.1f" +
                '}',avg);
    }
}
