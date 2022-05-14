package com.cs307.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Contract implements Serializable {
    String contract_num, contract_manager, enterprise, supply_center;
    List<OrderInContract> orders;

    public Contract(String contract_num, String contract_manager, String enterprise) {
        this.contract_num = contract_num;
        this.contract_manager = contract_manager;
        this.enterprise = enterprise;
    }

    public Contract(String contract_num, String contract_manager, String enterprise, String supply_center, List<OrderInContract> orders) {
        this.contract_num = contract_num;
        this.contract_manager = contract_manager;
        this.enterprise = enterprise;
        this.supply_center = supply_center;
        this.orders = orders;
    }

    public String getContract_num() {
        return contract_num;
    }

    public String getContract_manager() {
        return contract_manager;
    }

    public String getEnterprise() {
        return enterprise;
    }

    @Override
    public String toString() {
        StringBuilder order = new StringBuilder();
        if (!orders.isEmpty())
            order.append(String.format("%-30s%-20s%-10s%-15s%-30s%-30s\n", "product_model", "salesman", "quantity", "unit_price", "estimate_delivery_date", "lodgement_date"));
        orders.forEach((o) -> {
            String e_d = (o.estimate_delivery_date.getYear() + 1900) + "-" + (o.estimate_delivery_date.getMonth() + 1) + "-" + o.estimate_delivery_date.getDate();
            String l_d = (o.lodgement_date.getYear() + 1900) + "-" + (o.lodgement_date.getMonth() + 1) + "-" + o.lodgement_date.getDate();
            order.append(String.format("%-30s%-20s%-10d%-15d%-30s%-30s\n", o.product_model, o.salesman, o.quantity, o.unit_price, e_d, l_d));
        });
//        return "Contract{\n" +
//                "contract_number='" + contract_num + '\'' +
//                "\ncontract_manager_name='" + contract_manager + '\'' +
//                "\nenterprise_name='" + enterprise + '\'' +
//                "\nsupply_center='" + supply_center + '\'' +
//                "\n" + order +
//                '}';
        return "number: " + contract_num + '\n' +
                "manager: " + contract_manager + '\n' +
                "enterprise: " + enterprise + '\n' +
                "supply_center: " + supply_center + '\n' +
                order;
    }

    public static class OrderInContract {
        String product_model;
        String salesman;
        Integer quantity;
        Integer unit_price;
        Date estimate_delivery_date;
        Date lodgement_date;

        public OrderInContract(String product_model, String salesman, Integer quantity, Integer unit_price, Date estimate_delivery_date, Date lodgement_date) {
            this.product_model = product_model;
            this.salesman = salesman;
            this.quantity = quantity;
            this.unit_price = unit_price;
            this.estimate_delivery_date = estimate_delivery_date;
            this.lodgement_date = lodgement_date;
        }
    }
}
