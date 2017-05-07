package com.shop.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by yangxiao on 2017/5/1.
 */
public class JsonTest {

    @Test
    public void test(){

        String jsonStr="[{\"id\":3,\"value\":\"银色\"},{\"id\":7,\"value\":\"16GB\"}]";
        JSONArray jsonArray= JSON.parseArray(jsonStr);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Integer id=jsonObject.getInteger("id");
            String value=jsonObject.getString("value");
            System.out.println("id:"+id+"--"+"value:"+value);
        }
    }

}
