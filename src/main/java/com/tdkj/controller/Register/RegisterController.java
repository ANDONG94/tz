package com.tdkj.controller.Register;

import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.UserManager.FpUserService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.MD5Util;
import com.tdkj.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2019-04-30.
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private FpUserService fpUserService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 点击注册保存的方法
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, FpUser fpUser){
        String flag = "";
        String pass = MD5Util.getMD5(fpUser.getPassword());
        fpUser.setPassword(pass);
        fpUser.setId(UUIDGenerator.generate().toString());
        fpUser.setCreateddate(new Date());
        if(fpUser != null){
            fpUserService.insertSelective(fpUser);
            flag = "success";
        }else{
            flag = "fail";
        }
        return flag;
    }


    /**
     * 点击重置密码
     */
    @RequestMapping("update")
    @ResponseBody
    public String update(HttpServletRequest request, FpUser fpUser){
        String flag = "";
        String pass = MD5Util.getMD5("123456");
        FpUser fpUser1 =fpUserService.selectIdcard(fpUser.getIdcard(),fpUser.getMobilephome());

        if(fpUser1 != null){
            fpUser1.setPassword(pass);
            fpUserService.updateByPrimaryKeySelective(fpUser1);
            flag = fpUser1.getUsername();
        }else{
            flag = "fail";
        }
        return flag;
    }

    /**
     * 查询行政区划  下拉
     * @param request
     * @return
     */
    @RequestMapping("queryPoorXzqh")
    @ResponseBody
    public List<PoorXzqh> queryPoorXzqh(HttpServletRequest request){
        List<PoorXzqh> list = ztreeService.queryPoorXzqh();
        return list;
    }


}
