package com.fly.drools.dto;

import com.fly.drools.entity.Rule;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author guoxiang
 * @version 1.0.0
 * @since 2021/7/28
 */
@Data
@Accessors(chain = true)
public class RuleDetailDto {

    private Long id;

    private String name;

    private String description;

    private String ruleText;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public String getUrl() {
        return "/dynamic/" + id;
    }

    public static RuleDetailDto convertFrom(Rule rule) {
        RuleDetailDto dto = new RuleDetailDto();

        BeanUtils.copyProperties(rule, dto);

        return dto;
    }
}
