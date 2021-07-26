create table if not exists rule_demo.tb_rule
(
	id bigint auto_increment
		primary key,
	rule_text text not null,
	create_time datetime null,
	update_time datetime null,
	name varchar(32) null
)
comment '规则';

