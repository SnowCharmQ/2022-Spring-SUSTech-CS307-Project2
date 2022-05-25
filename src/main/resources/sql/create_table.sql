drop table if exists center, enterprise, model, staff,
    stockIn, placeOrder, stock_info, contract, t_user cascade;

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
    product_model  varchar(100),
    supply_staff   varchar(10),
    date           date,
    purchase_price integer,
    quantity       integer
);

create table placeOrder
(
    contract_num            varchar(15),
    enterprise              varchar(100),
    product_model           varchar(100),
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
    contract_num     varchar(15),
    contract_manager varchar(15),
    enterprise       varchar(100)
);

drop function if exists insert_contract();
create
or replace function insert_contract()
    returns trigger
as
$$
begin
    IF
NOT EXISTS(SELECT FROM contract WHERE contract_num = new.contract_num) THEN
        INSERT INTO contract (contract_num, contract_manager, enterprise)
        VALUes (new.contract_num, new.contract_manager, new.enterprise);
end if;
return new;
end;
$$
language plpgsql;

create trigger record_contract
    before insert
    on placeorder
    for each row
    execute procedure insert_contract();


truncate table center, enterprise, model, staff, stock_info, stockIn, placeOrder, contract cascade;

create table t_user
(
    username   varchar(20),
    pwd        varchar(100),
    salt       varchar(100),
    is_super   boolean,
    can_insert boolean,
    can_delete boolean,
    can_update boolean,
    can_select boolean
);

create table bill
(
    date     date,
    operator varchar(20),
    product  varchar(100),
    price    integer,
    quantity integer,
    money    integer
);

create
or replace function insert_contract()
    returns trigger
as
$$
begin
    IF
NOT EXISTS(SELECT FROM contract WHERE contract_num = new.contract_num) THEN
        INSERT INTO contract (contract_num, contract_manager, enterprise)
        VALUes (new.contract_num, new.contract_manager, new.enterprise);
end if;
return new;
end;
$$
language plpgsql;

create trigger record_contract
    before insert
    on placeorder
    for each row
    execute procedure insert_contract();

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
(tg_table_name = 'placeorder' and tg_op = 'UPDATE') then
select unit_price
into temp
from model
where model = old.product_model;
insert into bill (date, operator, product, price, quantity, money)
values (new.contract_date, 'update place order', new.product_model, temp, new.quantity - old.quantity,
        (new.quantity - old.quantity) * temp);
return new;
end if;
end
$$
language plpgsql;

create trigger bill_record
    before insert
    on stockin
    for each row
    execute procedure uppdate_bill();

drop trigger bill_order on placeOrder;
create trigger bill_order
    before insert or
delete
or update
    on placeOrder
    for each row
execute procedure uppdate_bill();

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
