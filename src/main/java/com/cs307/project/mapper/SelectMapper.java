package com.cs307.project.mapper;

import com.cs307.project.entity.*;

import java.util.List;

//select 语句
public interface SelectMapper {
    Staff selectStaffByNumber(String number);
    Center selectCenterByName(String name);
    Enterprise selectEnterpriseByName(String name);
    Model selectModelByModel(String model);
    List<StaffCount> selectAllStaffCount();
    Integer selectOrderCount();
    Integer selectModelStock(String supply_center, String model);
}
