package com.tdkj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by len on 2019-04-26.
 */
@Controller
public class IndexController {
    @RequestMapping("index")
    public String list(){
        
        return "/index";
    }
}
