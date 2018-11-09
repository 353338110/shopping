package com.shopping.service.impl;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;
import com.shopping.mapper.SearchItemMapper;
import com.shopping.service.SearchItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrClient client;
    @Override
    public ShoppingResult importAllItems() {

        //查询所有商品item包含节点名称
        List<SearchItem> lists = searchItemMapper.getItemList();
        try {
            for (SearchItem searchItem:lists) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_category_name",searchItem.getCategory_name());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_sell_point",searchItem.getSell_point());
                document.addField("item_price",searchItem.getPrice());
                //把文档对象写入索引库
                client.add(document);
            }
            //提交
            client.commit();
            return ShoppingResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ShoppingResult.build(StatusEnum.SOLR_ERROR.getCode(),
                    StatusEnum.SOLR_ERROR.getDesc());
        }
    }
}
