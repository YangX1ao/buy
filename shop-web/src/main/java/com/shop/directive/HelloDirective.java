package com.shop.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class HelloDirective implements TemplateDirectiveModel {
	
	
	private Logger logger=LoggerFactory.getLogger(HelloDirective.class);
	
	private static final BeansWrapper DEFAULT_BEANS_WRAPPER=new 
			BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		logger.info("传入的参数是:{}",params);
		
		Map<String, Object> content=new HashMap<>();
		content.put("content", "hello world");
		content.putAll(params);
		TemplateModel templateModel=DEFAULT_BEANS_WRAPPER.wrap(content);
		env.setVariable("helloworld", templateModel);
		if(body!=null){
			body.render(env.getOut());
		}else{
			env.getOut().write(JSON.toJSONString(content));
		}
	}

}
