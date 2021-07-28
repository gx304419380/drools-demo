package com.fly.drools.controller;

import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.DynamicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 动态接口
     *
     * @param ruleId    ruleId
     * @param param param
     * @return      result
     */
    @PostMapping("{ruleId}")
    public RuleResult handle(@PathVariable Long ruleId, @RequestBody Map<String, Object> param) {

        log.debug("- handle dynamic rule: {}, param: {}", ruleId, param);

        RuleResult result = dynamicService.handle(ruleId, param);

        log.debug("- dynamic controller result: {}", result);
        return result;
    }

}
