package com.fly.drools.controller;

import com.fly.drools.entity.Param;
import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.TestService;
import com.fly.drools.util.RuleUtils;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
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

    private final RuleUtils ruleUtils;

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



    /**
     * 动态获取规则，用户传规则到后台，然后根据字符串生成session
     *
     * @param param     param
     * @param rule      规则
     */
    @PostMapping("dynamic")
    public void testRuleByUtils(Param param,
                                @RequestBody @ApiParam(example = RULE) String rule) {
        RuleResult result = new RuleResult();
        KieSession kieSession = ruleUtils.getSession(rule);
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
