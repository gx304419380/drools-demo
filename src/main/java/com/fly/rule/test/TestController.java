package com.fly.rule.test;

import com.fly.rule.entity.RuleResult;
import com.fly.rule.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @author guoxiang
 */
@RestController
@RequestMapping("test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private KieContainer kieContainer;

    private final RuleService ruleService;

    private final TestService testService;

    /**
     * 初始化drools
     */
    @PostConstruct
    public void init() {
        kieContainer = KieServices.Factory.get().getKieClasspathContainer();
    }


    /**
     * 读取静态规则文件
     *
     * @param param 参数
     */
    @GetMapping("static")
    public void testRule(TestParam param) {
        RuleResult result = new RuleResult();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(testService);
        kieSession.insert(param);
        kieSession.insert(result);
        kieSession.fireAllRules();
        kieSession.dispose();

        log.info("result = {}", result);
    }



    /**
     * 动态获取规则，用户传规则到后台，然后根据字符串生成session
     *
     * @param param     param
     * @param rule      规则
     */
    @PostMapping("dynamic")
    public void testRuleByUtils(TestParam param, @RequestBody String rule) {
        RuleResult result = new RuleResult();
        KieSession kieSession = ruleService.getSessionByRule(rule);
        kieSession.insert(testService);
        kieSession.insert(param);
        kieSession.insert(result);
        kieSession.fireAllRules();
        Collection<?> objects = kieSession.getObjects();
        log.info("work memory: {}", objects);
        kieSession.dispose();

        log.info("result = {}", result);
    }
}
