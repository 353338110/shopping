package com.shopping.sql;

import com.shopping.mapper.TbContentCategoryMapper;
import com.shopping.pojo.TbContentCategory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class SQLTest {

    @Test
    public void sqlTest() throws Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //从容器中获得Mapper代理对象
        //TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        /*itemMapper.deleteByPrimaryKey(new Long("154112600888295"));*/
        /*String[] strIds = "1352516,1352521,".split(",");
        List<Long> ids = new ArrayList<>();
        for (String str:strIds) {
            ids.add(new Long(str));
        }
        int result = itemMapper.instockMultiByPrimaryKey(ids);
        System.out.print(result);*/
        /*List<Long> longs = new ArrayList<>();
        longs.add(154112600888295);
        longs.add(154108623456487);
        String a = "154112600888295,154108623456487";
        ArrayList[] arrayLists = new ArrayList[];
        itemMapper*/

        /*TbContentCategoryMapper contentCategoryMapper = applicationContext.getBean(TbContentCategoryMapper.class);
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(new Long(30));
        contentCategory.setName("测试一");
        //1正常 2删除
        contentCategory.setStatus(1);
        //新增加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        long returnId = contentCategoryMapper.insertReturnId(contentCategory);
        System.out.println("测试一"+returnId);//返回1
        contentCategory.setName("测试二");
        int insert = contentCategoryMapper.insert(contentCategory);//返回1
        System.out.println("测试二"+insert);*/
    }
}
