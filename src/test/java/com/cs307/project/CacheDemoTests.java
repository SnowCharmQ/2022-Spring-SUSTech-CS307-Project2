package com.cs307.project;

import com.cs307.project.entity.StaffCount;
import com.cs307.project.service.IService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheDemoTests{
    @Autowired
    private IService iService;

    @Test
    public void getAllStaffCountTest() {//6
        for (int i = 0; i < 100000; i++) {
            new Thread(() ->{
                iService.getAllStaffCount();
            }).start();
        }
    }

    @Test
    public void getContractNumberTest() {//7
        for (int i = 0; i < 100000; i++) {
            new Thread(() ->{
                iService.getContractCount();
            }).start();
        }
        Integer cnt = iService.getContractCount();
        System.out.println(cnt);
    }

    @Test
    public void getOrderCountTest() {//8
        for (int i = 0; i < 100000; i++) {
            new Thread(() ->{
                iService.getOrderCount();
            }).start();
        }
    }

    @Test
    public void ConcurrencyTest() {//8
        for (int i = 0; i < 1000; i++) {
            new Thread(() ->{
                iService.getAllStaffCount();
                iService.getContractCount();
                iService.getOrderCount();
                iService.getAvgStockByCenter();
                iService.getProductByNumber("A50L172");
                iService.getContractInfo("CSE0000106");
            }).start();
        }
    }

}
