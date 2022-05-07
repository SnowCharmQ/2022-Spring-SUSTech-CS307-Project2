drop table if exists center, enterprise, model, staff, stockIn, placeOrder, stock_info cascade;

create table center
(
    id   integer primary key,
    name varchar(50)-- primary key
);

create table enterprise
(
    id            integer primary key,
    name          varchar(100),
    country       varchar(50),
    city          varchar(50),
    supply_center varchar(50),-- references center,
    industry      varchar(50)
);

create table model
(
    id         integer primary key,
    number     varchar(20),
    model      varchar(100),
    name       varchar(100),
    unit_price integer
);

create table staff
(
    id            integer primary key,
    name          varchar(50),
    age           integer,
    gender        varchar(10),
    number        varchar(10),
    supply_center varchar(50),
    mobile_number varchar(15),
    type          varchar(20)
);

create table stockIn
(
    id             integer primary key,
    supply_center  varchar(50),
    product_model  varchar(50),
    supply_staff   varchar(10),
    date           date,
    purchase_price integer,
    quantity       integer
);

create table placeOrder
(
    contract_num            varchar(15),
    enterprise              varchar(100),
    product_model           varchar(50),
    quantity                integer,
    contract_manager        varchar(10),
    contract_date           date,
    estimated_delivery_date date,
    lodgement_date          date,
    salesman_num            varchar(10),
    contract_type           varchar(15)
);

create table stock_info
(
    supply_center varchar(50),
    product_model varchar(100),
    quantity      integer
);
create table contract
(
    contract_num varchar(15)
);

drop function if exists insert_contract();
create or replace function insert_contract()
    returns trigger
as
$$
begin
    IF NOT EXISTS(SELECT FROM contract WHERE contract_num = new.contract_num) THEN
        INSERT INTO contract VALUES (new.contract_num);
end IF;
return new;
end;
$$ language plpgsql;

create trigger record_contract
    before insert
    on placeOrder
    for each row
    execute procedure insert_contract();


truncate table center, enterprise, model, staff, stock_info, stockIn, placeOrder, contract cascade;


select * from stockIn order by product_model;
select * from stock_info;
select product_model, sum(quantity) from stock_info group by product_model;
select product_model, sum(quantity) from stockIn group by product_model;

