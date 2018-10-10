package com.kuyun.controller;

import com.kuyun.util.HystrixUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xuwuqiang on 2018/10/10.
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String getConfigurations(HttpServletRequest request) {
        String s =  HystrixUtil.execute("test");
        System.out.println(s);
        return s;
    }
}
