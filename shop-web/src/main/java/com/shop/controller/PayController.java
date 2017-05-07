package com.shop.controller;

import com.shop.annotation.IsLogin;
import com.shop.core.base.BaseController;
import com.shop.core.constant.PayConstant;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.core.vo.PayRequestVo;
import com.shop.service.AreaService;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("pay")
public class PayController extends BaseController{

    @Autowired
    private OrderService orderService;

    @RequestMapping("page/{orderNo}")
    @IsLogin
    public String list(@PathVariable String orderNo, HttpServletRequest request, Model model){
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        return "pay/pay";
    }


}
