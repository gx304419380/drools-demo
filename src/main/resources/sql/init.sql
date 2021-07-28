create table if not exists tb_rule
(
	id bigint auto_increment
		primary key,
    name varchar(32) null,
    rule_text text not null,
	create_time datetime null,
	update_time datetime null,
	description varchar(200) null
)
comment '规则';