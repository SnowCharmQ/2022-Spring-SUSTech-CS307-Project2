package com.cs307.project.service.impl;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;
import com.cs307.project.mapper.BaseMapper;
import com.cs307.project.service.IBaseService;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBaseServiceImpl implements IBaseService {
    @Autowired
    private BaseMapper baseMapper;

    @Override
    public List<Center> selectCenter() {
        return baseMapper.selectCenter();
    }

    @Override
    public List<Enterprise> selectEnterprise() {
        return baseMapper.selectEnterprise();
    }

    @Override
    public List<Model> selectModel() {
        return baseMapper.selectModel();
    }

    @Override
    public List<Staff> selectStaff() {
        return baseMapper.selectStaff();
    }

    @Override
    @SelectProvider(type = com.cs307.project.service.SqlProvider.SelectProvider.class, method = "SelectCenter")
    public List<Center> selectCenter(Center center) {
        return null;
    }

    @Override
    @SelectProvider(type = com.cs307.project.service.SqlProvider.SelectProvider.class, method = "SelectCenter")
    public List<Enterprise> selectEnterprise(Integer id, String name, String country, String city, String supplyCenter, String industry) {
        return null;
    }

    @Override
    public List<Model> selectModel(Integer id, String number, String model, String name, Integer unitPrice) {
        return null;
    }

    @Override
    public List<Staff> selectStaff(int id, String name, int age, String gender, String number, String supplyCenter, String mobileNumber, String type) {
        return null;
    }

}
