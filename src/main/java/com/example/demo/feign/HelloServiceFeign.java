package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="nacos-provider",fallback  = FeignFallBackFactory.class)
public interface  HelloServiceFeign {
	 //服务中方法的映射路径
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    String hi(@PathVariable(value = "id") String id);
	
	@RequestMapping("/user")
    String test();
}
