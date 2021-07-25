package com.fly.drools.util;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.util.HashMap;
import java.util.Map;

public class RuleUtils {

    private static final Map<String, KieContainer> CACHE = new HashMap<>();

    private static KieContainer getContainer(String ruleString) {
        KieContainer container = CACHE.get(ruleString);

        if (container != null) {
            return container;
        }

        synchronized (CACHE) {
            return CACHE.computeIfAbsent(ruleString, key -> {
                KieHelper kieHelper = new KieHelper();
                kieHelper.addContent(key, ResourceType.DRL);
                return kieHelper.getKieContainer();
            });
        }
    }

    public static KieSession getSession(String rule) {
        KieContainer container = getContainer(rule);
        return container.newKieSession();
    }

}
