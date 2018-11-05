package com.shopping.controller;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemDescController {

    @Autowired
    ItemDescService itemDescService;

    @RequestMapping(value = "/rest/item/query/item/desc/{id}")
    @ResponseBody
    public ShoppingResult addItem(@PathVariable Long id){
        ShoppingResult shoppingResult = itemDescService.getItemDesc(id);
        return shoppingResult;
    }
}
