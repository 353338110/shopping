package com.shopping.controller;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;



    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        //查询商品列表
        log.info("------------ItemController itemId = "+itemId );
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * 添加商品
     * @param tbItem item中image属性中如果有多个图片，保存方式为图片中间加","分开
     * @param desc  商品的详细描述，可以是包含标签等
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public ShoppingResult addItem(TbItem tbItem,String desc){
        ShoppingResult shoppingResult = itemService.addItem(tbItem, desc);
        return shoppingResult;
    }

    /**
     * 商品列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        //由于manager-web没有引入pageHelper，所有找不到page类
        //会报一个警告 com.alibaba.com.caucho.hessian.io.SerializerFactory.getDeserializer Hessian/Burlap: 'com.github.pagehelper.Page' is an unknown class in ParallelWebappClassLoader
        //java.lang.ClassNotFoundException: com.github.pagehelper.Page
        EasyUIDataGridResult result = itemService.getItemList(page,rows);
        return result;
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public ShoppingResult deleteItem(String ids){
        ShoppingResult shoppingResult = itemService.deletedItem(ids);
        return shoppingResult;
    }

    /**
     * 商品下架 status 改成 2
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public ShoppingResult instockItem(String ids){
        ShoppingResult shoppingResult = itemService.instock(ids);
        return shoppingResult;
    }

    /**
     * 商品上架 status 改成 1
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public ShoppingResult reshelfItem(String ids){
        ShoppingResult shoppingResult = itemService.reshelf(ids);
        return shoppingResult;
    }


    /**
     * 更新商品
     * @param tbItem
     * @return
     */
    @RequestMapping("rest/item/update")
    @ResponseBody
    public ShoppingResult updateItem(TbItem tbItem, String desc){
        ShoppingResult shoppingResult = itemService.updateItem(tbItem,desc);
        return shoppingResult;
    }
}
