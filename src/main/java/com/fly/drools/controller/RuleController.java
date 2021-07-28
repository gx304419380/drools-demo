package com.fly.drools.controller;

import com.fly.drools.dto.RuleBriefDto;
import com.fly.drools.dto.RuleDetailDto;
import com.fly.drools.entity.Rule;
import com.fly.drools.entity.RuleResult;
import com.fly.drools.service.DynamicService;
import com.fly.drools.service.RuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fly.drools.common.RuleErrorMessage.RULE_SYNTAX_ERROR;
import static com.fly.drools.common.RuleErrorMessage.RULE_TEXT_NULL_ERROR;

/**
 * 规则controller
 *
 * @author guoxiang
 */
@RestController
@RequestMapping("rule")
@Slf4j
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;
    private final DynamicService dynamicService;

    /**
     * 动态接口 POST
     *
     * @param ruleId    ruleId
     * @param param param
     * @return      result
     */
    @PostMapping("/dynamic/{ruleId}")
    public RuleResult handlePost(@PathVariable Long ruleId, @RequestBody Map<String, Object> param) {

        log.debug("- handle post dynamic rule: {}, param: {}", ruleId, param);

        RuleResult result = dynamicService.handle(ruleId, param);

        log.debug("- dynamic post controller result: {}", result);
        return result;
    }

    /**
     * 动态接口 GET
     *
     * @param ruleId    ruleId
     * @param param param
     * @return      result
     */
    @GetMapping("/dynamic/{ruleId}")
    public RuleResult handleGet(@PathVariable Long ruleId, @RequestParam Map<String, Object> param) {

        log.debug("- handle get dynamic rule: {}, param: {}", ruleId, param);

        RuleResult result = dynamicService.handle(ruleId, param);

        log.debug("- dynamic get controller result: {}", result);
        return result;
    }


    /**
     * 新增或修改规则
     * @param rule  规则
     * @return      result
     */
    @PostMapping
    public RuleResult saveRule(@RequestBody Rule rule) {
        log.info("save rule: {}", rule);

        checkRuleText(rule.getRuleText());
        ruleService.save(rule);

        log.info("save rule success id: {}", rule.getId());
        return RuleResult.success(rule.getId());
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
        checkRuleText(ruleText);

        Rule rule = new Rule();
        rule.setRuleText(ruleText);
        ruleService.save(rule);

        log.info("save ruleText success id: {}", rule.getId());
        return RuleResult.success(rule.getId());
    }


    /**
     * 校验规则是否有问题
     *
     * @param ruleText 规则文本
     */
    private void checkRuleText(String ruleText) {
        Assert.hasText(ruleText, RULE_TEXT_NULL_ERROR);

        KieHelper helper = new KieHelper();
        Results results = helper.addContent(ruleText, ResourceType.DRL).verify();
        List<Message> messages = results.getMessages();
        if (messages.isEmpty()) {
            return;
        }

        log.error("verify result: {}", results);
        String error = RULE_SYNTAX_ERROR + "\n" +
                messages.stream().map(Message::getText).collect(Collectors.joining("\n"));

        throw new IllegalArgumentException(error);
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
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String name) {

        Page<RuleBriefDto> page = ruleService.page(pageNo, pageSize, name);
        return RuleResult.success(page);
    }


    @GetMapping("{id}")
    public RuleResult getById(@PathVariable Long id) {
        Rule rule = ruleService.getById(id);
        return RuleResult.success(RuleDetailDto.convertFrom(rule));
    }

}
