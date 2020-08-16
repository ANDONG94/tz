package com.tdkj.controller.tobehelp;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorHouseholds;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.PoorWorkService;
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
 * 待帮扶 贫困户查询
 */
@Controller
@RequestMapping("TobeHelphu")
public class TobeHelphuController {

    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 进入待帮扶户页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "tobehelp/tobehelp_hu_list";
    }

    /**
     * 查询  待帮扶户台账列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryTobeHelpPoorByAab301")
    @ResponseBody
    public String queryTobeHelpPoorByAab301(HttpServletRequest request, PoorHouseholds poorHouseholds, HttpServletResponse response){
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
            List<PoorHouseholds> list= poorWorkService.queryTobeHelpPoorByAab301(poorHouseholds,startRecord+"",pageSize+"");
            String countlist = poorWorkService.queryAllTobeHelpPoorByAab301(poorHouseholds);
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



}
