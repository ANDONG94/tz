package com.tdkj.controller.PovertyAlleviationBase;

import com.tdkj.model.*;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import com.tdkj.service.Relation.RelationService;
import com.tdkj.service.Subsidy.EmploymentPovertySubsidyService;
import com.tdkj.service.Ztree.ZtreeService;
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

/**
 * Created by len on 2019-04-29.
 * 就业扶贫基地台账
 */
@Controller
@RequestMapping("povertyBase")
public class PovertyAlleviationBaseController {

    @Autowired
    private PovertyAlleviationBaseService povertyAlleviationBaseService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private EmploymentPovertySubsidyService employmentPovertySubsidyService;

    /**
     * 查询就业扶贫基地页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryPovertyAlleviationBaseByAab301")
    @ResponseBody
    public String queryCommunityFactoryByAab301(HttpServletRequest request, PovertyAlleviationBase povertyAlleviationBase,HttpServletResponse response){
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

    /**
     * 保存就业扶贫基地信息
     * @return
     */
    @RequestMapping("savePovertyBase")
    @ResponseBody
    public String savePovertyBase(HttpServletRequest request, ModelMap modelMap, PovertyAlleviationBase povertyAlleviationBase){
        String flag = "";
        if(povertyAlleviationBase != null){
            if(!"".equals(povertyAlleviationBase.getPab001()) && povertyAlleviationBase.getPab001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    povertyAlleviationBase.setAae012(fpUser.getId());
                    povertyAlleviationBase.setUpdateby(fpUser.getRealname());
                    povertyAlleviationBase.setUpdatedate(new Date());
                }
                povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
                flag = "update";
            }else{
                povertyAlleviationBase.setPab001(UUIDGenerator.generate().toString());
                povertyAlleviationBase.setAae036(new Date());
                povertyAlleviationBase.setDatasource("1");
                povertyAlleviationBase.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    povertyAlleviationBase.setAae011(fpUser.getId());
                    povertyAlleviationBase.setCreateby(fpUser.getRealname());
                    if(!"".equals(povertyAlleviationBase.getAab301()) && povertyAlleviationBase.getAab301() != null){
                    }else{
                        povertyAlleviationBase.setAab301(fpUser.getAab301());
                    }
                }
                povertyAlleviationBaseService.insertSelective(povertyAlleviationBase);
                flag = povertyAlleviationBase.getPab001();
            }
        }
        return flag;

    }


    /**
     * 修改就业扶贫基地信息
     * @return
     */
    @RequestMapping("updatePovertyBase")
    @ResponseBody
    public PovertyAlleviationBase updatePovertyBase(HttpServletRequest request, String pab001,ModelMap modelMap){
        PovertyAlleviationBase povertyAlleviationBase = null;
        if(!"".equals(pab001) && pab001 != null){
            povertyAlleviationBase = povertyAlleviationBaseService.selectByPrimaryKey(pab001);

        }
        return povertyAlleviationBase;
    }


    /**
     * 删除就业扶贫基地信息
     * @return
     */
    @RequestMapping("delPovertyBase")
    @ResponseBody
    public String delPovertyBase(HttpServletRequest request, String pab001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(pab001) && pab001 != null){
            relationService.updateByCompanyid(pab001);
            PovertyAlleviationBase povertyAlleviationBase = new PovertyAlleviationBase();
            povertyAlleviationBase = povertyAlleviationBaseService.selectByPrimaryKey(pab001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            povertyAlleviationBase.setDeleteby(fpUser.getRealname());
            povertyAlleviationBase.setDeletedate(new Date());
            povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
            s = povertyAlleviationBaseService.deleteByPrimaryKey(pab001);

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
        }else if(poorXzqh!=null && !"".equals(poorXzqh)){
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


    /********************************以下是补贴信息***************************************************************************************/

    /**
     * 根据基地id  查询补贴
     */
    @RequestMapping("querEmploymentPovertySubsidyByEps002")
    @ResponseBody
    public List<EmploymentPovertySubsidy> querEmploymentPovertySubsidyByEps002(HttpServletRequest request, String eps002){
        List<EmploymentPovertySubsidy> list = null;
        if(!"".equals(eps002) && eps002 != null){
            list = employmentPovertySubsidyService.querEmploymentPovertySubsidyByEps002(eps002);
        }
        return  list;
    }


    /**
     * 保存补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveEmploymentPovertySubsidy")
    @ResponseBody
    public String saveEmploymentPovertySubsidy(HttpServletRequest request,ModelMap modelMap,String jiuyeaab301, EmploymentPovertySubsidy employmentPovertySubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(employmentPovertySubsidy != null){
            if(!"".equals(employmentPovertySubsidy.getEps001()) && employmentPovertySubsidy.getEps001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentPovertySubsidy.setAae012(fpUser.getId());
                    employmentPovertySubsidy.setUpdateby(fpUser.getRealname());
                    employmentPovertySubsidy.setUpdatedate(new Date());
                }

                employmentPovertySubsidyService.updateByPrimaryKeySelective(employmentPovertySubsidy);

                flag = "update";
            }else{
                employmentPovertySubsidy.setEps001(UUIDGenerator.generate().toString());
                employmentPovertySubsidy.setAae036(new Date());
                employmentPovertySubsidy.setDatasource("1");
                employmentPovertySubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentPovertySubsidy.setAae011(fpUser.getId());
                    employmentPovertySubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(jiuyeaab301) && jiuyeaab301 != null){
                        employmentPovertySubsidy.setAab301(jiuyeaab301);
                    }else{
                        employmentPovertySubsidy.setAab301(fpUser.getAab301());
                    }
                }

                employmentPovertySubsidyService.insertSelective(employmentPovertySubsidy);
                flag = employmentPovertySubsidy.getEps001();
            }
        }

        //根据扶贫基地 id  更新社区工厂中的主字段  pab011以工代训补贴   pab012一次性奖补  pab015奖补性职业介绍补贴（）元
        String eps002 = employmentPovertySubsidy.getEps002();//扶贫基地 id
        EmploymentPovertySubsidy povertySubsidy = employmentPovertySubsidyService.selectSubsibyByEps002(eps002);
        PovertyAlleviationBase povertyAlleviationBase = povertyAlleviationBaseService.selectByPrimaryKey(eps002);
        if(povertySubsidy != null){
            povertyAlleviationBase.setPab011(Long.parseLong(povertySubsidy.getEps005()));
            povertyAlleviationBase.setPab012(Long.parseLong(povertySubsidy.getEps006()));
            povertyAlleviationBase.setPab015(povertySubsidy.getEps007());
            povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
        }
        return flag;
    }


    /**
     * 修改补贴信息
     * @return
     */
    @RequestMapping("updateEmploymentPovertySubsidy")
    @ResponseBody
    public EmploymentPovertySubsidy updateEmploymentPovertySubsidy(HttpServletRequest request,String eps001,ModelMap modelMap){
        EmploymentPovertySubsidy employmentPovertySubsidy = null;
        if(!"".equals(eps001) && eps001 != null){
            employmentPovertySubsidy = employmentPovertySubsidyService.selectByPrimaryKey(eps001);
        }
        return employmentPovertySubsidy;
    }

    /**
     * 根据  补贴 基地id
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteEmploymentPovertySubsidy")
    @ResponseBody
    public String deleteEmploymentPovertySubsidy(HttpServletRequest request,String eps001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(eps001) && eps001 != null){
            s = employmentPovertySubsidyService.deleteByPrimaryKey(eps001,fpUser.getRealname());
            //根据扶贫基地 id  更新社区工厂中的主字段  pab011以工代训补贴   pab012一次性奖补  pab015奖补性职业介绍补贴（）元
            EmploymentPovertySubsidy employmentPovertySubsidy = employmentPovertySubsidyService.selectByPrimaryKey(eps001);
            String eps002 = employmentPovertySubsidy.getEps002();//扶贫基地 id
            EmploymentPovertySubsidy povertySubsidy = employmentPovertySubsidyService.selectSubsibyByEps002(eps002);
            PovertyAlleviationBase povertyAlleviationBase = povertyAlleviationBaseService.selectByPrimaryKey(eps002);
            if(povertySubsidy != null){
                povertyAlleviationBase.setPab011(Long.parseLong(povertySubsidy.getEps005()));
                povertyAlleviationBase.setPab012(Long.parseLong(povertySubsidy.getEps006()));
                povertyAlleviationBase.setPab015(povertySubsidy.getEps007());
                povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
            }
        }

        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据基地 id
     * 删除贴信息
     * @return
     */
    @RequestMapping("deleteEmploymentPovertySubsidyByEps002")
    @ResponseBody
    public String deleteEmploymentPovertySubsidyByEps002(HttpServletRequest request,String eps002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(eps002) && eps002 != null){
            s = employmentPovertySubsidyService.deleteEmploymentPovertySubsidyByEps002(eps002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

}
