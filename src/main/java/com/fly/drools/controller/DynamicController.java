package com.fly.drools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.DynamicService;
import com.fly.drools.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * 动态controller
 *
 * @author guoxiang
 */
@RestController
@RequestMapping("dynamic")
@Slf4j
@RequiredArgsConstructor
public class DynamicController {

    private final DynamicService dynamicService;

    @PostMapping("{ruleId}")
    public RuleResult handle(@PathVariable Long ruleId, @RequestBody Map<String, Object> param) {

        log.debug("- handle dynamic rule: {}, param: {}", ruleId, param);

        RuleResult result = dynamicService.handle(ruleId, param);

        log.debug("- dynamic controller result: {}", result);
        return result;
    }

}
