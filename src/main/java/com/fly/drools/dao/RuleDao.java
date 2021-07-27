package com.fly.drools.dao;

import com.fly.drools.dto.RuleBriefDto;
import com.fly.drools.entity.Rule;
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
