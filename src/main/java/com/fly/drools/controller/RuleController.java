package com.fly.drools.controller;

import com.fly.drools.entity.Rule;
import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rule")
@Slf4j
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;


    /**
     * 新增或修改规则
     * @param rule  规则
     * @return      result
     */
    @PostMapping
    public RuleResult saveRule(@RequestBody Rule rule) {
        log.info("save rule: {}", rule);

        ruleService.save(rule);

        log.info("save rule success id: {}", rule.getId());
        return RuleResult.success();
    }


    /**
     * 新增或修改规则  纯文本
     *
     * @param ruleText  规则
     * @return          result
     */
    @PostMapping("text")
    public RuleResult saveRulePlainText(@RequestBody String ruleText) {
        log.info("save ruleText: {}", ruleText);

        Rule rule = new Rule();
        rule.setRuleText(ruleText);
        ruleService.save(rule);

        log.info("save ruleText success id: {}", rule.getId());
        return RuleResult.success();
    }



    /**
     * 删除规则
     *
     * @param id id
     * @return  规则
     */
    @DeleteMapping
    public RuleResult deleteById(@RequestParam Long id) {
        log.info("delete rule by id: {}", id);
        Rule delete = ruleService.delete(id);

        log.info("delete rule finish: {}", delete);
        return RuleResult.success();
    }


    /**
     * 分页查询
     * @param pageNo    pageNo
     * @param pageSize  pageSize
     * @return          page
     */
    @GetMapping
    public RuleResult page(@RequestParam Integer pageNo,
                           @RequestParam Integer pageSize) {
        Page<Rule> page = ruleService.page(pageNo, pageSize);
        return RuleResult.success(page);
    }


    @GetMapping("{id}")
    public RuleResult getById(@PathVariable Long id) {
        Rule rule = ruleService.getById(id);
        return RuleResult.success(rule);
    }

}
