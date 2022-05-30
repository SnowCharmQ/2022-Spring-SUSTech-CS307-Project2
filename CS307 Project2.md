# CS307 Project2

Group members:



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

