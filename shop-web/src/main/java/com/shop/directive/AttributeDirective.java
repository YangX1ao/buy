package com.shop.directive;

import com.shop.core.model.Attribute;
import com.shop.service.AttributeService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Component
public class AttributeDirective extends BaseDirective {

    @Autowired
    private AttributeService attributeService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        //获取参数
        //分类id
        Integer productCategoryId = (Integer) getParameter("productCategoryId", params);
        //查询方法
        List<Attribute> attributeList = attributeService.findAttributeList(productCategoryId);
        if(attributeList !=null &&attributeList.size()>0){
            for(Attribute attribute:attributeList){
                String options = attribute.getOptions();
                if(StringUtils.isBlank(options)){
                    continue;
                }
                //对获取到的尺寸进行分割
                //["3英寸以下","3-4英寸","4-5英寸","5-6英寸","6-7英寸","7-8英寸","8英寸以上"]
                String optionSubs= options.substring(1, options.lastIndexOf("]"));
                String[] optionArr= optionSubs.split(",");
                //然后将分割好的字符串放到list中
                List<String> subStrs=new ArrayList<>();
                for(String str:optionArr){
                    subStrs.add(str.substring(1,str.length()-1));
                }
                attribute.setOptionsList(subStrs.toArray(new String[]{}));
            }
        }
        //输出
        setVariable(env,body,"attributes",attributeList);
    }
}
