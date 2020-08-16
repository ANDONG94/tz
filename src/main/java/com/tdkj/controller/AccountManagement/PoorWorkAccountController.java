package com.tdkj.controller.AccountManagement;

import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.*;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by len on 2019-05-10.
 * 贫困户台账管理
 */
@Controller
@RequestMapping("poorAccount")
public class PoorWorkAccountController {

    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 进入贫困户台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountManagement/poorwork_account_list";
    }

    /**
     * 查询贫困户台账列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryPoorAccountByAab301")
    @ResponseBody
    public String queryPoorAccountByAab301(HttpServletRequest request,PoorHouseholds poorHouseholds, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorHouseholds.getAab301()) && poorHouseholds.getAab301() != null){
                aab301 =  aab301Substr(poorHouseholds.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorHouseholds.setAab301(aab301);

           ///开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start)+1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }
            if(!"".equals(poorHouseholds.getPhd002()) && poorHouseholds.getPhd002() != null){
                poorHouseholds.setPhd002("%"+poorHouseholds.getPhd002()+"%");
            }
            //如果不选的赋值为99解决mybatis把空判断为等于0
            if ("".equals(poorHouseholds.getPhd008_yes())) {
                poorHouseholds.setPhd008_yes("99");
            }
            List<PoorHouseholds> list= poorWorkService.queryPoorAccountByAab301(poorHouseholds,startRecord+"",pageSize+"");
            String countlist = poorWorkService.queryAllAccoutByAab301(poorHouseholds);
            DataTableResultVO<PoorHouseholds> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

    //**************************以下是贫困户待确认信息************************************************************************************************

    /**
     * 进入待确认贫困户页面
     * @return
     */
    @RequestMapping("tobecomfirelist")
    public String tobecomfirelist(){
        return "tobecomfire/poorwork_tobe_comfire_list";
    }


    /**
     * 根据行政区划查询 待确认贫困户信息
     * @return
     */
    @RequestMapping("queryToBeComfirePoorByAab301")
    @ResponseBody
    public String queryToBeComfirePoorByAab301(HttpServletRequest request,PoorHouseholds poorHouseholds, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorHouseholds.getAab301()) && poorHouseholds.getAab301() != null){
                aab301 =  aab301Substr(poorHouseholds.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorHouseholds.setAab301(aab301);

            ///开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start)+1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }
            if(!"".equals(poorHouseholds.getPhd002()) && poorHouseholds.getPhd002() != null){
                poorHouseholds.setPhd002("%"+poorHouseholds.getPhd002()+"%");
            }

            List<PoorHouseholds> list= poorWorkService.queryToBeComfirePoorByAab301(poorHouseholds,startRecord+"",pageSize+"");
            String countlist = poorWorkService.queryAllToBeComfireByAab301(poorHouseholds);
            DataTableResultVO<PoorHouseholds> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //**************************以下是贫困户待完善信息************************************************************************************************

    /**
     * 进入待完善贫困户页面
     * @return
     */
    @RequestMapping("tobecompletelist")
    public String tobecompletelist(){
        return "tobecomplete/poorwork_tobe_complete_list";
    }


    /**
     * 根据行政区划查询 待完善贫困户信息
     * @return
     */
    @RequestMapping("queryToBeCompletePoorByAab301")
    @ResponseBody
    public String queryToBeCompletePoorByAab301(HttpServletRequest request,PoorHouseholds poorHouseholds, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorHouseholds.getAab301()) && poorHouseholds.getAab301() != null){
                aab301 =  aab301Substr(poorHouseholds.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorHouseholds.setAab301(aab301);

            ///开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start)+1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }
            if(!"".equals(poorHouseholds.getPhd002()) && poorHouseholds.getPhd002() != null){
                poorHouseholds.setPhd002("%"+poorHouseholds.getPhd002()+"%");
            }

            List<PoorHouseholds> list= poorWorkService.queryToBeCompletePoorByAab301(poorHouseholds,startRecord+"",pageSize+"");
            String countlist = poorWorkService.queryAllToBeCompleteByAab301(poorHouseholds);
            DataTableResultVO<PoorHouseholds> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
