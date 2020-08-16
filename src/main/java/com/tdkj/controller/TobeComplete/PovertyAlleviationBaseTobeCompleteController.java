package com.tdkj.controller.TobeComplete;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.model.PovertyAlleviationBase;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import com.tdkj.service.Relation.RelationService;
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
 * Created by hedd on 2019/6/9.
 * 扶贫基地待完善
 */
@Controller
@RequestMapping("PovertyAlleviationBaseComplete")
public class PovertyAlleviationBaseTobeCompleteController {
    @Autowired
    private PovertyAlleviationBaseService povertyAlleviationBaseService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private RelationService relationService;

    /**
     * 进入扶贫基地待完善
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "tobecomplete/poverty_alleviation_base_tobe_complete_list";
    }


    /**
     * 查询就业扶贫基地   待完善页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryPovertyAlleviationBaseByAab301")
    @ResponseBody
    public String queryCommunityFactoryByAab301(HttpServletRequest request, PovertyAlleviationBase povertyAlleviationBase, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(povertyAlleviationBase.getAab301()) && povertyAlleviationBase.getAab301() != null){
                aab301 =  aab301Substr(povertyAlleviationBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            povertyAlleviationBase.setAab301(aab301);
            povertyAlleviationBase.setIstaizahang("1");//判断是不是台账进来查询的
            povertyAlleviationBase.setTaizhang("1");//判断是不是台账进来查询的
            //扶贫基地名称
            if(!"".equals(povertyAlleviationBase.getPab003()) && povertyAlleviationBase.getPab003() != null){
                povertyAlleviationBase.setPab003("%" +povertyAlleviationBase.getPab003() +"%");
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
            povertyAlleviationBase.setIstobecomplete("1");//待完善设置为1
            List<PovertyAlleviationBase> list= povertyAlleviationBaseService.queryPovertyAlleviationBaseByAab301(povertyAlleviationBase,startRecord+"",pageSize+"");
            String countlist = povertyAlleviationBaseService.queryAllByAab301(povertyAlleviationBase);
            DataTableResultVO<PovertyAlleviationBase> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //**************************查询待完善的扶贫基地信息  **********************************************************************************************************************

    /**
     * 进入待完善 扶贫基地  页面
     * @return
     */
    @RequestMapping("tobecompletelist")
    public String tobecompletelist(){
        return "tobecomplete/poverty_alleviation_base_tobe_complete_list";
    }


    /**
     * 查询待完善 扶贫基地   页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("querytobeCompletePovertyAlleviationBaseByAab301")
    @ResponseBody
    public String querytobeCompletePovertyAlleviationBaseByAab301(HttpServletRequest request, PovertyAlleviationBase povertyAlleviationBase, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(povertyAlleviationBase.getAab301()) && povertyAlleviationBase.getAab301() != null){
                aab301 =  aab301Substr(povertyAlleviationBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            povertyAlleviationBase.setAab301(aab301);

            //扶贫基地名称
            if(!"".equals(povertyAlleviationBase.getPab003()) && povertyAlleviationBase.getPab003() != null){
                povertyAlleviationBase.setPab003("%" +povertyAlleviationBase.getPab003() +"%");
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

            List<PovertyAlleviationBase> list= povertyAlleviationBaseService.querytobeCompletePovertyAlleviationBaseByAab301(povertyAlleviationBase,startRecord+"",pageSize+"");
            String countlist = povertyAlleviationBaseService.queryAlltobeCompleteByAab301(povertyAlleviationBase);
            DataTableResultVO<PovertyAlleviationBase> result=new DataTableResultVO<>();
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
}
