package com.shopping.listener;

import com.shopping.common.utils.StringUtils;
import com.shopping.pojo.Item;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class HtmlGenListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //创建一个模板，参考jsp
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("控制台:"+text);
            log.info("log:"+text);
            int type = Integer.parseInt(text.substring(0,1));
            List<Long> ids = StringUtils.getIDsListByStr(text.substring(1));
            //等待事务提交
            Thread.sleep(1000);
            switch (type){
                case StringUtils.ADD:
                    addHtml(ids.get(0));
                    break;
                case StringUtils.UPDATE:
                    for (Long itemId:ids) {
                        deleteFile(HTML_GEN_PATH + itemId + ".html");
                        addHtml(itemId);
                    }
                    break;
                case StringUtils.DELETE:
                    for (Long itemId:ids) {
                        deleteFile(HTML_GEN_PATH + itemId + ".html");
                    }
                    break;

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    private void deleteFile(String path) throws  Exception{
        FileUtils.deleteDirectory(new File(path));
    }

    private void addHtml(Long itemId) throws  Exception{
        //根据商品id查询商品信息，商品基本信息和商品描述。
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品描述
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //创建一个数据集，把商品数据封装
        Map data = new HashMap<>();
        data.put("item", item);
        data.put("itemDesc", itemDesc);
        //加载模板对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");
        //创建一个输出流，指定输出的目录及文件名。
        Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
        //生成静态页面。
        template.process(data, out);
        //关闭流
        out.close();
    }
}
