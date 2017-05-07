package com.shop.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.core.model.ProductCategory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("all")
public class ProductCategoryChildrenListDirective extends BaseDirective{

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		//获取参数   
		Integer parentId = (Integer) getParameter("productCategoryId", params);
		BigDecimal count= (BigDecimal) getParameter("count", params);
		//查询数据
		List<ProductCategory> productCategories = productCategoryService.findChildrenList(parentId, count.intValue());
		//输出
		setVariable(env, body, "productChildrenCategories", productCategories);
		
	}

}
