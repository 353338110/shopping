package com.shopping.message;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.utils.StringUtils;
import com.shopping.mapper.SearchItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

@Slf4j
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void onMessage(Message message) {
        try {
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("activeMQ 消息:"+text);
            //Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);

            int type = Integer.parseInt(text.substring(0,1));
            System.out.println("activeMQ type:"+type);
            List<Long> iDsListByStr = StringUtils.getIDsListByStr(text.substring(1));
            for (Long id:iDsListByStr) {
                System.out.println("activeMQ 循环id:"+id);

                switch (type){
                    case StringUtils.DELETE:
                        solrClient.deleteById(id+"");
                        break;
                    case StringUtils.ADD:
                    case StringUtils.UPDATE:
                        //根据商品id查询商品信息
                        SearchItem searchItem = searchItemMapper.getItemById(id);
                        //创建一个文档对象
                        SolrInputDocument document = new SolrInputDocument();
                        //向文档对象中添加域
                        document.addField("id", searchItem.getId());
                        document.addField("item_title", searchItem.getTitle());
                        document.addField("item_sell_point", searchItem.getSell_point());
                        document.addField("item_price", searchItem.getPrice());
                        document.addField("item_image", searchItem.getImage());
                        document.addField("item_category_name", searchItem.getCategory_name());
                        //把文档写入索引库
                        solrClient.add(document);
                        break;
                }
            }
            //提交
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
