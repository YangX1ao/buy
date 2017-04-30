package com.shop.directive;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.shop.core.model.AdPosition;
import com.shop.service.AdPositionService;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
@SuppressWarnings("rawtypes")
public class AdPositionDirective extends BaseDirective {
	
	@Autowired
	private AdPositionService adPositionService;
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, 
			TemplateDirectiveBody body)
	throws TemplateException, IOException {
		
		BigDecimal id = (BigDecimal) getParameter("id", params);
		AdPosition adPosition = adPositionService.findById(id.intValue());
		String template = adPosition.getTemplate();
		
		// 输出
		if (body == null) {
			// 将模板内容和数据进行绑定输出
			Template temp = new Template("adTemplate", 
					new StringReader(template), freemarkerConfig.getConfiguration());
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("adPosition", adPosition);
			temp.process(dataModel, env.getOut());
		} else {
			setVariable(env, body, "adPosition", adPosition);
		}
	}
}
