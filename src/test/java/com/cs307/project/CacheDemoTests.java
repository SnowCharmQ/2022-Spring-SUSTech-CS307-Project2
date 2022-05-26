package com.cs307.project;

import com.cs307.project.entity.StockIn;
import com.cs307.project.service.IService;
import com.cs307.project.service.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheDemoTests {
    @Autowired
    private IService iService;

    @Autowired
    private RedisService redisService;

    @Test
    public void stockInTest() {//2
        //CountDownLatch cdl = new CountDownLatch(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                String line = "1,Asia,Repeater97,11210906,2008/10/27,430,801";
                StockIn stock = new StockIn();
                readStockIn(stock, line);
                stock.setId(finalI);
                //System.out.println("=========");
                iService.stockIn(stock);
                //System.out.println("done");
            }
            ).start();
        }
        iService.getContractCount();
        //cdl.await();
    }

    @Test
    public void getAllStaffCountTest() {//6
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                iService.getAllStaffCount();
            }).start();
        }
    }

    @Test
    public void getContractNumberTest() {//7
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                iService.getContractCount();
            }).start();
        }
    }

    @Test
    public void getOrderCountTest() {//8
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                iService.getOrderCount();
            }).start();
        }
    }

    @Test
    public void ConcurrencyTest() {//8
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                iService.getAllStaffCount();
                iService.getContractCount();
                iService.getOrderCount();
                iService.getAvgStockByCenter();
                iService.getProductByNumber("A50L172");
                iService.getContractInfo("CSE0000106");
            }).start();
        }
    }

    public void readStockIn(StockIn stock, String line) {
        String[] content = line.split(",");
        stock.setId(Integer.parseInt(content[0]));
        if (content.length == 7) {
            stock.setSupplyCenter(content[1]);
            stock.setProductModel(content[2]);
            stock.setSupplyStaff(content[3]);
            String[] dateArr = content[4].split("/");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            Date date = c.getTime();
            stock.setDate(date);
            stock.setPurchasePrice(Integer.parseInt(content[5]));
            stock.setQuantity(Integer.parseInt(content[6]));
        } else {
            String sub = content[1] + "," + content[2];
            sub = sub.substring(1, sub.length() - 1);
            stock.setSupplyCenter(sub);
            stock.setProductModel(content[3]);
            stock.setSupplyStaff(content[4]);
            String[] dateArr = content[5].split("/");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]) - 1;
            int day = Integer.parseInt(dateArr[2]);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            Date date = c.getTime();
            stock.setDate(date);
            stock.setPurchasePrice(Integer.parseInt(content[6]));
            stock.setQuantity(Integer.parseInt(content[7]));
        }
    }


}
