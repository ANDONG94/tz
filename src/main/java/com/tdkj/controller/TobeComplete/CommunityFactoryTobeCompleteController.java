package com.tdkj.controller.TobeComplete;

import com.tdkj.model.CommunityFactory;
import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.Relation.RelationService;
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
 * Created by hedd on 2019/6/9.
 * 社区工厂台账
 */
@Controller
@RequestMapping("CommunityFactoryComplete")
public class CommunityFactoryTobeCompleteController {
    @Autowired
    private CommunityFactoryService communityFactoryService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private RelationService relationService;


    /**
     * 查询社区工厂台账列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "tobecomplete/community_factory_tobe_complete_list";
    }

    /**
     * 查询社区工厂台账列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryCommunityFactoryByAab301")
    @ResponseBody
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
            communityFactory.setIstaizahang("1");//判断是不是台账进来的
            communityFactory.setTaizhang("1");//判断是不是台账进来的

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
            communityFactory.setIstobecomplete("1");//待完善设置为1
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
     * 查询  待完善   社区工厂列表
     * @return
     */
    @RequestMapping("tobecompletelist")
    public String tobecompletelist(){
        return "tobecomplete/community_factory_tobe_complete_list";
    }

    /**
     * 查询    待完善      社区工厂  列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("querytobeCompleteFactoryByAab301")
    @ResponseBody
    public String querytobeCompleteFactoryByAab301(HttpServletRequest request, CommunityFactory communityFactory, HttpServletResponse response){
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

            List<CommunityFactory> list= communityFactoryService.querytobeCompleteFactoryByAab301(communityFactory,startRecord+"",pageSize+"");
            String countlist = communityFactoryService.queryAlltobeCompleteFactoryByAab301(communityFactory);
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
