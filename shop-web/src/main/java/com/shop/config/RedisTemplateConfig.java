package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RedisTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate=new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory=new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);// 连接的超时时间
		requestFactory.setReadTimeout(500); // 读取超时时间
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}
}
