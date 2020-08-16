package com.tdkj.controller.tobehelp;

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
 * Created by hedd on 2019/10/4.
 */
@Controller
@RequestMapping("TobeHelpWorker")
public class TobeHelpWorkerController {

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
     * 进入未就业创业劳动力查询页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "tobehelp/tobehelp_worker_list";
    }


    /**
     * 查询未就业创业劳动力 列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryTobeHelpWorkerByAab301")
    @ResponseBody
    public String queryTobeHelpWorkerByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce, HttpServletResponse response){
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

            List<PoorLaborForce> list= poorLaborForceService.queryTobeHelpWorkerByAab301(poorLaborForce,startRecord+"",pageSize+"");
            String countlist = poorLaborForceService.queryAllTobeHelpWorkerByAab301(poorLaborForce);
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


}
