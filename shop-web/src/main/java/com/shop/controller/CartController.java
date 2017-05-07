package com.shop.controller;

import com.shop.annotation.IsLogin;
import com.shop.core.base.BaseController;
import com.shop.core.base.BaseDto;
import com.shop.core.base.ResultInfo;
import com.shop.core.base.ResultListInfo;
import com.shop.core.model.CartItem;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Controller
@RequestMapping("cart")
public class CartController extends BaseController{

    @Autowired
    private CartService cartService;

    @RequestMapping("add")
    @IsLogin
    @ResponseBody
    public ResultInfo addToCart(Integer productId, Integer quantity,Integer goodsId, HttpServletRequest request) {
        // 获取当前登录用户的ID
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        cartService.add(productId, quantity, loginUserId,goodsId);
        return  success("添加成功", "添加成功");
    }

    @RequestMapping("list")
    @IsLogin
    public String selectForPage(HttpServletRequest request, BaseDto baseDto,Model model) {
        // 获取当前登录用户的ID
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        ResultListInfo <CartItem>result = cartService.selectForPage(baseDto, loginUserId);
        model.addAttribute("resultList",result);
        return "cart/list";
    }

    @RequestMapping("edit")
    @IsLogin
    @ResponseBody
    public ResultInfo edit(HttpServletRequest request, Integer quantity, Integer id) {
        // 获取当前登录用户的ID
        Integer loginUserId= LoginIdentityUtil.getFromLoginId(request);
        cartService.editCartItemQuantity(id,quantity,loginUserId);
        return  success("修改成功！","修改成功！");
    }

    @RequestMapping("delete/{id}")
    @ResponseBody
    @IsLogin
    public ResultInfo delete(@PathVariable Integer id,HttpServletRequest request){
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        cartService.delete(id,loginUserId);
        return success("删除成功！","删除成功！");
    }

    @RequestMapping("clear")
    @ResponseBody
    @IsLogin
    public ResultInfo clear(HttpServletRequest request){
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        cartService.clear(loginUserId);
        return success("清空成功！","清空成功！");

    }


    @RequestMapping("count")
    @ResponseBody
    @IsLogin
    public ResultInfo countQuantity(HttpServletRequest request){
        Integer loginUserId = LoginIdentityUtil.getFromLoginId(request);
        Integer amount=cartService.countQuantity(loginUserId);
        return success(amount);

    }

}
