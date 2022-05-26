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

    List<Center> selectCenter(Center center);

    List<Enterprise> selectEnterprise(
            Integer id,
            String name,
            String country,
            String city,
            String supplyCenter,
            String industry
    );

    List<Model> selectModel(
            Integer id,
            String number,
            String model,
            String name,
            Integer unitPrice
    );

    List<Staff> selectStaff(
            int id,
            String name,
            int age,
            String gender,
            String number,
            String supplyCenter,
            String mobileNumber,
            String type
    );

}
