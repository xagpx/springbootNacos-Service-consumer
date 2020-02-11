package com.example.demo.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.util.ReturnT;

/**
 * 此类表示FallBack执行的时候，打印相应的日志
 * 如果需要访问产生回退触发器的原因，可以使用@ feignclient中的fallbackFactory属性。
 *
 */
@Component
public class FeignFallBackFactory implements HelloServiceFeign {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignFallBackFactory.class);
    public String hi(String id) {
        return new ReturnT<Object>(500,"提供者服务出错",null).toString();
    }
    public String test() {
        return new ReturnT<Object>(500,"提供者服务出错",null).toString();
    }
}