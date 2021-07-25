package com.fly.drools.service;

import com.fly.drools.entity.Param;
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

    public void insertParam(Param param) {
        log.warn("insert param  {}", param);
    }

}
