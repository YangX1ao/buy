package com.shop.controller;

import com.shop.annotation.IsLogin;
import com.shop.core.base.BaseController;
import com.shop.core.base.ResultInfo;
import com.shop.core.model.Receiver;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.service.AreaService;
import com.shop.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("receiver")
public class ReceiverController extends BaseController{

    @Autowired
    private ReceiverService receiverService;

    @RequestMapping("add")
    @IsLogin
    @ResponseBody
    public ResultInfo addAddress(HttpServletRequest request, Receiver receiver){
        Integer loginUserId= LoginIdentityUtil.getFromLoginId(request);
        receiver=receiverService.addAddress(receiver,loginUserId);
        return  success(receiver);
    }



}
