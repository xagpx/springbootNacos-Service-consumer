package com.example.demo.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
@Data
@Builder
public class MyResponse {
	 private Integer status;
	 private String message;
	 private BlockException exception;   
}
