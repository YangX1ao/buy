package com.shop.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.core.model.Article;
import com.shop.service.ArticleService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("all")
public class ArticleListDirective extends BaseDirective {
	
	@Autowired
	private ArticleService articleService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		//获取参数
		Integer categoryId = (Integer) getParameter("categoryId", params);
		BigDecimal count = (BigDecimal) getParameter("count", params);
		//查询数据
		List<Article> articles=articleService.findArticleList(categoryId,count.intValue());
		//输出
		setVariable(env, body, "articles", articles);
	}

}
