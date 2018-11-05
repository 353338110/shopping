package com.shopping.controller;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbContent;
import com.shopping.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;


    @RequestMapping(value = "content/save",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult addContent(TbContent content){
        ShoppingResult shoppingResult = contentService.addContent(content);
        return shoppingResult;

    }

    @RequestMapping(value = "content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentListByCid(Long categoryId , int page , int rows){
        EasyUIDataGridResult easyUIDataGridResult = contentService.getContentListByCidForBack(categoryId, page, rows);
        return easyUIDataGridResult;

    }

    @RequestMapping(value = "rest/content/edit")
    @ResponseBody
    public ShoppingResult editContent(TbContent content){
        contentService.editContent(content);
        return ShoppingResult.ok();

    }

    @RequestMapping(value = "content/delete")
    @ResponseBody
    public ShoppingResult deleteContent(String ids){
       contentService.deleteContent(ids);
        return ShoppingResult.ok();

    }
}
