create table if not exists rule_demo.tb_rule
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

INSERT INTO rule_demo.tb_rule (id, name, rule_text, create_time, update_time, description) VALUES (28, 'test', 'package rules

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
        List<Rule> list = jdbc.query("select * from tb_rule", new BeanPropertyRowMapper<>(Rule.class));
        log.info("rule list = {}", list);
end', '2021-07-27 09:34:16', '2021-07-28 11:35:29', '阿凡达尬舞人噶为如果阿瓦噶人噶微软官方啊我日尬舞人噶为啊我发噶违法啊哥哥二哥奥尔格如果sergeA而是如果奥尔格色弱各色如果而是各色染个色染个色如果色弱各色如果QWEFGAERGERGERG ERSG');
INSERT INTO rule_demo.tb_rule (id, name, rule_text, create_time, update_time, description) VALUES (29, 'test', 'package rules

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
import com.fly.drools.test.TestService

rule "discount_rule_4"
    when
        p: Map()
        r: RuleResult()
        log: Logger()
        context: ApplicationContext()
        jdbc: JdbcTemplate()
    then
        log.info("p = {}", p);
        TestService testService = context.getBean(TestService.class);
        String t =testService.test("测试规则！");
        log.info("test service: {}", t);

        if ((Integer)p.get("age") <= 18) {
            log.info("儿童");
            r.setData("儿童");
        }
        r.setData("成人");
end', '2021-07-27 09:34:17', '2021-07-28 12:59:18', null);
INSERT INTO rule_demo.tb_rule (id, name, rule_text, create_time, update_time, description) VALUES (30, '页面测试规则1', 'package rules

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
        List<Rule> list = jdbc.query("select * from tb_rule", new BeanPropertyRowMapper<>(Rule.class));
        log.info("rule list = {}", list);
end', '2021-07-28 13:06:22', '2021-07-28 13:06:22', '这是一个测试规则');
INSERT INTO rule_demo.tb_rule (id, name, rule_text, create_time, update_time, description) VALUES (31, '测试2', 'package rules

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
        List<Rule> list = jdbc.query("select * from tb_rule", new BeanPropertyRowMapper<>(Rule.class));
        log.info("rule list = {}", list);
end', '2021-07-28 13:13:12', '2021-07-28 13:14:46', '测试2');
INSERT INTO rule_demo.tb_rule (id, name, rule_text, create_time, update_time, description) VALUES (32, '测试3', 'package rules

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
        List<Rule> list = jdbc.query("select * from tb_rule", new BeanPropertyRowMapper<>(Rule.class));
        log.info("rule list = {}", list);
end', '2021-07-28 13:17:14', '2021-07-28 13:17:14', '测试3');
