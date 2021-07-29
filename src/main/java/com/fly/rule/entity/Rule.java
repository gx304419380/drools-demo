package com.fly.rule.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author guoxiang
 */
@Data
@Table("TB_RULE")
public class Rule {

    @Id
    private Long id;

    private String name;

    private String description;

    private String ruleText;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
