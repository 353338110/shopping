package com.shopping.portal.controller;

import com.shopping.pojo.TbContent;
import com.shopping.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页展示
 */
@Controller
public class IndexController {

    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;


    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}
