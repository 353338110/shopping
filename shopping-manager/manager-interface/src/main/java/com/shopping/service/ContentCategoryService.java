package com.shopping.service;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.ShoppingResult;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(long parentId);

    ShoppingResult addContentCategory(long parentId,String name);

    ShoppingResult updateCategoryName(long id,String name);

    ShoppingResult deleteCategory(long id);



}
