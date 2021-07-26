package com.fly.drools.service;

import com.fly.drools.dao.RuleDao;
import com.fly.drools.entity.Rule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fly.drools.common.RuleConstant.CREATE_TIME;
import static com.fly.drools.common.RuleErrorMessage.RULE_NULL_ERROR;
import static com.fly.drools.common.RuleErrorMessage.RULE_TEXT_NULL_ERROR;
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
public class RuleService {

    private static final Map<String, KieContainer> CACHE_RULE = new HashMap<>();
    private static final Map<Long, KieContainer> CACHE_ID = new HashMap<>();

    private final ApplicationContext context;
    private final RuleDao ruleDao;
    private final JdbcTemplate jdbcTemplate;


    /**
     * 新增或保存
     *
     * @param rule 规则
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Rule rule) {
        Long id = rule.getId();
        LocalDateTime now = LocalDateTime.now();

        //如果是新增
        if (ObjectUtils.isEmpty(id)) {
            rule.setCreateTime(now);
        }

        rule.setUpdateTime(now);
        String ruleText = rule.getRuleText();

        Assert.hasText(ruleText, RULE_TEXT_NULL_ERROR);

        //保存数据库
        ruleDao.save(rule);

        //刷新缓存
        uninstallById(rule.getId());
    }


    /**
     * 分页查询
     * @param pageNo    页码
     * @param pageSize  大小
     * @return          page
     */
    public Page<Rule> page(Integer pageNo, Integer pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC, CREATE_TIME);

        return ruleDao.findAll(PageRequest.of(pageNo - 1, pageSize, sort));
    }


    /**
     * 查询详情
     *
     * @param id id
     * @return  详情
     */
    public Rule getById(Long id) {

        Optional<Rule> rule = ruleDao.findById(id);

        if (rule.isEmpty()) {
            return null;
        }

        return rule.get();
    }


    /**
     * 删除规则
     *
     * @param id id
     */
    public Rule delete(Long id) {

        Optional<Rule> rule = ruleDao.findById(id);

        if (rule.isEmpty()) {
            return null;
        }

        ruleDao.deleteById(id);

        //清缓存
        uninstallById(id);
        return rule.get();
    }


    /**
     * 根据规则获取对应的session
     *
     * @param rule rule
     * @return      session
     */
    public KieSession getSessionByRule(String rule) {
        KieContainer container = getContainerByRule(rule);

        KieSession session = container.newKieSession();

        //设置日志和spring容器
        session.insert(log);
        session.insert(context);
        session.insert(jdbcTemplate);

        return session;
    }


    /**
     * 根据id获取对应的session
     *
     * @param id    id
     * @return      session
     */
    public KieSession getSessionById(Long id) {
        KieContainer container = getContainerById(id);

        KieSession session = container.newKieSession();

        //设置日志和spring容器
        session.insert(log);
        session.insert(context);
        session.insert(jdbcTemplate);

        return session;
    }



    /**
     * 卸载某规则
     *
     * @param id id
     */
    private void uninstallById(Long id) {
        KieContainer container = CACHE_ID.get(id);
        if (container == null) {
            return;
        }

        container.dispose();
        CACHE_ID.remove(id);
    }


    /**
     * 根据id获取container
     *
     * @param id    id
     * @return      容器
     */
    private KieContainer getContainerById(Long id) {
        KieContainer container = CACHE_ID.get(id);

        if (container != null) {
            return container;
        }

        synchronized (CACHE_ID) {
            return CACHE_ID.computeIfAbsent(id, k -> new KieHelper().addContent(getRuleById(k), DRL).getKieContainer());
        }
    }


    /**
     * 从数据库获取规则
     *
     * @param id    id
     * @return      规则
     */
    private String getRuleById(Long id) {
        Optional<Rule> rule = ruleDao.findById(id);

        Assert.isTrue(rule.isPresent(), RULE_NULL_ERROR);
        return rule.get().getRuleText();
    }


    /**
     * 从缓存获取container
     *
     * @param rule  rule
     * @return      container
     */
    private KieContainer getContainerByRule(String rule) {
        KieContainer container = CACHE_RULE.get(rule);

        if (container != null) {
            return container;
        }

        synchronized (CACHE_RULE) {
            return CACHE_RULE.computeIfAbsent(rule, key -> new KieHelper().addContent(key, DRL).getKieContainer());
        }
    }


}
