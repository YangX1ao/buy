package com.shop.directive;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
@SuppressWarnings("all")
public abstract class BaseDirective implements TemplateDirectiveModel{
	
	private static Logger logger=LoggerFactory.getLogger(BaseDirective.class);
	
	/**
	 * 根据参数名获取参数
	 * @param paramName
	 * @param params
	 * @return
	 */
	protected Object getParameter(String paramName,Map params){
		BeansWrapper beansWrapper=new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
		TemplateModel positionModel=(TemplateModel) params.get(paramName);
		
		try {
			// 将TemplateModel转化成Object
			Object position = beansWrapper.unwrap(positionModel);
			return position;
		} catch (TemplateModelException e) {
			// TODO Auto-generated catch block
			logger.error("获取参数异常:{}",e);
		}
		return null;
	}
	
	/**
	 * 将数据进行输出
	 * @param env
	 * @param body
	 * @param name
	 * @param value
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected void setVariable(Environment env, TemplateDirectiveBody body, 
			String name, Object value) 
	throws TemplateException, IOException {
		// 构建一个TemplateModel
		BeansWrapper beansWrapper=new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
		//获取数据
		TemplateModel templateModel=beansWrapper.wrap(value);
		//设置输出的内容
		env.setVariable(name, templateModel);
		//输出
		if(body!=null){
			body.render(env.getOut());
		}
		
		
		
	}
	
	
}
