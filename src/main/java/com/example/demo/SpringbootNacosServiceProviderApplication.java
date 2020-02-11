package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableHystrixDashboard  //Hystrix 控制台
@EnableFeignClients      //Feign远程调用
@EnableDiscoveryClient   //nacos 服务注册
@SpringBootApplication
public class SpringbootNacosServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootNacosServiceProviderApplication.class, args);
	}
}
