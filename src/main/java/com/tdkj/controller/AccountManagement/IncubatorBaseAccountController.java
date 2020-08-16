package com.tdkj.controller.AccountManagement;

/**
 * Created by hedd on 2019/6/9.
 * 创业园区中心台账
 */

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.IncubatorBase;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.IncubatorBase.IncubatorBaseService;
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

@Controller
@RequestMapping("IncubatorBaseAccount")
public class IncubatorBaseAccountController {

    @Autowired
    private IncubatorBaseService incubatorBaseService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 进入创业基地园区中心台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountManagement/Incubator_base_account_list";
    }

    /**
     * 查询创业基地园区中心页面列表
     * @param treeid
     * @param response
     * @return
     */
    @RequestMapping("queryIncubatorBaseByAab301")
    @ResponseBody
    public String queryIncubatorBaseByAab301(HttpServletRequest request, IncubatorBase incubatorBase, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(incubatorBase.getAab301()) && incubatorBase.getAab301() != null){
                aab301 =  aab301Substr(incubatorBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            incubatorBase.setAab301(aab301);

            //园区基地名称
            if(!"".equals(incubatorBase.getIbb003()) && incubatorBase.getIbb003() != null){
                incubatorBase.setIbb003("%" +incubatorBase.getIbb003() +"%");
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

            List<IncubatorBase> list= incubatorBaseService.queryIncubatorBaseByAab301(incubatorBase,startRecord+"",pageSize+"");
            String countlist =incubatorBaseService.queryAllByAab301(incubatorBase);
            DataTableResultVO<IncubatorBase> result=new DataTableResultVO<>();
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
