# **Spring 2022 CS307 Project 2**

***Group Members:***

***SID: 12012705 Name: Li Jiacheng***

***SID: 12013006 Name: Qiu Yilun***

## Part - 1 Project Structure

Our group build our enterprise management system based on the Spring-Boot framework. And we also build our front client based on HTML, CSS, and JavaScript. Our project structure is as follows:



The directory *src.main.java* contains the code of our server, *src.main.resources* contains the original data,  **.xml* of spring mappers, our SQL tables, and the front client code. The directory *src.test* contains unit test functions.

All the APIs are in the directory *src.main.java.com.cs307.projct.service*, they will be introduced in the next parts.

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
if (model.getModel()!=null){
    sql.append(" and model = \'"+ model.getModel()+"\'");
}
if (model.getNumber()!=null){
    sql.append(" and number = \'" + model.getNumber()+"\'");
}
if (model.getName()!=null){
    sql.append(" and name = \'" + model.getName());
}
if (model.getUnitPrice()!=null){
    sql.append(" and unit_price ="+ model.getUnitPrice());
}
```

#### **2) stockIn:**

**Parameters:** the information on stock

**Returns:** void

**Throws:** self-defined exceptions when stock in illegal information defined in the project document

#### **3) placeOrder:**

**Parameters:** the information of an order

**Returns:** void

**Throws:** self-defined exceptions when place order illegally defined in the project document

#### **4) updateOrder:**

**Parameters:** the information of an updated order

**Returns:** void

**Throws:** self-defined exceptions when updated order illegally defined by in the project document

Other APIs all meet the same requirements of the project document, for the limit of this report, so further description.

## Part - 3 Advanced Requirements

#### **1. Query the order list based on multiple parameters, and the parameters can be null or not.**

​		We have implemented a search bar for order queries in the front end, and each query will return an order. You can choose four sorting methods for query: ascending according to contract date, descending according to contract date, ascending according to product quantity, and descending according to product quantity. The query can be performed according to the entered contract number or the order of a specified rank in a certain sorting method. Here is an example to show how to query the order.

![image-20220530162403903](https://s2.loli.net/2022/05/30/XNi8lSkjsW4pneL.png)

#### **2. Design the Bill Module**

​		We have designed the bill module. When we try to complete stock, place orders, update orders, and delete orders, we will record such operations in a *bill* table in our database by trigger as follows. 

```plsql
create trigger bill_record
    before insert
    on stockin
    for each row
execute procedure uppdate_bill();
create trigger bill_order
    before insert or
        delete
        or update
    on placeOrder
    for each row
execute procedure uppdate_bill();
```

​		The *bill* table records what the operation was, when the operation was performed, which products were operated on, the price of the products, the quantity of the products, and the final income or expenditure. 

#### **3. Design a mechanism to change order status according to time and date**

​		We try to change the order status according to time and date. We design an interface called *UpdateTypeMapper.java*, in this interface we only declare a method called *scheduleFixedDelayTask* with an annotation `@Select("call update_order_type();")`,  in which update_order_type is a procedure in *create_table.sql*. This procedure will change the order status in our table *placeOrder* based on the current time of our operating system. There are four contract types: before contract, no delivery, no lodgement, and finished. In *ProjectApplication.java*, we need to add an annotation `@EnableScheduling` to automatically change the order status.

#### **4. Encapsulate the features and implement a real back-end server instead of several independent scripts or programs**

​		We use the framework of SpringBoot and MyBatis. We can easily connect to our server by typing `http://localhost:8080/web/*.html`, here **.html* denotes our web pages. All HTML files are stored in the directory *src/main/resources/static/web*. We will introduce them in details in the following parts.

#### **5. Apply database connection pools**

​		As we mentioned above, we use the SpringBoot framework. The SpringBoot project uses the HikariCP database connection pool by default, and we added a configuration in the *application.properties* file to ensure that the database connection pool works properly when the program is running.

#### **6. A usable and beautiful GUI design for data presentation**

​		As we mentioned above, we store all our web pages in the directory *src/main/resources/static/web*. The main pages of our programs are *db-login.html*, *db-reg.html*, *db-pwd-change.html*, *db-user.html*, *db-user-management.html*, and *db-menu.html*. The first five web pages are used to manage user privileges, and the last web page is used to present our APIs and query the orders. Here we will introduce the last web page first.

​		On this web page, we create a side navigation bar menu to choose different APIs as follows.

![image-20220530161937878](https://s2.loli.net/2022/05/30/Nshz3U4MiLKn9BP.png)

​		Here we just show API 11. In API 3, 4, 5, 12, and 13, we will provide the user with an input field to accept what the user wants to select or update or delete. In other APIs, we can generate the result of the corresponding question and show the result in the front end.

#### **7. Proper use of user privileges, procedures, indexing, and views in a reasonable manner**

**User privileges:** 

​		We implement the user privilege management in the front end. In PostgreSQL, all users are stored in the table *pg_roles*. Here in our program, we use a table called t_user to simulate storing all created users. In *db-reg.html*, the user can enter a username and password to register an account. In *db-login.html*, if the user enters the correct username and password, he or she will successfully log in to our server. In *db-user.html*, the current user can choose different pages including *db-pwd-change.html* to change his or her password, *db-menu.html* to select different APIs, *db-tables.html* to view the four basic tables, and *db-user-management.html* only for superuser to manage the privileges of different users. Here is an example to grant or revoke the privilege, the superuser can manage all created users. If the superuser changes the status of corresponding buttons, the user will be granted or be revoked some privileges after clicking the SUBMIT button.

![image-20220530193525381](https://s2.loli.net/2022/05/30/sJcBMALiRnw6K2p.png)

**Procedures:**

​		To update the order status according to time and date, we implement a procedure called *update_order_type* as follows.

```plsql
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

​		Also, we implement some other functions including *insert_contract* to judge whether the operation of inserting is legal and *update_bill* to insert corresponding operations to the *bill* table.

**Indexing:**

​		After we import the data into the four basic tables, we index some of the columns in the basic tables. The statements are just as follows. Indexes are added to improve the speed of querying the data for corresponding tables.

```plsql
create index staff_index on staff (number);
create index center_index on center (name);
create index model_index on model (model);
create index enterprise_index on enterprise (name);
```

​		
