package com.shopping.controller;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    /**
     * 导入商品数据到solr索引库上
     * @return
     */
    @RequestMapping(value = "index/item/import")
    @ResponseBody
    public ShoppingResult deleteContentCategory(){
        ShoppingResult shoppingResult = searchItemService.importAllItems();
        return shoppingResult;

    }
}
