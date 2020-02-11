package com.example.demo.sentinel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.example.demo.util.MyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Data;

/**
  * @Title:MyUrlBlockHandler.java
  * @Description:TODO
  * @Author:82322156@qq.com
  * @Date:2020年2月8日下午12:25:10
  * @Version:1.0
  * Copyright 2020  Internet  Products Co., Ltd.
*/
@Component
public class MyUrlBlockHandler implements BlockExceptionHandler {
	public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
	        MyResponse errorResponse = null;
	        // 不同的异常返回不同的提示语
	        if (e instanceof FlowException) {
	            errorResponse = MyResponse.builder()
	                    .status(100).message("接口限流了")
	                    .exception(e)
	                    .build();
	        } else if (e instanceof DegradeException) {
	            errorResponse = MyResponse.builder()
	                    .status(101).message("服务降级了")
	                    .exception(e)
	                    .build();
	        } else if (e instanceof ParamFlowException) {
	            errorResponse = MyResponse.builder()
	                    .status(102).message("热点参数限流了")
	                    .exception(e)
	                    .build();
	        } else if (e instanceof SystemBlockException) {
	            errorResponse = MyResponse.builder()
	                    .status(103).message("触发系统保护规则")
	                    .exception(e)
	                    .build();
	        } else if (e instanceof AuthorityException) {
	            errorResponse = MyResponse.builder()
	                    .status(104).message("授权规则不通过")
	                    .exception(e)
	                    .build();
	        }
	        response.setStatus(500);
	        response.setCharacterEncoding("utf-8");
	        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
	        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
	    }
	}