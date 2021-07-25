package com.fly.drools.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.kie.api.io.ResourceType.DRL;

/**
 * 规则引擎动态获取session工具类
 *
 * @author guoxiang
 * @since 2021-07
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleUtils {

    private static final Map<String, KieContainer> CACHE = new HashMap<>();

    private final ApplicationContext context;


    /**
     * 根据规则获取对应的session
     *
     * @param rule rule
     * @return      session
     */
    public KieSession getSession(String rule) {
        KieContainer container = getContainer(rule);

        KieSession session = container.newKieSession();

        //设置日志和spring容器
        session.insert(log);
        session.insert(context);

        return session;
    }


    /**
     * 从缓存获取container
     *
     * @param rule  rule
     * @return      container
     */
    private KieContainer getContainer(String rule) {
        KieContainer container = CACHE.get(rule);

        if (container != null) {
            return container;
        }

        synchronized (CACHE) {
            return CACHE.computeIfAbsent(rule, key -> new KieHelper().addContent(key, DRL).getKieContainer());
        }
    }

}
