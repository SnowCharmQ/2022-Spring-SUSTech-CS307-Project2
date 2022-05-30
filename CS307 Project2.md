# **Spring 2022 CS307 Project 2**

***Group Members:***

***SID: 12012705 Name: Li Jiacheng***

***SID: 12013006 Name: Qiu Yilun***

## Part - 1 Project Structure

Our group build our enterprise management system based on the Spring-Boot framework. And we also build our front client based on html, css and JavaScript. Our project structure is as follows:



The directory src.main.java contains the code of our server, src.main.resources contains the original data,  *xml of spring mappers, our sql tables, and the front client code. The directory src.test contains unit test functions.

All the APIs are in the directory src.main.java.com.cs307.projct.service, they will be introduced in the next parts.

## Part - 2 APIs description





## Part - 3 Advanced Function

#### **1. Query the order list based on multiple parameters, and the parameters can be null or not.**

​		We have implemented a search bar for order queries in the front end, and each query will return an order. You can choose four sorting methods for query: ascending according to contract date, descending according to contract date, ascending according to product quantity, and descending according to product quantity. The query can be performed according to the entered contract number or the order of a specified rank in a certain sorting method.

#### **2. Design the Bill Module**

​		We have designed the bill module. When we try to complete stock, place orders, update orders, and delete orders, we will record such operations in a bill table in our database. The bill table records what the operation was, when the operation was performed, which products were operated on, the price of the products, the quantity of the products, and the final income or expenditure.

#### **3. Design a mechanism to change order status according to time and date**

​		We try to change the order status according to time and date. We design an interface called UpdateTypeMapper.java, in this interface we only declare a method scheduleFixedDelayTask with an annotation `@Select("call update_order_type();")`,  in which update_order_type is a procedure in create_table.sql. This procedure will change the order status in our table placeOrder based on the current time of our operating system. There are four contract types: before contract, no delivery, no lodgement, and finished.

#### **4. Encapsulate the features and implement a real back-end server instead of several independent scripts or programs**

​		We use the framework of 
