create table if not exists tb_rule
(
	id bigint auto_increment
		primary key,
    name varchar(32) null,
    rule_text text not null,
	create_time datetime null,
	update_time datetime null,
	description varchar(200) null
);

insert into TB_RULE (name, rule_text, create_time, update_time, description)
values ('动态规则1', 'package rules

import com.fly.drools.entity.RuleResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import com.fly.drools.test.TestService
import java.util.Map
import java.time.LocalDateTime
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.BeanPropertyRowMapper
import com.fly.drools.entity.Rule
import java.util.List

declare Param
    age: int
    name: String
    degree: String
    birthday: LocalDateTime
end

rule "discount_rule_4"
    when
        p: Map()
        param: Param()
        r: RuleResult()
        log: Logger()
        context: ApplicationContext()
        jdbc: JdbcTemplate()
    then
        log.info("drools execute, map={}", p);
        log.info("drools execute, param={}", param);

        TestService t = context.getBean(TestService.class);
        String res = t.test("123456");
        r.setData("hello world: " + param.getName());
        log.info("test spring context: {}", res);
        List<Rule> list = jdbc.query("select * from TB_RULE", new BeanPropertyRowMapper<>(Rule.class));
        log.info("rule list = {}", list);
end', now(), now(), 'Test Rule')