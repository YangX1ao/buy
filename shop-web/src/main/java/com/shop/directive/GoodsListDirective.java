package com.shop.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.core.model.Goods;
import com.shop.service.GoodsService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("all")
public class GoodsListDirective extends BaseDirective{
	
	@Autowired
	private GoodsService goodsService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		//获取参数
		Integer productCategoryId=1;
		 try {
			productCategoryId= (Integer) getParameter("productCategoryId", params);
		} catch (Exception e) {
			BigDecimal productCategoryIdBd=(BigDecimal) getParameter("productCategoryId", params);
			if(productCategoryIdBd !=null){
				productCategoryId=productCategoryIdBd.intValue();
			}
		}
		 if(productCategoryId==null){
			 productCategoryId=1;
		 }
		BigDecimal tagId=(BigDecimal) getParameter("tagId", params);
		BigDecimal count=(BigDecimal) getParameter("count", params);
		//查询数据
		List<Goods> goodsList=goodsService.findGoodsList(productCategoryId,tagId.intValue(),count.intValue());
		//输出
		setVariable(env, body, "goodsList", goodsList);
		
		
		
	}

}
