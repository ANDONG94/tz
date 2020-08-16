package com.tdkj.controller.AccountManagement;

/**
 * Created by hedd on 2019/6/9.
 * 劳动力公益性岗位台账
 */

import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.EmploymentSituationService;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("PublicPostAccount")
public class PublicPostAccountController {
    @Autowired
    private EmploymentSituationService employmentSituationService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力公益性岗位台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountManagement/public_post_account_list";
    }


    /**
     * 查询劳动力公益性岗位 台账列表
     * @param request
     * @param treeid
     * @param response
     * @return
     */
    @RequestMapping("queryPublicPostAccountByAab301")
    @ResponseBody
    public String queryPublicPostAccountByAab301(HttpServletRequest request,EysPlf eysPlf, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(eysPlf.getAab301()) && eysPlf.getAab301() != null){
                aab301 =  aab301Substr(eysPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            eysPlf.setAab301(aab301);

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
            if(!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null){
                eysPlf.setPlf004("%"+eysPlf.getPlf004()+"%");
            }

            List<EysPlf> list= employmentSituationService.queryPublicPostAccountByAab301(eysPlf,startRecord+"",pageSize+"");
            String countlist = employmentSituationService.queryPublicPostCountByAab301(eysPlf);
            DataTableResultVO<EysPlf> result=new DataTableResultVO<>();
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
     * 根据劳动力id  删除就业   公益性岗位  信息
     * @param request
     * @param eys002
     * @param modelMap
     * @return
     */
    @RequestMapping("deletePublicPostByEys002")
    @ResponseBody
    public String deletePublicPostByEys002(HttpServletRequest request, String eys002, ModelMap modelMap){
        int s = 0;
        if(!"".equals(eys002) && eys002 != null){
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            employmentSituationService.deletePublicPostByEys002(eys002,fpUser.getRealname());
            s=1;
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据劳动力信息id  查询公益性岗位  信息列表
     */
    @RequestMapping("queryEmploymentSituationByEys002Post")
    @ResponseBody
    public List<EmploymentSituation> queryEmploymentSituationByEys002Post(HttpServletRequest request, String eys002){
        List<EmploymentSituation> list = null;
        if(!"".equals(eys002) && eys002 != null){
            list = employmentSituationService.queryEmploymentSituationByEys002(eys002);
        }
        return  list;
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
