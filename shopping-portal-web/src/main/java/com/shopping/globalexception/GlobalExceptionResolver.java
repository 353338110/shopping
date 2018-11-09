package com.shopping.globalexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //打印控制台
        ex.printStackTrace();
        //写日志
        log.debug("测试输出的日志。。。。。。。");
        log.info("系统发生异常了。。。。。。。");
        log.error("系统发生异常", ex);
        //发邮件、发短信
        //使用jmail工具包。发短信使用第三方的Webservice。
        //显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
