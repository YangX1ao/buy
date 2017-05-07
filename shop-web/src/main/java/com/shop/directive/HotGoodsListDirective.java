package com.shop.directive;

import com.shop.core.model.Goods;
import com.shop.service.GoodsService;
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

/**
 * Created by yangxiao on 2017/5/1.
 */
@Component
public class HotGoodsListDirective extends BaseDirective{

    @Autowired
    private GoodsService goodsService;



    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        //获取参数
        BigDecimal tagId= (BigDecimal) getParameter("tagId",params);
        BigDecimal count= (BigDecimal) getParameter("count",params);
        //查询数据
        List<Goods> hotGoodsList=goodsService.findHotGoodsList(tagId.intValue(),count.intValue());
        //输出
        setVariable(env,body,"hotGoodsList",hotGoodsList);
    }
}
