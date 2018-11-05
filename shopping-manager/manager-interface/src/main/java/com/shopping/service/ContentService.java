package com.shopping.service;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbContent;

import java.util.List;

public interface ContentService {

    ShoppingResult addContent(TbContent tbContent);

    List<TbContent> getContentListByCid(long cid);

    EasyUIDataGridResult getContentListByCidForBack(long cid, int page, int rows);

    ShoppingResult editContent(TbContent content);

    ShoppingResult deleteContent(String ids);

}
