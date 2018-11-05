package com.shopping.service.impl;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;
import com.shopping.mapper.TbContentCategoryMapper;
import com.shopping.pojo.TbContentCategory;
import com.shopping.pojo.TbContentCategoryExample;
import com.shopping.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);

        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for(TbContentCategory contentCategory:categoryList){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent() ? "closed" :"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public ShoppingResult addContentCategory(long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1正常 2删除
        contentCategory.setStatus(1);
        //新增加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.insertReturnId(contentCategory);

        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            parent.setUpdated(new Date());
            contentCategoryMapper.updateByPrimaryKey(parent);
        }

        return ShoppingResult.ok(contentCategory);
    }

    @Override
    public ShoppingResult updateCategoryName(long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return ShoppingResult.ok();
    }

    @Override
    public ShoppingResult deleteCategory(long id) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);

        //如果节点是父节点，则不让删除
        if (contentCategory.getIsParent()){
            return ShoppingResult.build(StatusEnum.DELETE_NODE_ERROR.getCode(),
                    StatusEnum.DELETE_NODE_ERROR.getDesc());
        }

        //删除节点
        contentCategoryMapper.deleteByPrimaryKey(id);

        //如果被删除的节点的父节点本身只有一个节点，则要把被删除节点的父节点设置为子节点
        //首先要查询该节点父节点下面有几个节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(contentCategory.getParentId());
        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);

        //节点被删除后，如果该节点下面没有节点，则要把改父节点变成子节点
        if (categoryList.size()==0){
            TbContentCategory parent = new TbContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }

        return ShoppingResult.ok();
    }
}
