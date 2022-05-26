package com.cs307.project.service;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;

import java.util.List;

public interface IBaseService {
    List<Center> selectCenter();

    List<Enterprise> selectEnterprise();

    List<Model> selectModel();

    List<Staff> selectStaff();
}
