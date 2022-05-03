package com.cs307.project;

import com.cs307.project.entity.StockIn;
import com.cs307.project.service.IService;
import com.cs307.project.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {
    @Autowired
    private IService iService;

    @Test
    public void stockInTest() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task1_in_stoke_test_data_publish.csv")))) {
            String line = in.readLine();
            int cnt = 0;
            StockIn stock = new StockIn();
            while ((line = in.readLine()) != null) {
                read(stock, line);
                try {
                    iService.stockIn(stock);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
                System.out.println(++cnt);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read(StockIn stock, String line) {
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
            stock.setSupplyCenter(content[1] + "," + content[2]);
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
