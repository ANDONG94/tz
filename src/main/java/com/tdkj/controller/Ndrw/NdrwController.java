package com.tdkj.controller.Ndrw;

import com.tdkj.model.*;
import com.tdkj.service.Ndrw.NdrwService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by hedd on 2019/8/16.
 */
@RequestMapping("ndrw")
@RestController
public class NdrwController {

    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private NdrwService ndrwService;


    /**
     * 查询信用乡村页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryNdrwByAab301")
    @ResponseBody
    public String queryNdrwByAab301(HttpServletRequest request, Ndrw ndrw, HttpServletResponse response){
        try {
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(ndrw.getAab301()) && ndrw.getAab301()!= null){
                ndrw.setClickaab301(ndrw.getAab301());//点击的aab301
                aab301 =aab301SubstrNew(ndrw.getAab301());
                ndrw.setAab301(aab301);

            }else{
                aab301 = fpUser.getAab301();
                ndrw.setClickaab301(aab301);//点击的aab301
                ndrw.setAab301(aab301SubstrNew(aab301));
            }

            String useraab301 = fpUser.getAab301();//登录人的aab301
            ndrw.setIsnext(useraab301);//登录人的aab301

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

            List<Ndrw> list= ndrwService.queryNdrwByAab301(ndrw,startRecord+"",pageSize+"");
            String countlist= ndrwService.queryAllCountByAab301(ndrw);
            DataTableResultVO<Ndrw> result=new DataTableResultVO<>();
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
     * 保存年度任务信息
     * @return
     */
    @RequestMapping("saveNdrw")
    @ResponseBody
    public String saveNdrw(HttpServletRequest request, ModelMap modelMap, Ndrw ndrw){
        String flag = "";
        if(ndrw != null){
            if(!"".equals(ndrw.getNdrwid()) && ndrw.getNdrwid() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    ndrw.setAae102(fpUser.getId());
                    ndrw.setUpdateby(fpUser.getRealname());
                    ndrw.setUpdatedate(new Date());
                    ndrw.setXzqhbm(ndrw.getAab301());
                }
                ndrwService.updateByPrimaryKeySelective(ndrw);
                flag = "update";
            }else{
                ndrw.setNdrwid(UUIDGenerator.generate().toString());
                ndrw.setAae036(new Date());
                ndrw.setAae100("1");
                ndrw.setXzqhbm(ndrw.getAab301());
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    ndrw.setAae011(fpUser.getId());
                    ndrw.setCreateby(fpUser.getRealname());
                    if(!"".equals(ndrw.getAab301()) && ndrw.getAab301() != null){
                    }else{
                        ndrw.setAab301(fpUser.getAab301());
                        ndrw.setXzqhbm(fpUser.getAab301());
                    }
                }
                ndrwService.insertSelective(ndrw);
                flag = "insert";
            }
        }
        return flag;

    }


    /**
     * 根据年度  查询该年度是否已经录入了年度任务
     * @return
     */
    @RequestMapping("checkniandu")
    @ResponseBody
    public String checkniandu(String niandu,String aab301,String ndrwid,HttpServletRequest request){

        String flag = "no";
        FpUser fpUser = null;
        fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(aab301) && aab301 != null){
            aab301 =  aab301;
        }else{
            //从session中获取当前登录人的aab301 行政区划
            aab301 = fpUser.getAab301();
        }
        if(ndrwid == null || ndrwid.equals("")){
            Ndrw ndrw = new Ndrw();
            ndrw.setAab301(aab301);
            ndrw.setNiandu(niandu);
            List<Ndrw> list= ndrwService.checkNdrwByAab301(ndrw);
            if(list.size()>0){
                flag = "yes";
            }else{
                flag = "no";
            }
        }
        return flag;
    }


    /**
     * 修改年度任务信息
     * @return
     */
    @RequestMapping("updateNdrw")
    @ResponseBody
    public Ndrw updateNdrw(HttpServletRequest request, String ndrwid,ModelMap modelMap){
        Ndrw ndrw = null;
        if(!"".equals(ndrwid) && ndrwid != null){
            ndrw = ndrwService.selectByPrimaryKey(ndrwid);
        }
        return ndrw;
    }


    /**
     * 删除年度任务信息
     * @return
     */
    @RequestMapping("delNdrw")
    @ResponseBody
    public String delNdrw(HttpServletRequest request, String ndrwid,ModelMap modelMap){
        int s = 0;
        if(!"".equals(ndrwid) && ndrwid != null){
            Ndrw ndrw = new Ndrw();
            ndrw = ndrwService.selectByPrimaryKey(ndrwid);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            ndrw.setDeleteby(fpUser.getRealname());
            ndrw.setDeletedate(new Date());
            ndrw.setAae100("0");
           s =  ndrwService.updateByPrimaryKeySelective(ndrw);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }



    //获取aab301的截取
    public  String aab301SubstrNew(String aab301){
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5)+"__00000";
        }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
            String type=poorXzqh.getType();
            if(type.equals("1")){
                aab301=aab301.substring(0, 2)+"__00000000";
            }else if(type.equals("2")){
                aab301=aab301.substring(0, 4)+"__000000";
            }else if(type.equals("3")){
                aab301=aab301.substring(0, 6)+"___000";
            }else if(type.equals("4")){
                aab301=aab301.substring(0, 9)+"___";
            }else if(type.equals("5")){
                aab301=aab301;
            }
        }
        return aab301;
    }



    //获取aab301的截取   2019-10-24日弃用   因为年度任务查询只查询出 下面一级的数据
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
