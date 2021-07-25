package com.fly.drools.entity;

import lombok.Data;

@Data
public class RuleResult {

    private String message;

    private Integer code;

    private Object data;
}
