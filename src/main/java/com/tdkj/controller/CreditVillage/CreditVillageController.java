package com.tdkj.controller.CreditVillage;

import com.tdkj.model.*;
import com.tdkj.service.CreditVillage.CreditVillageService;
import com.tdkj.service.CreditVillage.CreditVillageSubsibyService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2019-04-29.
 * 信用乡村
 */
@Controller
@RequestMapping("CreditVillage")
public class CreditVillageController {
    @Autowired
    private CreditVillageService creditVillageService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private CreditVillageSubsibyService creditVillageSubsibyService;

    /**
     * 进入信用乡村页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "CreditVillage/Credit_village";
    }

    /**
     * 查询信用乡村页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryCreditVillageByAab301")
    @ResponseBody
    public String queryCreditVillageByAab301(HttpServletRequest request, CreditVillage creditVillage,HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(creditVillage.getAab301()) && creditVillage.getAab301() != null){
                aab301 =  aab301Substr(creditVillage.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            creditVillage.setAab301(aab301);

            //信用乡村名称
            if(!"".equals(creditVillage.getCvg002()) && creditVillage.getCvg002() != null){
                creditVillage.setCvg002("%" +creditVillage.getCvg002() +"%");
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

            List<CreditVillage> list= creditVillageService.queryCreditVillageByAab301(creditVillage,startRecord+"",pageSize+"");
            String countlist= creditVillageService.queryAllByAab301(creditVillage);
            DataTableResultVO<CreditVillage> result=new DataTableResultVO<>();
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
     * 保存信用乡村信息
     * @return
     */
    @RequestMapping("saveCreditVillage")
    @ResponseBody
    public String saveCreditVillage(HttpServletRequest request, ModelMap modelMap, CreditVillage creditVillage){
        String flag = "";
        if(creditVillage != null){
            if(!"".equals(creditVillage.getCvg001()) && creditVillage.getCvg001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    creditVillage.setAae012(fpUser.getId());
                    creditVillage.setUpdateby(fpUser.getRealname());
                    creditVillage.setUpdatedate(new Date());
                }
                creditVillageService.updateByPrimaryKeySelective(creditVillage);
                flag = "update";
            }else{
                creditVillage.setCvg001(UUIDGenerator.generate().toString());
                creditVillage.setAae036(new Date());
                creditVillage.setDatasource("1");//1代表录入，2代表导入
                creditVillage.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    creditVillage.setAae011(fpUser.getId());
                    creditVillage.setCreateby(fpUser.getRealname());
                    if(!"".equals(creditVillage.getAab301()) && creditVillage.getAab301() != null){
                    }else{
                        creditVillage.setAab301(fpUser.getAab301());
                    }
                }
                creditVillageService.insertSelective(creditVillage);
                flag = creditVillage.getCvg001();
            }
        }
        return flag;

    }


    /**
     * 修改信用乡村信息
     * @return
     */
    @RequestMapping("updateCreditVillage")
    @ResponseBody
    public CreditVillage updateCreditVillage(HttpServletRequest request, String cvg001,ModelMap modelMap){
        CreditVillage creditVillage = null;
        if(!"".equals(cvg001) && cvg001 != null){
            creditVillage = creditVillageService.selectByPrimaryKey(cvg001);
        }
        return creditVillage;
    }


    /**
     * 删除信用乡村信息
     * @return
     */
    @RequestMapping("delCreditVillage")
    @ResponseBody
    public String delCreditVillage(HttpServletRequest request, String cvg001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(cvg001) && cvg001 != null){
            CreditVillage creditVillage = new CreditVillage();
            creditVillage = creditVillageService.selectByPrimaryKey(cvg001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            creditVillage.setDeleteby(fpUser.getRealname());
            creditVillage.setDeletedate(new Date());
            creditVillage.setAae100("0");
           s=  creditVillageService.updateByPrimaryKeySelective(creditVillage);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /***********************************一下是  信用乡村创业情况 以及补贴***************************************************************************************************/


    /**
     * 根据社区工厂id  查询创业情况 以及补贴
     */
    @RequestMapping("querCreditVillageSubsibyByCvs002")
    @ResponseBody
    public List<CreditVillageSubsiby> querCreditVillageSubsibyByCvs002(HttpServletRequest request,String cvs002){
        List<CreditVillageSubsiby> list = null;
        if(!"".equals(cvs002) && cvs002 != null){
            list = creditVillageSubsibyService.querCreditVillageSubsibyByCvs002(cvs002);
        }
        return  list;
    }


    /**
     * 保存信用乡村信息  创业情况 以及补贴
     * @return
     */
    @RequestMapping("saveCreditVillageSubsiby")
    @ResponseBody
    public String saveCreditVillageSubsiby(HttpServletRequest request, ModelMap modelMap, CreditVillageSubsiby creditVillageSubsiby){
        String flag = "";
        if(creditVillageSubsiby != null){
            if(!"".equals(creditVillageSubsiby.getCvs001()) && creditVillageSubsiby.getCvs001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    creditVillageSubsiby.setAae012(fpUser.getId());
                    creditVillageSubsiby.setUpdateby(fpUser.getRealname());
                    creditVillageSubsiby.setUpdatedate(new Date());
                }
                creditVillageSubsibyService.updateByPrimaryKeySelective(creditVillageSubsiby);
                flag = "update";
            }else{
                creditVillageSubsiby.setCvs001(UUIDGenerator.generate().toString());
                creditVillageSubsiby.setAae036(new Date());
                creditVillageSubsiby.setDatasource("1");//1代表录入，2代表导入
                creditVillageSubsiby.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    creditVillageSubsiby.setAae011(fpUser.getId());
                    creditVillageSubsiby.setCreateby(fpUser.getRealname());
                    if(!"".equals(creditVillageSubsiby.getAab301()) && creditVillageSubsiby.getAab301() != null){
                    }else{
                        creditVillageSubsiby.setAab301(fpUser.getAab301());
                    }
                }
                creditVillageSubsibyService.insertSelective(creditVillageSubsiby);
                //根据信用乡村id  查询补贴，赋值给信用乡村表格
                flag = creditVillageSubsiby.getCvs001();
            }
        }

        //根据信用乡村id  更新信用乡村中的主字段  cvg013成功创业   cvg014贫困劳动力成功创业    cvg005当年新增贷款户数    cvg006当年新增贷款贫困户数   cvg007当年新增贷款总额（万元）   cvg008贫困户新增贷款总额（万元）
       // cvg010    年度
       // cvg013    成功创业             cvs003
       // cvg014    贫困劳动力成功创业   cvs004
       // cvg005   当年新增贷款户数      cvs005
       // cvg006   当年新增贷款贫困户数  cvs006
       // cvg007   当年新增贷款总额（万元）   cvs007
      //  cvg008   贫困户新增贷款总额（万元） cvs008

        String cvs002 = creditVillageSubsiby.getCvs002();//信用乡村id
        CreditVillageSubsiby subsidy = creditVillageSubsibyService.selectSubsibyByCvs002(cvs002);//计算个数

        CreditVillage creditVillage = creditVillageService.selectByPrimaryKey(cvs002);
        if(creditVillage != null){
            creditVillage.setCvg013(subsidy.getCvs003());
            creditVillage.setCvg014(subsidy.getCvs004());
            creditVillage.setCvg005(Integer.parseInt(subsidy.getCvs005()));
            creditVillage.setCvg006(Integer.parseInt(subsidy.getCvs006()));
            creditVillage.setCvg007(new BigDecimal(subsidy.getCvs007()));
            creditVillage.setCvg008(new BigDecimal(subsidy.getCvs008()));
            creditVillageService.updateByPrimaryKeySelective(creditVillage);
        }
        return flag;

    }


    /**
     * 修改信用乡村信息   创业情况 以及补贴
     * @return
     */
    @RequestMapping("updateCreditVillageSubsiby")
    @ResponseBody
    public CreditVillageSubsiby updateCreditVillageSubsiby(HttpServletRequest request, String cvs001,ModelMap modelMap){
        CreditVillageSubsiby creditVillageSubsiby = null;
        if(!"".equals(cvs001) && cvs001 != null){
            creditVillageSubsiby = creditVillageSubsibyService.selectByPrimaryKey(cvs001);
        }
        return creditVillageSubsiby;
    }


    /**
     * 删除信用乡村信息  创业情况 以及补贴
     * @return
     */
    @RequestMapping("delCreditVillageSubsiby")
    @ResponseBody
    public String delCreditVillageSubsiby(HttpServletRequest request, String cvs001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(cvs001) && cvs001 != null){
            CreditVillageSubsiby creditVillageSubsiby = new CreditVillageSubsiby();
            creditVillageSubsiby = creditVillageSubsibyService.selectByPrimaryKey(cvs001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            creditVillageSubsiby.setDeleteby(fpUser.getRealname());
            creditVillageSubsiby.setDeletedate(new Date());
            creditVillageSubsiby.setAae100("0");
           s= creditVillageSubsibyService.updateByPrimaryKeySelective(creditVillageSubsiby);
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
