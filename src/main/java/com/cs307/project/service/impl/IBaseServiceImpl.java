package com.cs307.project.service.impl;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;
import com.cs307.project.mapper.BaseMapper;
import com.cs307.project.service.IBaseService;
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

}
