package com.tdkj.controller.Information;

import com.tdkj.model.*;
import com.tdkj.service.Information.NewSushanTrainingService;
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
 * 新苏陕协作培训
 */
@Controller
@RequestMapping("newSushanTraining")
public class NewSuShanTrainingController {

    @Autowired
    private NewSushanTrainingService newSushanTrainingService;

    @Autowired
    private ZtreeService ztreeService;

    /**
     * 苏陕合作培训台账
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "accountInformation/new_sushan_training_account_list";
    }


    /**
     * 保存新苏陕协作培训信息
     * @param request
     * @param modelMap
     * @param newSushanTraining
     * @return
     */
    @RequestMapping("saveNewSushanTraining")
    @ResponseBody
    public String saveNewSushanTraining(HttpServletRequest request, ModelMap modelMap, NewSushanTraining newSushanTraining){
        String flag = "";
        if(newSushanTraining != null){
            if(!"".equals(newSushanTraining.getNst001()) && newSushanTraining.getNst001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    newSushanTraining.setAae012(fpUser.getId());
                    newSushanTraining.setUpdateby(fpUser.getRealname());
                    newSushanTraining.setUpdatedate(new Date());
                }
                newSushanTrainingService.updateByPrimaryKeySelective(newSushanTraining);
                flag = "update";
            }else{
                newSushanTraining.setNst001(UUIDGenerator.generate().toString());
                newSushanTraining.setAae036(new Date());
                newSushanTraining.setDatasource("1");//1代表录入，2代表导入
                newSushanTraining.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    newSushanTraining.setAae011(fpUser.getId());
                    newSushanTraining.setCreateby(fpUser.getRealname());
                    if(!"".equals(newSushanTraining.getAab301()) && newSushanTraining.getAab301() != null){
                    }else{
                        newSushanTraining.setAab301(fpUser.getAab301());
                    }
                }
                newSushanTrainingService.insertSelective(newSushanTraining);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改新苏陕协作培训信息
     * @return
     */
    @RequestMapping("updateNewSushanTraining")
    @ResponseBody
    public NewSushanTraining updateNewSushanTraining(HttpServletRequest request, String nst001,ModelMap modelMap){
        NewSushanTraining newSushanTraining = null;
        if(!"".equals(nst001) && nst001 != null){
            newSushanTraining = newSushanTrainingService.selectByPrimaryKey(nst001);
        }
        return newSushanTraining;
    }


    /**
     * 删除新苏陕协作培训信息
     * @return
     */
    @RequestMapping("delNewSushanTraining")
    @ResponseBody
    public String delNewSushanTraining(HttpServletRequest request, String nst001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(nst001) && nst001 != null){
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            NewSushanTraining newSushanTraining = new NewSushanTraining();
            newSushanTraining = newSushanTrainingService.selectByPrimaryKey(nst001);
            newSushanTraining.setDeleteby(fpUser.getRealname());
            newSushanTraining.setDeletedate(new Date());
            newSushanTraining.setAae100("0");
            s = newSushanTrainingService.updateByPrimaryKeySelective(newSushanTraining);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据aab301  查询新苏陕协作培训列表
     */
    @RequestMapping("queryNewSushanTrainingByAab301")
    @ResponseBody
    public String queryNewSushanTrainingByAab301(HttpServletRequest request,NewSushanTraining newSushanTraining){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(newSushanTraining.getAab301()) && newSushanTraining.getAab301() != null){
                aab301 =  aab301Substr(newSushanTraining.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            newSushanTraining.setAab301(aab301);

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

            List<NewSushanTraining> list= newSushanTrainingService.queryNewSushanTrainingByAab301(newSushanTraining,startRecord+"",pageSize+"");
            String countlist = newSushanTrainingService.queryAllNewSushanTrainingCountByAab301(newSushanTraining);
            DataTableResultVO<NewSushanTraining> result=new DataTableResultVO<>();
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
