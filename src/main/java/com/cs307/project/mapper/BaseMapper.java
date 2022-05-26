package com.cs307.project.mapper;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface BaseMapper {
    List<Center> selectCenter();

    List<Enterprise> selectEnterprise();

    List<Model> selectModel();

    List<Staff> selectStaff();

    @SelectProvider(type = select.class, method = "selectCenter")
    public List<Center> selectCenterPara(Integer id);
}

class select {
    public String selectCenter(Integer id) {
        return new SQL() {{
            SELECT("*");
            FROM("center");
            WHERE("id="+id);
        }}.toString();
    }
}
