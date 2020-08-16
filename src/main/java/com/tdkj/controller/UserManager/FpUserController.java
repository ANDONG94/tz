package com.tdkj.controller.UserManager;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.UserManager.FpUserService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.MD5Util;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by len on 2019-05-03.
 */
@Controller
@RequestMapping("fpUser")
public class FpUserController {

    @Autowired
    private FpUserService fpUserService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 进入用户管理界面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "UserManager/fpUser_list";
    }


    /**
     * 查询用户信息页面列表
     * @param request
     * @param treeid
     * @param response
     * @return
     */
    @RequestMapping("queryFpUserByAab301")
    @ResponseBody
    public String queryFpUserByAab301(HttpServletRequest request, String aab301, String username, String realname,String idcard, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            if(!"".equals(aab301) && aab301 != null){
                aab301 =  aab301Substr(aab301)+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start)+1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }


            List<FpUser> list= fpUserService.queryFpUserByAab301(aab301,username,realname,idcard,startRecord+"",pageSize+"");
            String countlist= fpUserService.queryAllByAab301(aab301,username,realname,idcard);
            DataTableResultVO<FpUser> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息
     * @return
     */
    @RequestMapping("saveFpUser")
    @ResponseBody
    public String saveFpUser(HttpServletRequest request, ModelMap modelMap, FpUser fpUser){
        String flag = "";
        if(fpUser != null){
            if(!"".equals(fpUser.getId()) && fpUser.getId() != null){
                fpUserService.updateByPrimaryKeySelective(fpUser);
                flag = "update";
            }else{
                fpUser.setId(UUIDGenerator.generate().toString());
                fpUser.setCreateddate(new Date());
                fpUserService.insertSelective(fpUser);
                flag = "insert";
            }
        }
        return flag;

    }


    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping("updateFpUser")
    @ResponseBody
    public FpUser updateFpUser(HttpServletRequest request, String id,ModelMap modelMap){
        FpUser fpUser = null;
        if(!"".equals(id) && id != null){
            fpUser = fpUserService.selectByPrimaryKey(id);
        }
        return fpUser;
    }


    /**
     * 删除用户信息
     * @return
     */
    @RequestMapping("delFpUser")
    @ResponseBody
    public String delFpUser(HttpServletRequest request, String id,ModelMap modelMap){
        int s = 0;
        if(!"".equals(id) && id != null){
            s = fpUserService.deleteByPrimaryKey(id);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    //获取aab301的截取
    public  String aab301Substr(String aab301){
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
            String type=poorXzqh.getType();
            if(type.equals("1")){
                aab301=aab301.substring(0, 2);
            }else if(type.equals("2")){
                aab301=aab301.substring(0, 4);
            }else if(type.equals("3")){
                aab301=aab301.substring(0, 6);
            }else if(type.equals("4")){
                aab301=aab301.substring(0, 9);
            }else if(type.equals("5")){
                aab301=aab301;
            }
        }
        return aab301;
    }



    /**
     * 自动生成用户账号密码
     * 单个地方
     * @param counts  每个地方生成生成几个账号
     * @return
     */
    @RequestMapping("zidongByOne")
    @ResponseBody
    public String zidongByOne(HttpServletRequest request, String aab301,String counts,ModelMap modelMap){
        //根据省获取市aab301
        Integer num = Integer.parseInt(counts);
        List<PoorXzqh> list = ztreeService.queryArea(aab301);
        for (int j=0;j<num;j++){
            if(list.size()>0){
                for (int i=0;i<list.size();i++){
                    FpUser fpUser = new FpUser();
                    fpUser.setId(UUIDGenerator.generate().toString());
                    fpUser.setCreateddate(new Date());
                    fpUser.setAab301(list.get(i).getXzqhbm());
                    fpUser.setUsername(getRandomString(6));
                    fpUser.setPassword(MD5Util.getMD5("123456"));
                    fpUser.setUsertype("1");//1经办员   2管理员
                    System.out.print("用户名======"+fpUser.getUsername());
                   fpUserService.insertSelective(fpUser);
                }
            }
        }
        return "yes";
    }


    /**
     * 自动生成  所有下级账号
     * @return
     */
    @RequestMapping("zidongByAllNext")
    @ResponseBody
    public String zidongByAllNext(HttpServletRequest request, String aab301,String counts,ModelMap modelMap){
        //根据省获取市aab301
        Integer num = Integer.parseInt(counts);
        List<PoorXzqh> list = ztreeService.queryAreaByPid(aab301);
        for (int j=0;j<num;j++){
            if(list.size()>0){
                for (int i=0;i<list.size();i++){
                    FpUser fpUser = new FpUser();
                    fpUser.setId(UUIDGenerator.generate().toString());
                    fpUser.setCreateddate(new Date());
                    fpUser.setAab301(list.get(i).getXzqhbm());
                    fpUser.setUsername(getRandomString(6));
                    fpUser.setPassword(MD5Util.getMD5("123456"));
                    fpUser.setUsertype("1");//1经办员   2管理员
                    System.out.print("用户名======"+fpUser.getUsername());
                    fpUserService.insertSelective(fpUser);
                }
            }
        }
        return "yes";
    }


    /**
     * 生成随机字符串
     * length 代办生成几位
     * @return
     */
    public  String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(52);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

}
