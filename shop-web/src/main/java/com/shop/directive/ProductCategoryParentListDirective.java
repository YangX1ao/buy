package com.shop.directive;

import com.shop.core.model.ProductCategory;
import com.shop.service.ProductCategoryService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class ProductCategoryParentListDirective extends BaseDirective{

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		//获取参数   
		Integer productCategoryId = (Integer) getParameter("productCategoryId", params);
		//查询数据
		List<ProductCategory> productCategories = productCategoryService.findParentList(productCategoryId);
		//输出
		setVariable(env, body, "productCategories", productCategories);
		
	}

}
