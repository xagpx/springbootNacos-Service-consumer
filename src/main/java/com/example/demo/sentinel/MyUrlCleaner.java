package com.example.demo.sentinel;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;


/**
 * @author hj
 * 2019-08-15 14:24
 * 扩展restFul-Url，让/shares/* 使用相同的逻辑
 */
/*@Component*/
public class MyUrlCleaner implements UrlCleaner {
	  public String clean(String url) {
        String[] split = url.split("/");
        String web_url=Arrays.stream(split)
                .map(a -> {
                    if (NumberUtils.isNumber(a)) {
                        return "{number}";
                    }
                    return a;
                })
                .reduce((a, b) -> a + "/" + b)
                .orElse("");
        return  web_url;
    }
}