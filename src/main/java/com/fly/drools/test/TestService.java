package com.fly.drools.test;

import com.fly.drools.entity.RuleParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TestService {

    public List<String> getList() {
        return Arrays.asList("aaa", "bbb", "ccc");
    }

    public void insertParam(RuleParam param) {
        log.warn("insert param  {}", param);
    }

    public String test(String value) {
        return "this string from test service " + value;
    }

}
