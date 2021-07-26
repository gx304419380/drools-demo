package com.fly.drools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fly.drools.entity.RuleResult;
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

    private final RuleService ruleService;

    private final ObjectMapper objectMapper;

    @PostMapping("{ruleId}")
    public RuleResult handle(@PathVariable Long ruleId, @RequestBody Map<String, Object> param) {

        log.debug("- handle dynamic rule: {}, param: {}", ruleId, param);

        //从缓存中获取session
        KieSession session = ruleService.getSessionById(ruleId);

        //接收参数，并转为动态参数
        generateDynamicParam(session, param);

        RuleResult result = new RuleResult();

        //执行规则引擎
        try {
            session.insert(param);
            session.insert(result);
            session.fireAllRules();
        } finally {
            session.dispose();
        }

        log.debug("- dynamic controller result: {}", result);
        return result;
    }

    /**
     * 这里使用jackson进行赋值，支持更多数据类型
     * 传统赋值方式如下，只支持基本数据类型：
     * Object o = factType.newInstance();
     * factType.setFromMap(o, param);
     *
     * @param session   session
     * @param param param
     */
    private void generateDynamicParam(KieSession session, Map<String, Object> param) {

        Collection<KiePackage> packages = session.getKieBase().getKiePackages();
        KiePackage kiePackage = packages.stream().filter(p -> !p.getRules().isEmpty()).findFirst().orElse(null);

        if (kiePackage == null) {
            return;
        }

        Collection<FactType> factTypes = kiePackage.getFactTypes();
        for (FactType factType : factTypes) {
            Object value = objectMapper.convertValue(param, factType.getFactClass());
            session.insert(value);
        }

    }

}
