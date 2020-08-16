package com.tdkj.controller.Information;

import com.tdkj.model.*;
import com.tdkj.service.Information.BusinessGuaranteeLoanService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by hedd on 2019/11/23.
 * 创业担保贷款
 */
@Controller
@RequestMapping("businessGuaranteeLoan")
public class BusinessGuaranteeLoanController {

    @Autowired
    private BusinessGuaranteeLoanService businessGuaranteeLoanService;

    @Autowired
    private ZtreeService ztreeService;


    /**
     * 创业担保贷款 台账页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountInformation/business_guarantee_loan_account_list";
    }


    /**
     * 保存创业担保贷款信息
     * @param request
     * @param modelMap
     * @param businessGuaranteeLoan
     * @return
     */
    @RequestMapping("saveBusinessGuaranteeLoan")
    @ResponseBody
    public String saveBusinessGuaranteeLoan(HttpServletRequest request, ModelMap modelMap, BusinessGuaranteeLoan businessGuaranteeLoan){
        String flag = "";
        if(businessGuaranteeLoan != null){
            if(!"".equals(businessGuaranteeLoan.getBgl001()) && businessGuaranteeLoan.getBgl001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    businessGuaranteeLoan.setAae012(fpUser.getId());
                    businessGuaranteeLoan.setUpdateby(fpUser.getRealname());
                    businessGuaranteeLoan.setUpdatedate(new Date());
                }
                businessGuaranteeLoanService.updateByPrimaryKeySelective(businessGuaranteeLoan);
                flag = "update";
            }else{
                businessGuaranteeLoan.setBgl001(UUIDGenerator.generate().toString());
                businessGuaranteeLoan.setAae036(new Date());
                businessGuaranteeLoan.setDatasource("1");//1代表录入，2代表导入
                businessGuaranteeLoan.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    businessGuaranteeLoan.setAae011(fpUser.getId());
                    businessGuaranteeLoan.setCreateby(fpUser.getRealname());
                    if(!"".equals(businessGuaranteeLoan.getAab301()) && businessGuaranteeLoan.getAab301() != null){
                    }else{
                        businessGuaranteeLoan.setAab301(fpUser.getAab301());
                    }
                }
                businessGuaranteeLoanService.insertSelective(businessGuaranteeLoan);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改创业担保贷款信息
     * @return
     */
    @RequestMapping("updateBusinessGuaranteeLoan")
    @ResponseBody
    public BusinessGuaranteeLoan updateBusinessGuaranteeLoan(HttpServletRequest request, String bgl001,ModelMap modelMap){
        BusinessGuaranteeLoan businessGuaranteeLoan = null;
        if(!"".equals(bgl001) && bgl001 != null){
            businessGuaranteeLoan = businessGuaranteeLoanService.selectByPrimaryKey(bgl001);
        }
        return businessGuaranteeLoan;
    }


    /**
     * 删除创业担保贷款信息
     * @return
     */
    @RequestMapping("delBusinessGuaranteeLoan")
    @ResponseBody
    public String delBusinessGuaranteeLoan(HttpServletRequest request, String bgl001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(bgl001) && bgl001 != null){
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            BusinessGuaranteeLoan businessGuaranteeLoan = new BusinessGuaranteeLoan();
            businessGuaranteeLoan = businessGuaranteeLoanService.selectByPrimaryKey(bgl001);
            businessGuaranteeLoan.setDeleteby(fpUser.getRealname());
            businessGuaranteeLoan.setDeletedate(new Date());
            businessGuaranteeLoan.setAae100("0");
            s = businessGuaranteeLoanService.updateByPrimaryKeySelective(businessGuaranteeLoan);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据aab301  查询创业担保贷款信息列表
     */
    @RequestMapping("queryBusinessGuaranteeLoanByAab301")
    @ResponseBody
    public String queryBusinessGuaranteeLoanByAab301(HttpServletRequest request,BusinessGuaranteeLoan businessGuaranteeLoan){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(businessGuaranteeLoan.getAab301()) && businessGuaranteeLoan.getAab301() != null){
                aab301 =  aab301Substr(businessGuaranteeLoan.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            businessGuaranteeLoan.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<BusinessGuaranteeLoan> list= businessGuaranteeLoanService.queryBusinessGuaranteeLoanByAab301(businessGuaranteeLoan,startRecord+"",pageSize+"");
            String countlist = businessGuaranteeLoanService.queryAllBusinessGuaranteeLoanCountByAab301(businessGuaranteeLoan);
            DataTableResultVO<BusinessGuaranteeLoan> result=new DataTableResultVO<>();
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
