package com.cs307.project.service.api1;

import com.cs307.project.entity.Center;
import com.cs307.project.entity.Enterprise;
import com.cs307.project.entity.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ModelHandling {
    private static final int BATCH_SIZE = 500;
    private static Connection con = null;
    private static PreparedStatement stmt1 = null;
    private static PreparedStatement stmt2 = null;

    public static void main(String[] args) throws SQLException {
        openDB("localhost","project2", "test", "123456");
    //    insertModel(new Model(1,"124","test","name", 2));
//we        delete(new Model(null, "124",null,null,null));
//        update(new Model(null, "124",null,null,null),new Model(null, null,"12345",null,null));
        selectModel(new Model(null, "124",null,null,null));
        closeDB();
    }

    public static List<Center> selectCenter(Center center) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from center where ");
        if (center.getId()!=null){
            sql.append("id="+ center.getId());
        }
        if (center.getName()!=null){
            sql.append("and name = \'" + center.getName() + "\'");
        }
        Statement stmt = con.createStatement();
        ResultSet set = stmt.executeQuery(sql.toString());
        stmt.close();
        List<Center> centers = new ArrayList<>();
        while (set.next()){
            Center center1 = new Center(set.getInt(1), set.getString(2));
            centers.add(center1);
        }
        return centers;
    }

    public static List<Enterprise> selectEnterprise(Enterprise enterprise) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from enterprise where ");
        if (enterprise.getId()!=null){
            sql.append("id="+ enterprise.getId());
        }
        if (enterprise.getName()!=null)
        {
            sql.append(" and name = \'" + enterprise.getName() +"\'");
        }
        if (enterprise.getCountry()!=null){
            sql.append(" and country = \'" + enterprise.getCountry() + "\'");
        }
        if (enterprise.getCity()!=null){
            sql.append(" and city = \'" + enterprise.getCity()+"\'");
        }
        if (enterprise.getSupplyCenter()!=null){
            sql.append(" and supply_center= \'"+ enterprise.getSupplyCenter()+"\'");
        }
        if (enterprise.getIndustry()!=null){
            sql.append(" and industry = \'" + enterprise.getIndustry() + "\'");
        }
        Statement stmt = con.createStatement();
        ResultSet set = stmt.executeQuery(sql.toString());
        stmt.close();
        List<Enterprise> enterprises = new ArrayList<>();
        while (set.next()){
            Enterprise enterprise1 = new Enterprise(set.getInt(1), set.getString(2),set.getString(3),set.getString(4),set.getString(5),set.getString(6));
            enterprises.add(enterprise1);
        }
        return enterprises;
    }

    public static List<Model> selectModel(Model model) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from model where ");
        if (model.getId()!=null) {
            sql.append("id=" + model.getId());
        }
        if (model.getModel()!=null)
        {
            sql.append(" and model = \'"+ model.getModel()+"\'");
        }
        if (model.getNumber()!=null)
        {
            sql.append(" and number = \'" + model.getNumber()+"\'");
        }
        if (model.getName()!=null)
        {
            sql.append(" and name = \'" + model.getName());
        }
        if (model.getUnitPrice()!=null){
            sql.append(" and unit_price ="+ model.getUnitPrice());
        }
        if (sql.substring(27,30).equals("and"))sql.delete(27,30);
        Statement stmt = con.createStatement();
        ResultSet set = stmt.executeQuery(sql.toString());
        List<Model> models = new ArrayList<>();
        while (set.next()){
            Model model1 = new Model(set.getInt(1),set.getString(2),set.getString(3),set.getString(4),set.getInt(5));
            models.add(model1);
        }
        stmt.close();
        return models;
    }

    public static void insertModel(Model model) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into model (id, number, model, name, unit_price) values (" + model.getId() + ",\'"
                + model.getNumber() + "\',\'" + model.getModel() + "\',\'" + model.getName() + "\'," + model.getUnitPrice() + ")");
        Statement stmt = con.createStatement();
        stmt.execute(sql.toString());
        stmt.close();
        con.commit();
    }

    public static void update(Model old, Model new_model) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update model set ");
        if (new_model.getId()!=null) {
            sql.append("id=" + new_model.getId());
        }
        if (new_model.getModel()!=null)
        {
            sql.append(", model = \'"+ new_model.getModel()+"\'");
        }
        if (new_model.getNumber()!=null)
        {
            sql.append(", number = \'" + new_model.getNumber()+"\'");
        }
        if (new_model.getName()!=null)
        {
            sql.append(", name = \'" + new_model.getName());
        }
        if (new_model.getUnitPrice()!=null){
            sql.append(", unit_price ="+ new_model.getUnitPrice());
        }
        if (sql.charAt(17)==',')sql.deleteCharAt(17);
        StringBuilder sql2 = new StringBuilder();
        sql2.append("where ");
        if (old.getId()!=null) {
            sql2.append("id=" + old.getId());
        }
        if (old.getModel()!=null)
        {
            sql2.append(" and model = \'"+ old.getModel()+"\'");
        }
        if (old.getNumber()!=null)
        {
            sql2.append(" and number = \'" + old.getNumber()+"\'");
        }
        if (old.getName()!=null)
        {
            sql2.append(" and name = \'" + old.getName());
        }
        if (old.getUnitPrice()!=null){
            sql2.append(" and unit_price ="+ old.getUnitPrice());
        }
        if (sql2.substring(7,10).equals("and")){
            sql2.delete(7,10);
        }
        sql.append(sql2);
        Statement stmt = con.createStatement();
        stmt.execute(sql.toString());
        stmt.close();
        con.commit();
    }

    public static void delete(Model old) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from model ");
        sql.append("where ");
        if (old.getId()!=null) {
            sql.append("id=" + old.getId());
        }
        if (old.getModel()!=null)
        {
            sql.append(" and model = \'"+ old.getModel()+"\'");
        }
        if (old.getNumber()!=null)
        {
            sql.append(" and number = \'" + old.getNumber()+"\'");
        }
        if (old.getName()!=null)
        {
            sql.append(" and name = \'" + old.getName());
        }
        if (old.getUnitPrice()!=null){
            sql.append(" and unit_price ="+ old.getUnitPrice());
        }
        if (sql.substring(25,28).equals("and")){
            sql.delete(25,28);
        }
        Statement stmt = con.createStatement();
        stmt.execute(sql.toString());
        stmt.close();
        con.commit();
    }


    public static void openDB(String host, String dbname,
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

    public static void closeDB() {
        if (con != null) {
            try {
                if (stmt1 != null) {
                    stmt1.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }
}
