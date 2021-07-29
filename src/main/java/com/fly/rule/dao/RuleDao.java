package com.fly.rule.dao;

import com.fly.rule.dto.RuleBriefDto;
import com.fly.rule.entity.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author guoxiang
 */
public interface RuleDao extends PagingAndSortingRepository<Rule, Long> {
    /**
     * 根据名称查询
     *
     * @param name  name
     * @param pageable  pageable
     * @return          page
     */
    Page<RuleBriefDto> findByNameLike(String name, Pageable pageable);
}
