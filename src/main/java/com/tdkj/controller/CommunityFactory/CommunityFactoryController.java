package com.tdkj.controller.CommunityFactory;

import com.tdkj.model.*;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.Relation.RelationService;
import com.tdkj.service.Subsidy.CommunityFactorySubsidyService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2019-04-29.
 * 社区工厂信息
 */
@RestController
@RequestMapping("CommunityFactory")
public class CommunityFactoryController {

    @Autowired
    private CommunityFactoryService communityFactoryService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private CommunityFactorySubsidyService communityFactorySubsidyService;

    /**
     * 查询社区工厂列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryCommunityFactoryByAab301")
    public String queryCommunityFactoryByAab301(HttpServletRequest request, CommunityFactory communityFactory, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(communityFactory.getAab301()) && communityFactory.getAab301() != null){
                aab301 =  aab301Substr(communityFactory.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            communityFactory.setAab301(aab301);

            if(!"".equals(communityFactory.getCtf002()) && communityFactory.getCtf002() != null){
                communityFactory.setCtf002("%" +communityFactory.getCtf002() +"%");
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

            List<CommunityFactory> list= communityFactoryService.queryCommunityFactoryByAab301(communityFactory,startRecord+"",pageSize+"");
            String countlist = communityFactoryService.queryAllByAab301(communityFactory);
            DataTableResultVO<CommunityFactory> result=new DataTableResultVO<>();
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
     * 保存社区工厂信息
     * @return
     */
    @RequestMapping("saveFactory")
    public String saveFactory(HttpServletRequest request, ModelMap modelMap, CommunityFactory communityFactory){
        String flag = "";
        if(communityFactory != null){
            if(!"".equals(communityFactory.getCtf001()) && communityFactory.getCtf001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    communityFactory.setAae012(fpUser.getId());
                    communityFactory.setUpdateby(fpUser.getRealname());
                    communityFactory.setUpdatedate(new Date());
                }
                communityFactoryService.updateByPrimaryKeySelective(communityFactory);
                flag = "update";
            }else{
                communityFactory.setCtf001(UUIDGenerator.generate().toString());
                communityFactory.setAae036(new Date());
                communityFactory.setDatasource("1");//1代表录入，2代表导入
                communityFactory.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    communityFactory.setAae011(fpUser.getId());
                    communityFactory.setCreateby(fpUser.getRealname());
                    if(!"".equals(communityFactory.getAab301()) && communityFactory.getAab301() != null){
                    }else{
                        communityFactory.setAab301(fpUser.getAab301());
                    }
                }
                communityFactoryService.insertSelective(communityFactory);
                flag = communityFactory.getCtf001();

            }
        }
        return flag;

    }


    /**
     * 修改社区工厂信息
     * @return
     */
    @RequestMapping("updateFactory")
    public CommunityFactory updateFactory(HttpServletRequest request, String ctf001,ModelMap modelMap){
        CommunityFactory communityFactory = null;
        if(!"".equals(ctf001) && ctf001 != null){
            communityFactory = communityFactoryService.selectByPrimaryKey(ctf001);
        }
        return communityFactory;
    }


    /**
     * 删除社区工厂信息
     * @return
     */
    @RequestMapping("delFactory")
    public String delFactory(HttpServletRequest request, String ctf001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(ctf001) && ctf001 != null){
            relationService.updateByCompanyid(ctf001);
            CommunityFactory communityFactory = new CommunityFactory();
            communityFactory = communityFactoryService.selectByPrimaryKey(ctf001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            communityFactory.setDeleteby(fpUser.getRealname());
            communityFactory.setDeletedate(new Date());
            communityFactoryService.updateByPrimaryKeySelective(communityFactory);
            s = communityFactoryService.deleteByPrimaryKey(ctf001);

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


    /********************************以下是补贴信息***************************************************************************************/

    /**
     * 根据社区工厂id  查询补贴
     */
    @RequestMapping("querCommunityFactorySubsidyByCfs002")
    @ResponseBody
    public List<CommunityFactorySubsidy> querCommunityFactorySubsidyByCfs002(HttpServletRequest request,String cfs002){
        List<CommunityFactorySubsidy> list = null;
        if(!"".equals(cfs002) && cfs002 != null){
            list = communityFactorySubsidyService.querCommunityFactorySubsidyByCfs002(cfs002);
        }
        return  list;
    }


    /**
     * 保存补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveCommunityFactorySubsidy")
    @ResponseBody
    public String saveCommunityFactorySubsidy(HttpServletRequest request,ModelMap modelMap,String aab301,CommunityFactorySubsidy communityFactorySubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(communityFactorySubsidy != null){
            if(!"".equals(communityFactorySubsidy.getCfs001()) && communityFactorySubsidy.getCfs001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    communityFactorySubsidy.setAae012(fpUser.getId());
                    communityFactorySubsidy.setUpdateby(fpUser.getRealname());
                    communityFactorySubsidy.setUpdatedate(new Date());
                }

                communityFactorySubsidyService.updateByPrimaryKeySelective(communityFactorySubsidy);

                flag = "update";
            }else{
                communityFactorySubsidy.setCfs001(UUIDGenerator.generate().toString());
                communityFactorySubsidy.setAae036(new Date());
                communityFactorySubsidy.setDatasource("1");
                communityFactorySubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    communityFactorySubsidy.setAae011(fpUser.getId());
                    communityFactorySubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(aab301) && aab301 != null){
                        communityFactorySubsidy.setAab301(aab301);
                    }else{
                        communityFactorySubsidy.setAab301(fpUser.getAab301());
                    }
                }

                communityFactorySubsidyService.insertSelective(communityFactorySubsidy);
                flag = communityFactorySubsidy.getCfs001();
            }
        }
        //根据社区工厂id  更新社区工厂中的主字段  ctf014以工代训补贴   ctf012享受租赁水电补贴  ctf013享受一次性岗位补贴
        String cfs002 = communityFactorySubsidy.getCfs002();//社区工厂id
        CommunityFactorySubsidy communitySubsidy = communityFactorySubsidyService.selectSubsibyByCfs002(cfs002);
        CommunityFactory communityFactory = communityFactoryService.selectByPrimaryKey(cfs002);
        if(communitySubsidy != null){
            communityFactory.setCtf014(communitySubsidy.getCfs005());
            communityFactory.setCtf012(communitySubsidy.getCfs006());
            communityFactory.setCtf013(communitySubsidy.getCfs007());
            communityFactoryService.updateByPrimaryKeySelective(communityFactory);
        }
        return flag;
    }


    /**
     * 修改补贴信息
     * @return
     */
    @RequestMapping("updateCommunityFactorySubsidy")
    @ResponseBody
    public CommunityFactorySubsidy updateCommunityFactorySubsidy(HttpServletRequest request,String cfs001,ModelMap modelMap){
        CommunityFactorySubsidy communityFactorySubsidy = null;
        if(!"".equals(cfs001) && cfs001 != null){
            communityFactorySubsidy = communityFactorySubsidyService.selectByPrimaryKey(cfs001);
        }
        return communityFactorySubsidy;
    }

    /**
     * 根据  补贴 工厂id
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteCommunityFactorySubsidy")
    @ResponseBody
    public String deleteCommunityFactorySubsidy(HttpServletRequest request,String cfs001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(cfs001) && cfs001 != null){
            s = communityFactorySubsidyService.deleteByPrimaryKey(cfs001,fpUser.getRealname());
        }
        if(s > 0 ){
            //根据社区工厂id  更新社区工厂中的主字段  ctf014以工代训补贴   ctf012享受租赁水电补贴  ctf013享受一次性岗位补贴
            CommunityFactorySubsidy communityFactorySubsidy =communityFactorySubsidyService.selectByPrimaryKey(cfs001);
            String cfs002 = communityFactorySubsidy.getCfs002();//社区工厂id
            CommunityFactorySubsidy communitySubsidy = communityFactorySubsidyService.selectSubsibyByCfs002(cfs002);
            CommunityFactory communityFactory = communityFactoryService.selectByPrimaryKey(cfs002);
            if(communitySubsidy != null){
                communityFactory.setCtf014(communitySubsidy.getCfs005());
                communityFactory.setCtf012(communitySubsidy.getCfs006());
                communityFactory.setCtf013(communitySubsidy.getCfs007());
                communityFactoryService.updateByPrimaryKeySelective(communityFactory);
            }
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据工厂 id
     * 删除贴信息
     * @return
     */
    @RequestMapping("deleteCommunityFactorySubsidyByCfs002")
    @ResponseBody
    public String deleteCommunityFactorySubsidyByCfs002(HttpServletRequest request,String cfs002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(cfs002) && cfs002 != null){
            s = communityFactorySubsidyService.deleteCommunityFactorySubsidyByCfs002(cfs002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


}
