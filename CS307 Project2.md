# **Spring 2022 CS307 Project 2**

***Group Members:***

***SID: 12012705 Name: Li Jiacheng***

***SID: 12013006 Name: Qiu Yilun***

## Part - 1 Project Structure

Our group build our enterprise management system based on the Spring-Boot framework. And we also build our front client based on html, css and javascript. Our project structure is as follows:

<img src="/Users/leopold-lee/Library/Application Support/typora-user-images/image-20220526200701637.png" alt="image-20220526200701637" style="zoom: 25%;" />

The directory src.main.java contains the code of our server, src.main.resources contains the original data,  *xml of spring mappers, our sql tables, and the front client code. The directory src.test contains unit test functions.

All the APIs are in the directory src.main.java.com.cs307.projct.service, they will be introduced in the next parts.

## Part - 2 APIs description

#### 1) APIs for manipulating the original data:

**Parameters**: the information about a model

**Returns** : the list of selected models, or void in delete, update and insert 

We mainly implemented the manipulation for the table model, to realize more flexible way to pass the parameters, we use SQL statements splicing, part of our code is as follows:

```java
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
```

#### **2) stockIn:**

Parameters: the information of stock

Returns: void

Throws: self defined exceptions when stock in illegal information defined in the project document

#### **3) placeOrder:**

Parameters: the information of a order

Returns: void

Throws: self defined exceptions when order is illegal defined by in the project document

#### **4) updateOrder:**

Parameters: the information of a updated order

Returns: void

Throws: self defined exceptions when updated order is illegal defined by in the project document

Other APIs all meet the same requirements of the project document, for the limit of this report, so further description.

## Part - 3 Advanced Requirements

#### Bill Module

We create a table **bill** and a trigger **update_bill** to record the bill whenever create a stockIn, insert, delete, update the order. Each record in bill table contains the information of data, operator (stockin, insert order, delete order or update order), product, price, quantity and money, like follows:

![image-20220530094853017](https://tva1.sinaimg.cn/large/e6c9d24egy1h2q6wmbjhrj21e203imxw.jpg)

```sql
create
or replace function uppdate_bill()
    returns trigger
as
$$
declare
temp integer;
begin
    if
(tg_table_name = 'stockin' and tg_op = 'INSERT') then
        insert into bill (date, operator, product, price, quantity, money)
        VALUES (new.date, 'stockin', new.product_model, -new.purchase_price, new.quantity,
                -new.purchase_price * new.quantity);
return new;
end if;
    if
(tg_table_name = 'placeorder' and tg_op = 'INSERT') then
select unit_price
into temp
from model
where model = new.product_model;
insert into bill (date, operator, product, price, quantity, money)
values (new.contract_date, 'place order', new.product_model, temp, new.quantity, new.quantity * temp);
return new;
end if;
    if
(tg_table_name = 'placeorder' and tg_op = 'DELETE') then
select unit_price
into temp
from model
where model = old.product_model;
insert into bill (date, operator, product, price, quantity, money)
values (new.contract_date, 'delete place order', old.product_model, temp, -old.quantity, -old.quantity * temp);
return old;
end if;
    if
	......
return new;
end if;
end
$$
language plpgsql;

```

#### Order status updating

We created a procedure **update_order_type** to update the order type according to current time. We have four types in total, before contract, no delivery, no lodgment and finished. The procedure is as follows:

```sql
create
or replace procedure update_order_type()
as
$$
BEGIN
update placeOrder
set contract_type = 'Before Contract'
where contract_date > now() at time zone 'PRC';
update placeOrder
set contract_type = 'No Delivery'
where contract_date <= now()
  and estimated_delivery_date > now() at time zone 'PRC';
update placeOrder
set contract_type = 'No Lodgement'
where estimated_delivery_date <= now() at time zone 'PRC'
    AND lodgement_date > now() at time zone 'PRC';
update placeOrder
set contract_type = 'Finished'
where lodgement_date <= now() at time zone 'PRC';
end;
$$
language plpgsql;
```

To update the types automatically, we use the **schedule** annotation of springboot to do the updating every 5 seconds.

```java
    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        updateTypeMapper.scheduleFixedDelayTask();//"call update_order_type();"
        System.out.println(
        	"update order type at " + System.currentTimeMillis() / 1000);
    }
```





## Part - 3 Advanced Function

#### **1. Query the order list based on multiple parameters, and the parameters can be null or not.**

​		We have implemented a search bar for order queries in the front end, and each query will return an order. You can choose four sorting methods for query: ascending according to contract date, descending according to contract date, ascending according to product quantity, and descending according to product quantity. The query can be performed according to the entered contract number or the order of a specified rank in a certain sorting method.

#### **2. Design the Bill Module**

​		We have designed the bill module. When we try to complete stock, place orders, update orders, and delete orders, we will record such operations in a bill table in our database. The bill table records what the operation was, when the operation was performed, which products were operated on, the price of the products, the quantity of the products, and the final income or expenditure.

#### **3. Design a mechanism to change order status according to time and date**

​		We try to change the order status according to time and date. We design an interface called UpdateTypeMapper.java, in this interface we only declare a method scheduleFixedDelayTask with an annotation `@Select("call update_order_type();")`,  in which update_order_type is a procedure in create_table.sql. This procedure will change the order status in our table placeOrder based on the current time of our operating system. There are four contract types: before contract, no delivery, no lodgement, and finished.

#### **4. Encapsulate the features and implement a real back-end server instead of several independent scripts or programs**

​		We use the framework of 
