package com.shop.controller;

import com.shop.annotation.IsLogin;
import com.shop.core.base.BaseController;
import com.shop.core.base.BaseDto;
import com.shop.core.base.ResultInfo;
import com.shop.core.base.ResultListInfo;
import com.shop.core.model.CartItem;
import com.shop.core.model.Receiver;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

    @Autowired
    private CartService cartService;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private OrderService orderService;


    @RequestMapping("checkout")
    public String findCartItemByIds(HttpServletRequest request,String cartItemIds,Model model) {
        // 获取当前登录用户的ID
        Integer loginUserId= LoginIdentityUtil.getFromLoginId(request);
        //获取订单信息
        String str=cartItemIds;
        List<CartItem> cartItems=cartService.findCartItemByIds(cartItemIds);
        //获取用户收货地址
        List<Receiver> receivers=receiverService.findAddressById(loginUserId);
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("receivers",receivers);
        model.addAttribute("cartItemIds",cartItemIds);
        return  "order/checkout";
    }

    @RequestMapping("create")
    @ResponseBody
    @IsLogin
    public ResultInfo create(HttpServletRequest request,Model model,
                             String cartItemIds, Integer receiverId, String memo) throws  Exception{
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        String orderNo=orderService.create(loginUserId,cartItemIds,receiverId,memo);
        return success(orderNo);
    }

}
