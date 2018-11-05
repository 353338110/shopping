package com.shopping.service;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbItem;

public interface ItemService {

    ShoppingResult addItem(TbItem tbItem,String desc);

    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page , int rows);

    ShoppingResult deletedItem(String ids);

    ShoppingResult instock(String ids);

    ShoppingResult reshelf(String ids);

    ShoppingResult updateItem(TbItem tbItem, String desc);
}
