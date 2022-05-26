package com.cs307.project;

import com.cs307.project.mapper.UpdateTypeMapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.cs307.project.mapper")
public class ProjectApplication {
    @Autowired
    private UpdateTypeMapper updateTypeMapper;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        updateTypeMapper.scheduleFixedDelayTask();
//        System.out.println(
//                "update order type at - " + System.currentTimeMillis() / 1000);
    }
}
