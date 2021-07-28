package com.fly.drools.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guoxiang
 * @version 1.0.0
 * @since 2021/7/27
 */
@Data
public class RuleBriefDto {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime createTime;

    public String getUrl() {
        return "/dynamic/" + id;
    }
}
