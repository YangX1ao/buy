/*package com.shop.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shop.core.base.ResultInfo;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("rawtypes")
public class HotSearchKeywordsDirective extends BaseDirective {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${cache.domain}")
	private String cacheDomain;
	
	@Value("${url.hot.search}")
	private String urlHotSearch;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, 
			TemplateDirectiveBody body)
	throws TemplateException, IOException {
		
		ResultInfo result = restTemplate.getForObject(cacheDomain + urlHotSearch, 
				ResultInfo.class);
		List<String> keywords = (List<String>) result.getResult();
		
		setVariable(env, body, "hotSearchKeywords", keywords);
		
	}
}
*/