package com.example.demo.util;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;

/**
 * @author Eric Zhao
 */
public final class ExceptionUtil {
	public static  String handleException(String str,BlockException e) {
		MyResponse errorResponse = null;
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
        ReturnT<MyResponse> resp=  new ReturnT<MyResponse>(500,"流量控制",errorResponse);
		return resp.toString();
    }
	
	public static SentinelClientHttpResponse handleException(HttpRequest request,
            byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        System.err.println("Oops: " + ex.getClass().getCanonicalName());
        MyResponse errorResponse =MyResponse.builder().status(100).message("接口限流了").exception(ex).build();
        ReturnT<MyResponse> resp=  new ReturnT<MyResponse>(500,"流量控制",errorResponse);
        return new SentinelClientHttpResponse(resp.toString());
    }
    
    public static SentinelClientHttpResponse fallback(HttpRequest request,
            byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        System.err.println("fallback: " + ex.getClass().getCanonicalName());
        MyResponse errorResponse =MyResponse.builder().status(100).message("接口限流了").exception(ex).build();
        ReturnT<MyResponse> resp=  new ReturnT<MyResponse>(500,"流量控制",errorResponse);
        return new SentinelClientHttpResponse(resp.toString());
    }
}