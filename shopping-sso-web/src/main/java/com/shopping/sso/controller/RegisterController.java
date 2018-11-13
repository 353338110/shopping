package com.shopping.sso.controller;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public ShoppingResult checkData(@PathVariable String param,@PathVariable Integer type){
        ShoppingResult shoppingResult = registerService.checkData(param, type);
        return shoppingResult;
    }

    @RequestMapping(value="/user/register", method= RequestMethod.POST)
    @ResponseBody
    public ShoppingResult register(TbUser user) {
        ShoppingResult shoppingResult = registerService.register(user);
        return shoppingResult;
    }
}
