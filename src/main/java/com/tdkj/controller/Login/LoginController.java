package com.tdkj.controller.Login;

import com.tdkj.model.FpUser;
import com.tdkj.model.IpLogin;
import com.tdkj.service.IpLoing.IpLoginService;
import com.tdkj.service.UserManager.FpUserService;
import com.tdkj.util.IdCardManageUtil;
import com.tdkj.util.IpUtil;
import com.tdkj.util.MD5Util;
import com.tdkj.util.UUIDGenerator;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.shiro.SecurityUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by len on 2019-04-30.
 */
@Controller
public class LoginController {

    @Autowired
    private FpUserService fpUserService;
    @Autowired
    private IpLoginService ipLoginService;
  /*  *//**
     * 进入到登录页面
     *//*
    @RequestMapping("login")
    public String login(){
        return "login/login";
    }*/

    /**
     * 退出
     */
    @RequestMapping("loginOut")
    public String logins(HttpServletRequest request, HttpServletResponse response){



        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login/login";
    }


    /**
     * 输入用户名校验该用户是否存在
     */
    @RequestMapping("loginUserName")
    @ResponseBody
    public String loginUserName(HttpServletRequest request, String username){
        String flag = "";
        FpUser fpUser  = new FpUser();
        fpUser = fpUserService.queryUserByUserName(username);

        if(fpUser != null){
            flag = "success";
        }else{
            flag = "用户名不存在！！！";
        }
        return flag;
    }

    /**
     * 点击登录的按钮执行的方法
     */
    @RequestMapping("loginValidate")
    @ResponseBody
    public String loginValidate(HttpServletRequest request, String username,boolean rememberMe, String password, Model model){
        String flag = "";
        FpUser fpUser  = new FpUser();
        String pass = MD5Util.getMD5(password);
        fpUser = fpUserService.LoginByUserNameAndPwd(username,pass);
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        //2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,pass,rememberMe);

        //3、执行登录方法
        try {
            //交给Realm处理--->执行它的认证方法
            subject.login(token);
            request.getSession().setAttribute("fpUser",fpUser);
            String ip= IpUtil.getIpAddr(request);
            IpLogin ipLogin = new IpLogin();
            ipLogin.setUuid(UUIDGenerator.generate().toString());
            ipLogin.setIntodate(new Date());
            ipLogin.setUsercardid(fpUser.getIdcard());
            ipLogin.setUserid(fpUser.getId());
            ipLogin.setUsername(fpUser.getUsername());
            ipLogin.setLog006(fpUser.getAab301());
            ipLogin.setLog007(ip);
            ipLogin.setLog008("登陆");
            ipLoginService.insert(ipLogin);
            //登录成功
            return "1";
        }catch (UnknownAccountException e){
            //登录失败:用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "2";
        }catch (IncorrectCredentialsException e){
            //登录失败：密码错误
            model.addAttribute("msg","密码错误");
            return "3";
        }

    }


    /**
     * 查询登录用户新
     * @param request
     * @return
     */
    @RequestMapping("queryLoginUser")
    @ResponseBody
    public FpUser queryLoginUser(HttpServletRequest request){
        FpUser user = null;
        user = (FpUser)request.getSession().getAttribute("fpUser");
        return user;
    }


    /**
     * 修改密码时
     * 根据输入的原始密码和数据库中原始密码进行比对
     * @param request
     * @return
     */
    @RequestMapping("comparePass")
    @ResponseBody
    public String comparePass(HttpServletRequest request,String pass){
        FpUser user = null;
        String flag = "";
        user = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(pass) && pass != null){
            String passbymd5 = MD5Util.getMD5(pass);
            if(!user.getPassword().equals(passbymd5)){
                //说明原始密码输入错误，则提示
                flag = "no";
            }else{
                flag = "yes";
            }
        }
        return flag;
    }


    /**
     * 修改密码的保存方法
     * @param request
     * @return
     */
    @RequestMapping("savePassword")
    @ResponseBody
    public String savePassword(HttpServletRequest request,FpUser fpUser){
        String flag = "";
        if(fpUser != null){
            String passbymd5 = MD5Util.getMD5(fpUser.getPassword());
            fpUser.setPassword(passbymd5);
            fpUserService.updateByPrimaryKeySelective(fpUser);
            flag = "update";
        }
        return flag;
    }


    /**
     * 检查登录用户信息是否完成
     * @param request
     * @return
     */
    @RequestMapping("login/checkUser")
    @ResponseBody
    public String checkUser(HttpServletRequest request){
        String flag = "";
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(fpUser.getIdcard()) && fpUser.getIdcard() != null){
            flag = "yes";   //说明信息完整
        }else{
            flag = "no";//说明信息完整，打开弹框，让他完整信息
        }
        return flag;
    }


    /**
     * 输入用户名校验该用户名是否已经存在，用于密码修改，和用户信息完善
     */
    @RequestMapping("usernameCheck")
    @ResponseBody
    public String usernameCheck(HttpServletRequest request, String username){
        String flag = "";
        FpUser fpUser  = new FpUser();
        fpUser = fpUserService.queryUserByUserName(username);
        if(fpUser != null){
            flag = fpUser.getUsername();
        }else{
            flag = "no";
        }
        return flag;
    }



    /**
     * 输入用户名校验该用户名是否已经存在，用于密码修改，和用户信息完善
     */
    @RequestMapping("checkIdcard")
    @ResponseBody
    public String checkIdcard( String card) throws ParseException {
        String message= new IdCardManageUtil().checkIdCard(card);
        return message;
    }

    /**
     * 功能正在开发中的页面提示
     * @return
     */
    @RequestMapping("loading")
    public String loading(){
        return "loading";
    }

}
