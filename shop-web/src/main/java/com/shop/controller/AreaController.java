package com.shop.controller;

import com.shop.annotation.IsLogin;
import com.shop.core.base.BaseController;
import com.shop.core.base.BaseDto;
import com.shop.core.base.ResultInfo;
import com.shop.core.base.ResultListInfo;
import com.shop.core.model.CartItem;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.service.AreaService;
import com.shop.service.CartService;
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
@RequestMapping("area")
public class AreaController extends BaseController{

    @Autowired
    private AreaService areaService;

    @RequestMapping("list")
    @IsLogin
    @ResponseBody
    public List<Map<String,Object>> addToCart(Integer parentId) {
        List<Map<String,Object>> result=areaService.findAreas(parentId);
        return result;
    }



}
