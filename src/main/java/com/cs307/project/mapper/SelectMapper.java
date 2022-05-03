package com.cs307.project.mapper;

import com.cs307.project.entity.*;

//select 语句
public interface SelectMapper {
    Staff selectStaffByNumber(String number);
    Center selectCenterByName(String name);
    Model selectModelByModel(String model);
    StaffCount selectAllStaffCount();
    OrderCount selectOrderCount();
}
