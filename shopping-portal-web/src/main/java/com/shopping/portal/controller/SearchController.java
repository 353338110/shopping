package com.shopping.portal.controller;

import com.shopping.common.pojo.SearchResult;
import com.shopping.pojo.TbContent;
import com.shopping.service.ContentService;
import com.shopping.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @Autowired
    private SearchService searchService;


    @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue = "1")Integer page,Model model) throws  Exception{
        /*System.out.println("keyword1："+keyword);
        keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        System.out.println("keyword2："+keyword);*/
        searchService.search(keyword,page,SEARCH_RESULT_ROWS);
        //查询商品列表
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        //异常测试
        //int a = 1/0;
        //返回逻辑视图
        return "search";
    }
}
