package com.fly.drools.controller;

import com.fly.drools.entity.Param;
import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.TestService;
import com.fly.drools.util.RuleUtils;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.kie.api.KieServices;
import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collection;

@RestController
@RequestMapping("rule")
@Slf4j
@RequiredArgsConstructor
public class RuleController {

    private KieContainer kieContainer;

    private final TestService testService;

    @PostConstruct
    public void init() {
        kieContainer = KieServices.Factory.get().getKieClasspathContainer();
    }


    @GetMapping
    public String hello() {
        return "hello";
    }


    @GetMapping("test")
    public void testRule(Param param) {
        RuleResult result = new RuleResult();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(testService);
        kieSession.insert(param);
        kieSession.insert(result);
        kieSession.fireAllRules();
        kieSession.dispose();

        log.info("result = {}", result);
    }

    @PostMapping("utils")
    public void testRuleByUtils(Param param,
                                @RequestBody @ApiParam(defaultValue = RULE) String rule) {
        RuleResult result = new RuleResult();
        KieSession kieSession = RuleUtils.getSession(rule);
        kieSession.insert(testService);
        kieSession.insert(param);
        kieSession.insert(result);
        kieSession.fireAllRules();
        Collection<?> objects = kieSession.getObjects();
        log.info("work memory: {}", objects);
        kieSession.dispose();

        log.info("result = {}", result);
    }

    private static final String RULE =
            "package rules\n" +
            "import com.fly.drools.entity.Param\n" +
            "import com.fly.drools.entity.RuleResult\n" +
            "import com.fly.drools.service.TestService\n" +
            "rule \"discount_rule_4\"\n" +
            "    when\n" +
            "        p: Param()\n" +
            "        r: RuleResult()\n" +
            "        testService: TestService()\n" +
            "    then\n" +
            "        System.out.println(\"博士 result = \" + r);\n" +
            "        if (testService.getList().contains(p.getName())) {\n" +
            "            testService.insertParam(p);\n" +
            "\n" +
            "        } else {\n" +
            "            throw new RuntimeException(\"name error\");\n" +
            "        }\n" +
            "end";
}
