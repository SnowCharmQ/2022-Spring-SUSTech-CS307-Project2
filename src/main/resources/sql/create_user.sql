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