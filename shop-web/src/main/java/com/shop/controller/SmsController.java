package com.shop.controller;

import com.alibaba.fastjson.JSON;
import com.shop.core.base.BaseController;
import com.shop.core.base.ResultInfo;
import com.shop.core.base.exception.ParamException;
import com.shop.core.constant.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("sms")
public class SmsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.appkey}")
    private String appKey;
    @Value("${sms.appsecret}")
    private String appSecret;
    @Value("${sms.type}")
    private String smsType;
    @Value("${sms.sms_free_signname}")
    private String smsFreeSignName;
    @Value("${sms.sms_template_code}")
    private String smsTemplateCode;


    @RequestMapping("send")
    @ResponseBody
    public ResultInfo sendVerifyCode(String phone, HttpServletRequest request) throws ApiException {
        if (StringUtils.isBlank(phone)) {
            throw new ParamException("请输入手机号码");
        }

        // 先从session判断该手机号是否已发送过验证码
        String verfiedCode = (String)request.getSession().getAttribute(Constant.VERIFYCODE_SESSION_KEY);
        if (StringUtils.isNotBlank(verfiedCode)) {
            return success("发送成功");
        }

        TaobaoClient client = new DefaultTaobaoClient(smsUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(smsType);
        req.setSmsFreeSignName(smsFreeSignName);
        int verifyCode = (int) ((int)((Math.random() * 9 + 1) * 100000));
        Map<String, String> map = new HashMap<>();
        map.put("verifyCode", verifyCode + "");

        req.setSmsParam(JSON.toJSONString(map));
        req.setRecNum(phone);
        req.setSmsTemplateCode(smsTemplateCode);
        try {
            AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
            logger.info(response.getBody());
            // 发送成功后将验证码存入session
            request.getSession().setAttribute(Constant.VERIFYCODE_SESSION_KEY, verifyCode + "");
            request.getSession().setMaxInactiveInterval(300000); // 验证码5分钟过期 可以存放redis
        } catch (Exception e) {
            logger.error("{}", e);
            return failure("发送失败，请联系客服", "发送失败，请联系客服");
        }
        return success("发送成功");
    }

}
