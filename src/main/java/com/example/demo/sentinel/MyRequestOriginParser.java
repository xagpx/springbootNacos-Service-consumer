package com.example.demo.sentinel;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

/**
  * @Title:MyRequestOriginParser.java
  * @Description:实现区分来源当配置流控规则或授权规则时，若需要针对调用来源进行限流，得先实现来源的区分
  * @Author:82322156@qq.com
  * @Date:2020年2月8日下午3:21:41
  * @Version:1.0
  * Copyright 2020  Internet  Products Co., Ltd.
*/
@Component
public class MyRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 从header中获取名为 origin 的参数并返回
        String origin = request.getHeader("origin");
        /*if (StringUtils.isBlank(origin)) {
            // 如果获取不到，则抛异常
            String err = "origin param must not be blank!";
            throw new IllegalArgumentException(err);
        }*/

        return origin;
    }
}