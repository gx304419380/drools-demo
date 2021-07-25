package com.fly.drools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DroolsDemoApplicationTests {

    @Test
    void contextLoads() {
        String s = "package rules\n" +
                "\n" +
                "import com.fly.drools.test.TestParam\n" +
                "import com.fly.drools.entity.RuleResult\n" +
                "import org.slf4j.Logger\n" +
                "import org.slf4j.LoggerFactory\n" +
                "import org.springframework.context.ApplicationContext\n" +
                "import java.util.List\n" +
                "import com.fly.drools.test.TestService\n" +
                "\n" +
                "rule \"discount_rule_4\"\n" +
                "    when\n" +
                "        p: TestParam()\n" +
                "        r: RuleResult()\n" +
                "        log: Logger()\n" +
                "        context: ApplicationContext()\n" +
                "        testService: TestService()\n" +
                "    then\n" +
                "        log.info(\"drools execute...\");\n" +
                "\n" +
                "        List<String> list = testService.getList();\n" +
                "\n" +
                "        if (list.contains(p.getName())) {\n" +
                "            testService.insertParam(p);\n" +
                "            r.setData(200);\n" +
                "        }\n" +
                "\n" +
                "        TestService t = context.getBean(TestService.class);\n" +
                "        String res = t.test(\"123456\");\n" +
                "        r.setData(100);\n" +
                "        log.info(\"test spring context: {}\", res);\n" +
                "end";
    }

}
