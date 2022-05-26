package com.cs307.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UpdateTypeMapper {
    @Select("call update_order_type();")
    public void scheduleFixedDelayTask();
}
