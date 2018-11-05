package com.shopping.service.impl;


import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.IDUtils;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;
import com.shopping.common.utils.StringUtils;
import com.shopping.mapper.TbItemDescMapper;
import com.shopping.mapper.TbItemMapper;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.pojo.TbItemExample;
import com.shopping.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public TbItem getItemById(long itemId) {
        log.info("------------ItemServiceImpl itemId = "+itemId );
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = new ArrayList<TbItem>();
        try {
            System.out.println("begin------------");
            list = itemMapper.selectByExample(example);
            System.out.println("end------------");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally-----------");
        }

        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        String orderBy = "updated desc";//"desc ASC"
        PageHelper.startPage(page,rows,orderBy);
        TbItemExample example = new TbItemExample();
        //example.setOrderByClause("created desc");//"desc ASC"
        List<TbItem> list = itemMapper.selectByExample(example);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public ShoppingResult deletedItem(String ids) {
        List<Long> longs = StringUtils.getIDsListByStr(ids);
        int result = itemMapper.deleteByMultiPrimaryKey(longs);;
        if (result>0){
            return ShoppingResult.ok();
        }
        return new ShoppingResult(StatusEnum.MYSQL_ERROR.getCode(),StatusEnum.MYSQL_ERROR.getDesc(),null);
    }

    @Override
    public ShoppingResult instock(String ids) {
        List<Long> longs = StringUtils.getIDsListByStr(ids);
        int result = itemMapper.instockMultiByPrimaryKey(longs);
        if (result>0){
            return ShoppingResult.ok();
        }
        return new ShoppingResult(StatusEnum.MYSQL_ERROR.getCode(),StatusEnum.MYSQL_ERROR.getDesc(),null);
    }

    @Override
    public ShoppingResult reshelf(String ids) {
        List<Long> longs = StringUtils.getIDsListByStr(ids);
        int result = itemMapper.reshelfMultiByPrimaryKey(longs);
        if (result>0){
            return ShoppingResult.ok();
        }
        return new ShoppingResult(StatusEnum.MYSQL_ERROR.getCode(),StatusEnum.MYSQL_ERROR.getDesc(),null);
    }

    @Override
    public ShoppingResult updateItem(TbItem tbItem,String desc) {
        tbItem.setUpdated(new Date());
        int result1 = itemMapper.updateByPrimaryKeySelective(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        int result2 = itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        if ((result1>0 && result2>0)){
            return ShoppingResult.ok();
        }
        return new ShoppingResult(StatusEnum.MYSQL_ERROR.getCode(),StatusEnum.MYSQL_ERROR.getDesc(),null);
    }


    @Override
    public ShoppingResult addItem(TbItem tbItem, String desc) {
        Long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        //1-正常 2-下架 3-删除(未知)
        tbItem.setStatus((byte)1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());

        itemMapper.insert(tbItem);

        //商品描述表
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return ShoppingResult.ok();
    }
}
