package com.shopping.service.impl;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.mapper.TbItemDescMapper;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public ShoppingResult getItemDesc(long id) {
        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(id);
        return ShoppingResult.ok(tbItemDesc);
    }
}
