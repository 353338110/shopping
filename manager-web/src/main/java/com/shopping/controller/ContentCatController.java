package com.shopping.controller;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContentCatController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据父节点查询内容的节点列表（对应数据库名 tb_content_category）
     * @param parentId 父节点id  默认0
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(name = "id" ,defaultValue = "0")Long parentId){
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(parentId);
        return contentCatList;
    }

    /**
     * 创建内容的新节点
     * @param parentId 该内容节点对应的父节点id
     * @param name      该内容节点的名字
     * @return
     */
    @RequestMapping(value = "content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult addContentCategory(
            /*@RequestParam(name = "parentId")*/ Long parentId,/*@RequestParam(name = "name")*/ String name){
        ShoppingResult shoppingResult = contentCategoryService.addContentCategory(parentId, name);
        return shoppingResult;

    }

    /**
     * 跟新内容节点的名称
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult updateNameContentCategory(
            @RequestParam(name = "id") Long id,@RequestParam(name = "name") String name){
        ShoppingResult shoppingResult = contentCategoryService.updateCategoryName(id, name);
        return shoppingResult;

    }

    /**
     * 删除该内容节(无法执行)
     * @param id
     * @return
     */
    @RequestMapping(value = "content/category/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult deleteContentCategory(@PathVariable Long id){
        ShoppingResult shoppingResult = contentCategoryService.deleteCategory(id);
        return shoppingResult;

    }

    /**
     * 删除该内容节
     * @param id
     * @return
     */
    @RequestMapping(value = "content/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult deleteContentCategory2( Long id){
        ShoppingResult shoppingResult = contentCategoryService.deleteCategory(id);
        return shoppingResult;

    }
}
