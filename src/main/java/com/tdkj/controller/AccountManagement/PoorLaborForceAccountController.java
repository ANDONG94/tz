package com.tdkj.controller.AccountManagement;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.*;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hedd on 2019/5/25.
 */
@Controller
@RequestMapping("PoorLaborForceAccount")
public class PoorLaborForceAccountController {
    @Autowired
    private PoorLaborForceService poorLaborForceService;
    @Autowired
    private EmploymentSituationService employmentSituationService;
    @Autowired
    private EntrepreneurshipService entrepreneurshipService;
    @Autowired
    private TrainingSituationService trainingSituationService;
    @Autowired
    private TechnicalSchoolsService technicalSchoolsService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountManagement/poor_labor_force_account_list";
    }


    /**
     * 查询劳动力台账列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryPoorLaborForceAccountByAab301")
    @ResponseBody
    public String queryPoorLaborForceAccountByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce,HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

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
            if(!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null){
                poorLaborForce.setPlf004("%"+poorLaborForce.getPlf004()+"%") ;
            }

            List<PoorLaborForce> list= poorLaborForceService.queryPoorLaborForceImportByAab301(poorLaborForce,startRecord+"",pageSize+"");
            String countlist = poorLaborForceService.queryAllPoorLaborForceAccoutByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result=new DataTableResultVO<>();
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

    /**
     * 批量   删除劳动力信息
     * @return
     */
    @RequestMapping("delAllWorkerByIds")
    @ResponseBody
    public String delAllPoorWorkByIds(HttpServletRequest request, @RequestParam(value = "plf001s[]") String[] plf001s, ModelMap modelMap){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(plf001s) && plf001s != null){
            //循环删除劳动力
            for (int i=0;i<plf001s.length;i++){
                employmentSituationService.deleteEmploymentSituationByEys002(plf001s[i],fpUser.getRealname());
                entrepreneurshipService.deleteEntrepreneurshipByEpp002(plf001s[i],fpUser.getRealname());
                trainingSituationService.deleteTrainingSituationByTsn010(plf001s[i],fpUser.getRealname());
                technicalSchoolsService.deleteTechnicalSchoolsByThs002(plf001s[i],fpUser.getRealname());
                poorLaborForceService.deleteByPrimaryKey(plf001s[i]);//删除劳动力
                s++;
            }
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 进入待确认劳动力页面
     * @return
     */
    @RequestMapping("tobecomfirelist")
    public String tobecomfirelist(){
        return "tobecomfire/poor_labor_force_tobe_comfire_list";
    }


    /**
     * 查询待确认劳动力列表
     * @param queryToBeComfirePoorByAab301
     * @param response
     * @return
     */
    @RequestMapping("queryPoorLaborForceToBeComfireByAab301")
    @ResponseBody
    public String queryPoorLaborForceToBeComfireByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce,HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

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
            if(!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null){
                poorLaborForce.setPlf004("%"+poorLaborForce.getPlf004()+"%") ;
            }

            List<PoorLaborForce> list= poorLaborForceService.queryPoorLaborForceToBeComfireByAab301(poorLaborForce,startRecord+"",pageSize+"");
            String countlist = poorLaborForceService.queryAllPoorLaborForceToBeComfireByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result=new DataTableResultVO<>();
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
     * 进入弱劳动力台账页面
     * @return
     */
    @RequestMapping("ruolist")
    public String ruolist(){
        return "accountManagement/ruo_poor_labor_force_account_list";
    }


    /**
     * 查询弱劳动力台账列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryRuoPoorLaborForceAccountByAab301")
    @ResponseBody
    public String queryRuoPoorLaborForceAccountByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce,HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

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
            if(!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null){
                poorLaborForce.setPlf004("%"+poorLaborForce.getPlf004()+"%") ;
            }

            List<PoorLaborForce> list= poorLaborForceService.queryRuoPoorLaborForceAccountByAab301(poorLaborForce,startRecord+"",pageSize+"");
            String countlist = poorLaborForceService.queryAllRuoPoorLaborForceAccoutByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result=new DataTableResultVO<>();
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
