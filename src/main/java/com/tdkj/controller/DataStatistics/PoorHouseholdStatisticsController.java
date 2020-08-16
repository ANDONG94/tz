package com.tdkj.controller.DataStatistics;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorHouseholdTranslation;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.DataStatistics.StatisticsService;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("PoorHouseholdStatistics")
public class PoorHouseholdStatisticsController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ZtreeService ztreeService;

    @RequestMapping("/queryAab301")
    public String query(PoorHouseholdTranslation poorHouseholdTranslation){

        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 =poorHouseholdTranslation.getAab301();
            if(!"".equals(aab301) && aab301 != null){
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
            }
            poorHouseholdTranslation.setAab301(aab301);
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
            String type=aab301Substr(aab301);
            poorHouseholdTranslation.setType(type);
            List<PoorHouseholdTranslation> list= statisticsService.query(poorHouseholdTranslation);
            String countlist = statisticsService.queryAllByAab301(poorHouseholdTranslation);
            DataTableResultVO<PoorHouseholdTranslation> result=new DataTableResultVO<>();
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
        String type="";
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
             type=poorXzqh.getType();
            if(type.equals("1")){
               type="2";
            }else if(type.equals("2")){
                type="4";
            }else if(type.equals("3")){
                type="6";
            }else if(type.equals("4")){
                type="9";
            }else if(type.equals("5")){
                type="12";
            }
        }
        return type;
    }
}
