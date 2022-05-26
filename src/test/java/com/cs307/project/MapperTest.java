package com.cs307.project;

import com.cs307.project.entity.Center;
import com.cs307.project.mapper.BaseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    BaseMapper baseMapper;

    @Test
    public void centerTest()
    {
        List<Center> centers = baseMapper.selectCenter(new Center(1,null));
        System.out.println();
    }
}
