package com.cs307.project.mapper;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface BaseMapper {
    List<Center> selectCenter();

    List<Enterprise> selectEnterprise();

    List<Model> selectModel();

    List<Staff> selectStaff();

    @SelectProvider(type = com.cs307.project.service.SqlProvider.SelectProvider.class, method = "SelectCenter")
    public List<Center> selectCenter(Center center);
}
