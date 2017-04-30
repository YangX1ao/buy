/*package com.shop.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shop.core.base.ResultInfo;

@Service
@SuppressWarnings("unchecked")
public class IndexService {
	
	@Resource
	private RestTemplate restTemplate;
	

	
	public List<String> findHotSearchKeys() {
		ResultInfo resultInfo=restTemplate.getForObject("http://localhost:8083/hot_search/get", ResultInfo.class);
		
		return (List<String>) resultInfo.getResult();
	}

}*/
