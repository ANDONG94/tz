package com.tdkj.controller.AccountManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hedd on 2019/7/25.
 */
@Controller
@RequestMapping("OnLinePerson")
public class OnLinePersonController {

    /**
     * 进入劳动力台j就业台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){

        return "ceshi";
    }
}
