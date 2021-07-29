package com.fly.rule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fly.rule.entity.RuleResult;
import lombok.RequiredArgsConstructor;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author guoxiang
 * @version 1.0.0
 * @since 2021/7/28
 */
@Service
@RequiredArgsConstructor
public class DynamicService {

    private final RuleService ruleService;

    private final ObjectMapper objectMapper;

    public RuleResult handle(Long id, Map<String, Object> param) {
        //从缓存中获取session
        KieSession session = ruleService.getSessionById(id);

        //接收参数，并转为动态参数
        generateDynamicParam(session, param);

        RuleResult result = new RuleResult().setCode(0);

        //执行规则引擎
        try {
            session.insert(param);
            session.insert(result);
            session.fireAllRules();
        } finally {
            session.dispose();
        }

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
