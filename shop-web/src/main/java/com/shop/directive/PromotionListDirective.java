package com.shop.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.core.model.Promotion;
import com.shop.service.PromotionListDirectiveService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("all")
public class PromotionListDirective extends BaseDirective{
	
	@Autowired
	private PromotionListDirectiveService promotionListDirectiveService;
	
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		//获取参数
		Integer productCategoryId = (Integer) getParameter("productCategoryId", params);
		BigDecimal count = (BigDecimal) getParameter("count", params);
		Boolean hasEnded=(Boolean) getParameter("hasEnded", params);
		//查询数据
		List<Promotion> promotions = promotionListDirectiveService.findPromotionList(productCategoryId,count.intValue(),hasEnded);
		//输出
		setVariable(env, body, "promotions", promotions);
	}

}
