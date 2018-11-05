package com.shopping.controller;

import com.shopping.common.utils.FtpUtil;
import com.shopping.common.utils.IDUtils;
import com.shopping.common.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PictureController {
    String ftpBaseUrl = "http://"+FtpUtil.host+"/"+FtpUtil.filePath+"/";
    //produces指定返回的类型  "text/plain"，增加兼容性
    @RequestMapping(value ="/pic/mulUpload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFiles(MultipartFile[] uploadFiles){
        String newName = "";
        List<String> urls = new ArrayList<>();
        Map resultMap = new HashMap<>();
        try {
            //把图片上传到服务器
            FtpUtil ftpUtil = new FtpUtil();
            ftpUtil.connect();
            for (MultipartFile uploadFile :uploadFiles){
                String oldName = uploadFile.getOriginalFilename();
                String suffix = oldName.substring(oldName.lastIndexOf(".") + 1, oldName.length());
                //得到一个图片名称
                newName = IDUtils.genImageName()+"."+suffix;
                ftpUtil.upload(newName,uploadFile.getInputStream());
                urls.add(ftpBaseUrl+newName);
            }
            //封装到map中返回
            resultMap.put("error", 0);
            resultMap.put("urls", urls);
            //增加浏览器的兼容性，返回类型变成String
            return JsonUtils.objectToJson(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", 1);
            resultMap.put("message", "upload Fail");
            return JsonUtils.objectToJson(resultMap);
        }
    }


    @RequestMapping(value ="/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        String newName = "";
        Map resultMap = new HashMap<>();
        try {
            //把图片上传到服务器
            FtpUtil ftpUtil = new FtpUtil();
            ftpUtil.connect();
            String oldName = uploadFile.getOriginalFilename();
            String suffix = oldName.substring(oldName.lastIndexOf(".") + 1, oldName.length());
            //得到一个图片名称
            newName = IDUtils.genImageName()+"."+suffix;
            ftpUtil.upload(newName,uploadFile.getInputStream());
            //封装到map中返回
            resultMap.put("error", 0);
            resultMap.put("url", ftpBaseUrl+newName);
            //增加浏览器的兼容性，返回类型变成String
            return JsonUtils.objectToJson(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", 1);
            resultMap.put("message", "upload Fail");
            return JsonUtils.objectToJson(resultMap);
        }
    }
}
