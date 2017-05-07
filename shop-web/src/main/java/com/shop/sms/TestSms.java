package com.shop.sms;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.messaging.support.HeaderMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TW on 2017/5/2.
 */
public class TestSms {

    public static void main(String[] args) {
        String url = "http://gw.api.taobao.com/router/rest";
        String appkey = "23782396";
        String secret = "b211e462e4e81168244c3edf13e3fd65";
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);

        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("尚Hai购");
        Map<String, String> map = new HashMap<>();
        map.put("verifyCode", "123456");
//        req.setSmsParam("{\"verifyCode\":\"12344\"}");
        req.setSmsParam(JSON.toJSONString(map));
        //req.setRecNum("18860470118");
        req.setSmsTemplateCode("SMS_65095001");

        try {
            AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
            System.out.println(JSON.toJSONString(response));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
