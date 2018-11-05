package com.shopping.service.impl;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.mapper.TbItemCatMapper;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemCat;
import com.shopping.pojo.TbItemCatExample;
import com.shopping.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据parentId查询子节点列表

        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria  criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat :list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setState(tbItemCat.getIsParent() ? "close" :"open");
            node.setText(tbItemCat.getName());
            resultList.add(node);
        }

        return resultList;
    }
}
