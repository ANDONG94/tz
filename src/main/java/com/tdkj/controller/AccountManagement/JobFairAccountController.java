package com.tdkj.controller.AccountManagement;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.JobFair;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.JobFair.JobFairService;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("JobFairAccount")
public class JobFairAccountController {

    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private JobFairService jobFairService;



    @RequestMapping(value = "/query",produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public String getJobFair(HttpServletRequest request, JobFair jobFair, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(jobFair.getAab301()) && jobFair.getAab301() != null){
                aab301 =  aab301Substr(jobFair.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            jobFair.setAab301(aab301);

            if(!"".equals(jobFair.getJfr002()) && jobFair.getJfr002() != null){
                jobFair.setJfr002("%" +jobFair.getJfr002() +"%");
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
            List<JobFair> list= jobFairService.queryJobFairServiceByAab301(jobFair,startRecord+"",pageSize+"");
            String countlist = jobFairService.queryAllByAab301(jobFair);
            DataTableResultVO<JobFair> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
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
}
