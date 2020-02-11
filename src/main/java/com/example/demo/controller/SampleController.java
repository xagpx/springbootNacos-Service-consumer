package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.demo.feign.HelloServiceFeign;
import com.example.demo.util.ExceptionUtil;
import com.example.demo.util.ReturnT;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(tags = "SampleApi")
@RefreshScope
@RestController
class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	@Autowired
	private HelloServiceFeign helloServiceFeign;
	private RestTemplate restTemplate;

	@Autowired
	public SampleController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@ApiOperation(value = "查询单个用户", notes = "根据用户ID查询用户")
	  @ApiImplicitParams({
	          @ApiImplicitParam(name = "id", value = "用户id",required = true,paramType = "query", dataType = "String")
	  })
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String echo(@PathVariable String id) {
		return restTemplate.getForObject("http://nacos-provider/users/" + id, String.class);
	}
	
  @RequestMapping(value = "/getString/{id}", method = RequestMethod.GET)
    public String getString(@PathVariable String id){
      return this.helloServiceFeign.hi(id);
   }
	
	@RequestMapping("/test")
    public String test(){
      return this.helloServiceFeign.test();
   }
 	@SentinelResource(value = "flowRule" , blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class })
	@RequestMapping("/query")
    public String query(String str){
      return "1"+str;
   }
 	
 	@SentinelResource
	@RequestMapping("/a")
    public String a(String str){
      return "1"+str;
   }
 	@SentinelResource
	@RequestMapping("/b")
    public String b(String str,String x){
      return "1"+str+x;
   }
	@SentinelResource(value = "ax" , blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class })
	@RequestMapping("/e/{id}")
    public String e(@PathVariable("id") String id){
      return "1"+id;
   }
	@SentinelResource(value = "bx" , blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class })
	@RequestMapping("/c/{id}")
    public String c(@PathVariable("id") String id){
      return "1"+id;
   }
	@SentinelResource
	@RequestMapping("/c/{id}/d")
    public String d(@PathVariable("id") String id){
      return "1"+id;
   }
	/**
	 * 默认限流统一接口
	 */
	@RequestMapping("/limiting")
    public ReturnT<String> limiting(){
		ReturnT<String> t=new ReturnT<String>(500,"限流了",null);
      return t;
   }
	
	/**
	 * 默认限流统一接口
	 */
	@RequestMapping("/test/a")
    public ReturnT<String> a(){
		ReturnT<String> t=new ReturnT<String>(500,"tesst",null);
      return t;
   }
}
