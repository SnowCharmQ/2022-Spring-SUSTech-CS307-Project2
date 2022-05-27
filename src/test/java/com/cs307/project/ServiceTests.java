package com.cs307.project;

import com.cs307.project.entity.*;
import com.cs307.project.service.IService;
import com.cs307.project.service.api1.ModelHandling;
import com.cs307.project.service.ex.ServiceException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTests {
    @Autowired
    private IService iService;

    @Test
    public void allTest() throws IOException, SQLException {
        ModelHandling.openDB();
        ModelHandling.createIndex();
        ModelHandling.closeDB();
        String line = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/test2/in_stoke_test.csv")))) {
            line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                StockIn stock = new StockIn();
                readStockIn(stock, line);
                try {
                    iService.stockIn(stock);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
                //System.out.println(++cnt);
            }
        } catch (IOException e) {
            System.out.println(line);
            throw new RuntimeException(e);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/test2/task2_test_data_final_public.tsv")))) {
            line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                PlaceOrder placeOrder = new PlaceOrder();
                readPlaceOrder(placeOrder, line);
                try {
                    iService.placeOrder(placeOrder);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
                //System.out.println(++cnt);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/test2/update_final_test.tsv")))) {
            line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                String[] dateArr = content[4].split("-");
                int year = Integer.parseInt(dateArr[0]);
                int month = Integer.parseInt(dateArr[1]) - 1;
                int day = Integer.parseInt(dateArr[2]);
                Calendar edc = Calendar.getInstance();
                edc.set(year, month, day);
                Date edd = edc.getTime();
                dateArr = content[5].split("-");
                year = Integer.parseInt(dateArr[0]);
                month = Integer.parseInt(dateArr[1]) - 1;
                day = Integer.parseInt(dateArr[2]);
                Calendar lc = Calendar.getInstance();
                lc.set(year, month, day);
                Date ld = lc.getTime();
                //System.out.println(++cnt);
                try {
                    iService.updateOrder(content[0], content[1], content[2], Integer.parseInt(content[3]), edd, ld);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/test2/delete_final.tsv")))) {
            line = in.readLine();
            System.out.println(iService.getOrderCount());
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                //System.out.println(++cnt);
                try {
                    iService.deleteOrder(content[0], content[1], Integer.parseInt(content[2]));
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(iService.getOrderCount());
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/data/test2/out.txt"));
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Q6\n");
        List<StaffCount> staffCounts = iService.getAllStaffCount();//6
        for (StaffCount sc : staffCounts) {
            stringBuilder.append(String.format("%-25s%d\n",sc.getStaffType(),sc.getCount()));
        }

        stringBuilder.append("Q7   ");
        Integer cnt = iService.getContractCount();//7
        stringBuilder.append(cnt);

        stringBuilder.append("\nQ8   ");
        cnt = iService.getOrderCount();//8
        stringBuilder.append(cnt);

        stringBuilder.append("\nQ9   ");
        cnt = iService.getNeverSoldProductCount();//9
        stringBuilder.append(cnt);

        stringBuilder.append("\nQ10\n");
        FavoriteModel fm = iService.getFavoriteProductModel();//10
        stringBuilder.append(String.format("%-25s%d",fm.getProductModel(),fm.getQuantity()));

        stringBuilder.append("\nQ11\n");
        List<AvgStockByCenter> list = iService.getAvgStockByCenter();//11
        for (AvgStockByCenter a : list) {
            stringBuilder.append(String.format("%-50s%.1f\n",a.getCenter(), a.getAvg()));
        }

//        stringBuilder.append("\nQ12\n");
//        stringBuilder.append(iService.getProductByNumber("A50L172"));//12
//
//        stringBuilder.append("\nQ13\n");
//        stringBuilder.append(iService.getContractInfo("CSE0000106"));//13
//        stringBuilder.append(iService.getContractInfo("CSE0000209"));
//        stringBuilder.append(iService.getContractInfo("CSE0000306"));

        writer.write(stringBuilder.toString());
        writer.close();
    }

    @Test
    @Order(1)
//    @Ignore
    public void init() {
        ClassLoader classLoader = ServiceTests.class.getClassLoader();
        try {
            Class<?> loadClass = classLoader.loadClass("com.cs307.project.utils.Loader");
            Method method = loadClass.getMethod("main", String[].class);
            method.invoke(null, new Object[]{new String[]{}});
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Init");
    }

    @Test
    @Order(2)
    public void stockInTest() {//2
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/test2/in_stoke_test.csv"), StandardCharsets.UTF_8))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                StockIn stock = new StockIn();
                readStockIn(stock, line);
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

    @Test
    @Order(3)
    public void placeOrderTest() {//3
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task2_test_data_publish.csv"), StandardCharsets.UTF_8))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                PlaceOrder placeOrder = new PlaceOrder();
                readPlaceOrder(placeOrder, line);
                try {
                    iService.placeOrder(placeOrder);
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

    @Test
    @Order(4)
    public void updateOrderTest() {//4
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task34_update_test_data_publish.tsv"), StandardCharsets.UTF_8))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                String[] dateArr = content[4].split("-");
                int year = Integer.parseInt(dateArr[0]);
                int month = Integer.parseInt(dateArr[1]) - 1;
                int day = Integer.parseInt(dateArr[2]);
                Calendar edc = Calendar.getInstance();
                edc.set(year, month, day);
                Date edd = edc.getTime();
                dateArr = content[5].split("-");
                year = Integer.parseInt(dateArr[0]);
                month = Integer.parseInt(dateArr[1]) - 1;
                day = Integer.parseInt(dateArr[2]);
                Calendar lc = Calendar.getInstance();
                lc.set(year, month, day);
                Date ld = lc.getTime();
                System.out.println(++cnt);
                try {
                    iService.updateOrder(content[0], content[1], content[2], Integer.parseInt(content[3]), edd, ld);
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    public void deleteOrderTest() {//5
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/task34_delete_test_data_publish.tsv"), StandardCharsets.UTF_8))) {
            String line = in.readLine();
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                String[] content = line.split("\t");
                System.out.println(++cnt);
                try {
                    iService.deleteOrder(content[0], content[1], Integer.parseInt(content[2]));
                } catch (ServiceException e) {
                    System.out.println(e.getClass().getSimpleName());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(6)
    public void getAllStaffCountTest() {//6
        List<StaffCount> staffCounts = iService.getAllStaffCount();
        for (StaffCount sc : staffCounts) System.out.println(sc);
    }

    @Test
    @Order(7)
    public void getContractNumberTest() {//7
        Integer cnt = iService.getContractCount();
        System.out.println(cnt);
    }

    @Test
    @Order(8)
    public void getOrderCountTest() {//8
        Integer cnt = iService.getOrderCount();
        System.out.println(cnt);
    }

    @Test
    @Order(9)
    public void getNeverSoldProductCountTest() {//9
        Integer cnt = iService.getNeverSoldProductCount();
        System.out.println(cnt);
    }

    @Test
    @Order(10)
    public void getFavoriteProductModelTest() {//10
        FavoriteModel fm = iService.getFavoriteProductModel();
        System.out.println(fm);
    }

    @Test
    @Order(11)
    public void getAvgStockByCenterTest() {//11
        List<AvgStockByCenter> list = iService.getAvgStockByCenter();
        for (AvgStockByCenter a : list) {
            System.out.println(a.toString());
        }
    }

    @Test
    @Order(12)
    public void getProductByNumber() {//12
        System.out.println(iService.getProductByNumber("A50L172"));
    }

    @Test
    @Order(13)
    public void getContractInfo() {//13
        System.out.println(iService.getContractInfo("CSE0000106"));
        System.out.println(iService.getContractInfo("CSE0000209"));
        System.out.println(iService.getContractInfo("CSE0000306"));
    }

    public void readPlaceOrder(PlaceOrder placeOrder, String line) {
        String[] content = line.split("\t");
        placeOrder.setContractNum(content[0]);
        placeOrder.setEnterprise(content[1]);
        placeOrder.setProductModel(content[2]);
        placeOrder.setQuantity(Integer.parseInt(content[3]));
        placeOrder.setContractManager(content[4]);
        String[] dateArr = content[5].split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]) - 1;
        int day = Integer.parseInt(dateArr[2]);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date date = c.getTime();
        placeOrder.setContractDate(date);
        dateArr = content[6].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        date = c.getTime();
        placeOrder.setEstimatedDeliveryDate(date);
        dateArr = content[7].split("-");
        year = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]) - 1;
        day = Integer.parseInt(dateArr[2]);
        c = Calendar.getInstance();
        c.set(year, month, day);
        date = c.getTime();
        placeOrder.setLodgementDate(date);
        placeOrder.setSalesmanNum(content[8]);
        placeOrder.setContractType(content[9]);
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