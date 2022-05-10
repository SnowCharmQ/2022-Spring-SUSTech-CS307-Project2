package com.cs307.project.utils;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;
import com.cs307.project.entity.Staff;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

public class Loader {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    static HashSet<Center> set_center = new HashSet<>();
    static HashSet<Enterprise> set_enterprise = new HashSet<>();
    static HashSet<Model> set_model = new HashSet<>();
    static HashSet<Staff> set_staff = new HashSet<>();

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {//find the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {//connect
            con = DriverManager.getConnection(url, props);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            assert con != null;
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadCenter() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/center.csv")))) {
            String line = inline.readLine();
            String[] content;
            int id = 0, name = 1;
            while ((line = inline.readLine()) != null) {
                content = line.split(",");
                if (content.length > 2) {
                    content[1] = content[1] + "," + content[2];
                    content[1] = content[1].substring(1, content[1].length() - 1);
                }
                set_center.add(new Center(Integer.parseInt(content[id]), content[name]));
            }
            stmt = con.prepareStatement("insert into center (id, name) values(?,?)");
            for (Center c :
                    set_center) {
                stmt.setInt(1, c.getId());
                stmt.setString(2, c.getName());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadEnterprise() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/enterprise.csv")))) {
            String line = inline.readLine();
            String[] content;
            int id = 0, name = 1, country = 2, city = 3, supply_center = 4, industry = 5;
            while ((line = inline.readLine()) != null) {
                content = line.split(",");
                if (content.length > 6) {
                    content[4] = content[4] + "," + content[5];
                    content[4] = content[4].substring(1, content[4].length() - 1);
                    content[5] = content[6];
                }
                set_enterprise.add(new Enterprise(Integer.parseInt(content[id]), content[name], content[country], content[city], content[supply_center], content[industry]));
            }
            stmt = con.prepareStatement("insert into enterprise (id, name, country, city, supply_center, industry) values (?,?,?,?,?,?)");
            for (Enterprise e :
                    set_enterprise) {
                stmt.setInt(1, e.getId());
                stmt.setString(2, e.getName());
                stmt.setString(3, e.getCountry());
                stmt.setString(4, e.getCity());
                stmt.setString(5, e.getSupplyCenter());
                stmt.setString(6, e.getIndustry());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadModel() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/model.csv")))) {
            String line = inline.readLine();
            String[] content;
            int id = 0, number = 1, model = 2, name = 3, unit_price = 4;
            while ((line = inline.readLine()) != null) {
                content = line.split(",");
                set_model.add(new Model(Integer.parseInt(content[id]), content[number], content[model], content[name], Integer.parseInt(content[unit_price])));
            }
            stmt = con.prepareStatement("insert into model (id, number, model, name, unit_price)  values (?,?,?,?,?)");
            for (Model e :
                    set_model) {
                stmt.setInt(1, e.getId());
                stmt.setString(2, e.getNumber());
                stmt.setString(3, e.getModel());
                stmt.setString(4, e.getName());
                stmt.setInt(5, e.getUnitPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadStaff() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/data/staff.csv")))) {
            String line = inline.readLine();
            String[] content;
            int id = 0, name = 1, age = 2, gender = 3, number = 4, supply_center = 5, mobile_number = 6, type = 7;
            while ((line = inline.readLine()) != null) {
                content = line.split(",");
                if (content.length > 8) {
                    content[5] = content[5] + "," + content[6];
                    content[5] = content[5].substring(1, content[5].length() - 1);
                    content[6] = content[7];
                    content[7] = content[8];
                }
                set_staff.add(new Staff(Integer.parseInt(content[id]), content[name], Integer.parseInt(content[age]), content[gender], content[number], content[supply_center], content[mobile_number], content[type]));
            }
            stmt = con.prepareStatement("insert into staff (id, name, age, gender, number, supply_center, mobile_number, type) values (?,?,?,?,?,?,?,?)");
            for (Staff e :
                    set_staff) {
                stmt.setInt(1, e.getId());
                stmt.setString(2, e.getName());
                stmt.setInt(3, e.getAge());
                stmt.setString(4, e.getGender());
                stmt.setString(5, e.getNumber());
                stmt.setString(6, e.getSupplyCenter());
                stmt.setString(7, e.getMobileNumber());
                stmt.setString(8, e.getType());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) throws SQLException {
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "test");
        defprop.put("password", "123456");
        defprop.put("database", "project2");
        Properties prop = new Properties(defprop);
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            stmt0.execute("truncate table center, enterprise, model, staff, stock_info, stockIn, placeOrder, contract cascade;");
            stmt0.execute("");
            con.commit();
            stmt0.close();
        }
        loadCenter();
        loadEnterprise();
        loadModel();
        loadStaff();
        closeDB();
        System.out.println("LOAD DONE!");
    }
}
