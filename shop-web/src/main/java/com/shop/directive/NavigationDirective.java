package com.shop.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.shop.core.model.Navigation;
import com.shop.service.NavigationService;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@SuppressWarnings("all")
@Component
public class NavigationDirective extends BaseDirective{
	
	@Resource
	private NavigationService navigationService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		//获取参数
		BigDecimal position = (BigDecimal)getParameter("position", params);//获取参数
		if(position==null){
			position=new BigDecimal(0);
		}
		//查询数据
		List<Navigation> navigations = navigationService.findByPosition(position.intValue());
		//输出
		setVariable(env,body,"navigations", navigations);
		
	}

}
