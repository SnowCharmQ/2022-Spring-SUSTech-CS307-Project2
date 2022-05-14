package com.cs307.project;

import com.cs307.project.entity.StaffCount;
import com.cs307.project.service.IService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheDemoTests{
    @Autowired
    private IService iService;

//    @Autowired
//    private RedisServiceImpl redisService;
//
//    @Test
//    public void QueryTest()
//    {
//        iService.getContractCount();
//        iService.getContractCount();
//        iService.getContractCount();
//        iService.getContractCount();
//        iService.getContractCount();
//    }
//
//    @Test
//    public void testRedisSet()
//    {
//        Boolean flag = redisService.set("gerry", "gerry-value");
//        System.out.println(flag);
//        flag = redisService.set("le", "gerry-value");
//        redisService.expire("gerry", 5000);
//    }
//
//    @Test
//    public void concurrencyTest()
//    {
//        CountDownLatch countDownLatch = new CountDownLatch(100000);
//        for (int i = 0; i < 100000; i++) {
//            new Thread(() ->{
//                countDownLatch.countDown();
//                iService.getAvgStockByCenter();
//            }).start();
//        }
//
//        try {
//            countDownLatch.await();
//            //Thread.currentThread().join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void getAllStaffCountTest() {//6
//        List<StaffCount> staffCounts = iService.getAllStaffCount();
//        for (StaffCount sc : staffCounts) System.out.println(sc);
        for (int i = 0; i < 100000; i++) {
            new Thread(() ->{
                iService.getAllStaffCount();
            }).start();
        }
    }

    @Test
    public void getContractNumberTest() {//7
        Integer cnt = iService.getContractCount();
        System.out.println(cnt);
    }

    @Test
    public void getOrderCountTest() {//8
//        Integer cnt = iService.getOrderCount();
//        System.out.println(cnt);
        for (int i = 0; i < 100000; i++) {
            new Thread(() ->{
                iService.getOrderCount();
            }).start();
        }
    }
}
