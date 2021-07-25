package com.fly.drools.controller;

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
 */
@RestController
@RequestMapping("dynamic")
@Slf4j
@RequiredArgsConstructor
public class DynamicController {

    private final RuleService ruleService;

    @PostMapping("{ruleId}")
    public RuleResult handle(@PathVariable Long ruleId, @RequestBody Map<String, Object> param)
            throws IllegalAccessException, InstantiationException {

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

    private void generateDynamicParam(KieSession session, Map<String, Object> param)
            throws IllegalAccessException, InstantiationException {

        Collection<KiePackage> packages = session.getKieBase().getKiePackages();
        KiePackage kiePackage = packages.stream().filter(p -> !p.getRules().isEmpty()).findFirst().orElse(null);

        if (kiePackage == null) {
            return;
        }

        Collection<FactType> factTypes = kiePackage.getFactTypes();
        for (FactType factType : factTypes) {
            Object instance = factType.newInstance();
            factType.setFromMap(instance, param);
            session.insert(instance);
        }

    }

}
