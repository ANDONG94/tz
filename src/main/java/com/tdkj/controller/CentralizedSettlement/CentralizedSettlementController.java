package com.tdkj.controller.CentralizedSettlement;

import com.tdkj.model.CentralizedSettlements;
import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.Param.CentralizedSettlementsService;
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
 * Created by len on 2019-06-04.
 * 集中安置点
 */
@Controller
@RequestMapping("CentralizedSettlement")
public class CentralizedSettlementController {

    @Autowired
    private CentralizedSettlementsService centralizedSettlementsService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 进入集中安置点页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "CentralizedSettlement/centralized_settlement";
    }

    /**
     * 查询集中安置点页面列表
     * @param request
     * @param treeid
     * @param response
     * @return
     */
    @RequestMapping("queryCentralizedSettlementByAab301")
    @ResponseBody
    public String queryCentralizedSettlementByAab301(HttpServletRequest request, String treeid,  String cts002_scan, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            if(!"".equals(treeid) && treeid != null){
                treeid =  aab301Substr(treeid)+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                treeid = fpUser.getAab301();
                treeid =  aab301Substr(treeid)+ "%";
            }
            //集中安置点名称名称
            if(!"".equals(cts002_scan) && cts002_scan != null){
                cts002_scan = "%" +cts002_scan +"%";
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

            List<CentralizedSettlements> list= centralizedSettlementsService.queryAllCentralizedSettlementByAab301(treeid,cts002_scan,startRecord+"",pageSize+"");
            String countlist= centralizedSettlementsService.queryAllByAab301(treeid,cts002_scan);
            DataTableResultVO<CentralizedSettlements> result=new DataTableResultVO<>();
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
     * 保存安置点
     * @return
     */
    @RequestMapping("saveCentralizedSettlement")
    @ResponseBody
    public String saveCentralizedSettlement(HttpServletRequest request, ModelMap modelMap, CentralizedSettlements centralizedSettlements
    ){
        String flag = "";
        if(centralizedSettlements != null){
            if(!"".equals(centralizedSettlements.getCts001()) && centralizedSettlements.getCts001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    centralizedSettlements.setAae012(fpUser.getId());
                    centralizedSettlements.setUpdateby(fpUser.getRealname());
                    centralizedSettlements.setUpdatedate(new Date());
                }
                centralizedSettlementsService.updateByPrimaryKeySelective(centralizedSettlements);
                flag = "update";
            }else{
                centralizedSettlements.setCts001(UUIDGenerator.generate().toString());
                centralizedSettlements.setAae036(new Date());
                centralizedSettlements.setDatasource("1");//1代表录入，2代表导入
                centralizedSettlements.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    centralizedSettlements.setAae011(fpUser.getId());
                    centralizedSettlements.setCreateby(fpUser.getRealname());
                    if(!"".equals(centralizedSettlements.getAab301()) && centralizedSettlements.getAab301() != null){
                    }else{
                        centralizedSettlements.setAab301(fpUser.getAab301());
                    }
                }
                centralizedSettlementsService.insertSelective(centralizedSettlements);
                flag = "insert";
            }
        }
        return flag;

    }


    /**
     * 修改集中安置点信息
     * @return
     */
    @RequestMapping("updateCentralizedSettlement")
    @ResponseBody
    public CentralizedSettlements updateCentralizedSettlement(HttpServletRequest request, String cts001,ModelMap modelMap){
        CentralizedSettlements centralizedSettlements = null;
        if(!"".equals(cts001) && cts001 != null){
            centralizedSettlements = centralizedSettlementsService.selectByPrimaryKey(cts001);
        }
        return centralizedSettlements;
    }


    /**
     * 删除集中安置点信息
     * @return
     */
    @RequestMapping("delCentralizedSettlement")
    @ResponseBody
    public String delCentralizedSettlement(HttpServletRequest request, String cts001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(cts001) && cts001 != null){
            s = centralizedSettlementsService.deleteByPrimaryKey(cts001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            CentralizedSettlements scentralizedSettlements = new CentralizedSettlements();
            scentralizedSettlements = centralizedSettlementsService.selectByPrimaryKey(cts001);
            scentralizedSettlements.setDeleteby(fpUser.getRealname());
            scentralizedSettlements.setDeletedate(new Date());
            centralizedSettlementsService.updateByPrimaryKeySelective(scentralizedSettlements);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 判断该安置点是否已被使用
     * @param request
     * @param cts001
     * @param modelMap
     * @return
     */
    @RequestMapping("queryCentralizedSettlementIsInPhd")
    @ResponseBody
    public String queryCentralizedSettlementIsInPhd(HttpServletRequest request, String cts001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(cts001) && cts001 != null){
            String countlist =centralizedSettlementsService.queryCentralizedSettlementIsInPhd(cts001);
            if(Integer.parseInt(countlist) >0){
                s =1;
            }
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

}
