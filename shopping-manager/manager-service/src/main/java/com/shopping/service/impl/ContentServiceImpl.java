package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.common.jedis.JedisClient;
import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;

import com.shopping.mapper.TbContentMapper;
import com.shopping.pojo.TbContent;
import com.shopping.pojo.TbContentExample;
import com.shopping.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {


    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;
    /**
     * 添加内容
     * @param tbContent
     * @return
     */
    @Override
    public ShoppingResult addContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());

        //由于TbContent主键是自增，这边无需传递
        contentMapper.insert(tbContent);

        //缓存同步，删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST,tbContent.getCategoryId().toString());

        return ShoppingResult.ok();
    }

    /**
     * 根据内容分类id查询内容列表  返回给前台用户看的
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {

        //查询缓存
        try {
            //如果缓存中有直接响应结果
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果没有查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);

        //把结果添加到缓存
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(tbContents));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbContents;
    }

    @Override
    public EasyUIDataGridResult getContentListByCidForBack(long cid,int page, int rows) {
        String orderBy = "updated desc";//"desc ASC"
        PageHelper.startPage(page,rows,orderBy);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);
        //PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(tbContents);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public ShoppingResult editContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        //缓存同步，删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST,content.getCategoryId().toString());
        return ShoppingResult.ok();
    }

    @Override
    public ShoppingResult deleteContent(String ids) {
        List<Long> longs = com.shopping.common.utils.StringUtils.getIDsListByStr(ids);
        int result = contentMapper.deleteByMultiPrimaryKey(longs);;
        if (result>0){
            //缓存同步，删除缓存中对应的数据
            for (Long id:longs) {
                jedisClient.hdel(CONTENT_LIST,id.toString());
            }
            return ShoppingResult.ok();

        }
        return new ShoppingResult(StatusEnum.MYSQL_ERROR.getCode(),StatusEnum.MYSQL_ERROR.getDesc(),null);
    }
}
