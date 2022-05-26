package com.cs307.project.service.SqlProvider;

import com.cs307.project.entity.Center;
import org.apache.ibatis.jdbc.SQL;

public class SelectProvider {
    public String selectCenter(Center center) {
        return new SQL() {{
            SELECT("*");
            FROM("center");
            if (center.getId() != null) {
                WHERE("id=" + center.getId());
            }
            if (center.getName() != null) {
                AND();
                WHERE("name=" + center.getName());
            }
        }}.toString();
    }
}
