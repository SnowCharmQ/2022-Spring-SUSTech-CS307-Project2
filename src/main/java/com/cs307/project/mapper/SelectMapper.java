package com.cs307.project.mapper;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;

//select 语句
public interface SelectMapper {
    Staff selectStaffByNumber(String number);
    Center selectCenterByName(String name);
    Model selectModelByModel(String model);
}
