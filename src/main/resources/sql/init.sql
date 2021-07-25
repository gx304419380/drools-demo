create table rule_demo.tb_rule
(
	id bigint auto_increment
		primary key,
	rule_text text not null,
	create_time datetime null,
	update_time datetime null,
	package_name varchar(255) null
)
comment '规则';

