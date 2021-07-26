package com.fly.drools.dao;

import com.fly.drools.entity.Rule;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author guoxiang
 */
public interface RuleDao extends PagingAndSortingRepository<Rule, Long> {
}
