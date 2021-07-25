package com.fly.drools.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("tb_rule")
public class Rule {

    @Id
    private Long id;

    private String packageName;

    private String ruleText;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
