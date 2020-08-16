package com.tdkj.controller.ParamController;

import com.tdkj.model.Aa10;
import com.tdkj.model.CentralizedSettlements;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.Param.CentralizedSettlementsService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.EHCacheUtils;
import net.sf.ehcache.CacheManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 2019-04-28.
 */
@Controller
@RequestMapping("aa10")
public class Aa10Controller {

    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    private CentralizedSettlementsService centralizedSettlementsService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("queryAa10")
    @ResponseBody
    public List<Aa10> queryAa10(HttpServletRequest request, String aaa100, HttpServletResponse response){
        List<Aa10> list = null;
        if(!"".equals(aaa100) && aaa100 != null){

            //list = (List<Aa10>)EHCacheUtils.getCache(cacheManager,"list");//从缓存中获取数据
            // if(list == null){
            list= aa10Service.queryAa10ByAaa100(aaa100);
            //   EHCacheUtils.setCache(cacheManager,"list",list);//放入缓存
        }
        return list;
        // }else{
        //     return null;
        //  }
    }


    /**
     * 将aa10的值放在缓存中
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryAllAa10list")
    @ResponseBody
    public List<Aa10> queryAllAa10list(HttpServletRequest request, HttpServletResponse response){
        List<Aa10> list = null;
        list = (List<Aa10>)EHCacheUtils.getCache(cacheManager,"ss");//从缓存中获取数据
        if(list == null){
            list= aa10Service.queryAllAa10list();
            EHCacheUtils.setCache(cacheManager,"ss",list);//放入缓存
        }
        return list;

    }



    /**
     * 根据aab301 查询安置点名称
     * @param request
     * @param aab301
     * @param response
     * @return
     */
    @RequestMapping("queryCentralizedSettlementsByAab301")
    @ResponseBody
    public List<CentralizedSettlements> queryCentralizedSettlementsByAab301(HttpServletRequest request, String aab301, HttpServletResponse response){
        if(!"".equals(aab301) && aab301 != null){
            PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
            if(aab301.equals("610180000000")){
                aab301=aab301.substring(0, 5)+"%";;
            }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
                String type=poorXzqh.getType();
                if(type.equals("1")){
                    aab301=aab301.substring(0, 2)+"%";
                }else if(type.equals("2")){
                    aab301=aab301.substring(0, 4)+"%";
                }else if(type.equals("3")){
                    aab301=aab301.substring(0, 6)+"%";
                }else if(type.equals("4")){
                    aab301=aab301.substring(0, 6)+"%";
                }else if(type.equals("5")){
                    aab301=aab301.substring(0, 6)+"%";
                }
            }
            // List<CentralizedSettlements> listeach  = (List<CentralizedSettlements>)EHCacheUtils.getCache(cacheManager,"op");
            //if(listeach == null){
            List<CentralizedSettlements>  listeach = centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301);
            //EHCacheUtils.setCache(cacheManager,"op",listeach);
            //  }
            return listeach;
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            aab301 = fpUser.getAab301();
            PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
            if(aab301.equals("610180000000")){
                aab301=aab301.substring(0, 5);
            }else  if(poorXzqh!=null && !"".equals(poorXzqh)) {
                String type = poorXzqh.getType();
                if (type.equals("1")) {
                    aab301 = aab301.substring(0, 2);
                } else if (type.equals("2")) {
                    aab301 = aab301.substring(0, 4);
                } else if (type.equals("3")) {
                    aab301 = aab301.substring(0, 6);
                } else if (type.equals("4")) {
                    aab301 = aab301.substring(0, 6);
                } else if (type.equals("5")) {
                    aab301 = aab301.substring(0, 6);
                }
            }
            aab301 =  aab301Substr(aab301)+ "%";

            //List<CentralizedSettlements> listeach  = (List<CentralizedSettlements>)EHCacheUtils.getCache(cacheManager,"op");
            //if(listeach == null){
            List<CentralizedSettlements>    listeach = centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301);
            // EHCacheUtils.setCache(cacheManager,"op",listeach);
            // }
            return listeach;
        }
    }


    /**
     * 根据aab301 查询安置点名称
     * 保存，修改  安置点 刷新安置点缓存
     * @param request
     * @param aab301
     * @param response
     * @return
     */
    @RequestMapping("refreshCentralizedSettlementsByAab301")
    @ResponseBody
    public List<CentralizedSettlements> refreshCentralizedSettlementsByAab301(HttpServletRequest request, String aab301, HttpServletResponse response){
        if(!"".equals(aab301) && aab301 != null){
            PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
            if(aab301.equals("610180000000")){
                aab301=aab301.substring(0, 5)+"%";;
            }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
                String type=poorXzqh.getType();
                if(type.equals("1")){
                    aab301=aab301.substring(0, 2)+"%";
                }else if(type.equals("2")){
                    aab301=aab301.substring(0, 4)+"%";
                }else if(type.equals("3")){
                    aab301=aab301.substring(0, 6)+"%";
                }else if(type.equals("4")){
                    aab301=aab301.substring(0, 6)+"%";
                }else if(type.equals("5")){
                    aab301=aab301.substring(0, 6)+"%";
                }
            }
            // List<CentralizedSettlements> listeach  = (List<CentralizedSettlements>)EHCacheUtils.getCache(cacheManager,"op");
            //if(listeach == null){
            List<CentralizedSettlements>  listeach = centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301);
            EHCacheUtils.setCache(cacheManager,"op",listeach);
            //  }
            return listeach;
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            aab301 = fpUser.getAab301();
            PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
            if(aab301.equals("610180000000")){
                aab301=aab301.substring(0, 5);
            }else  if(poorXzqh!=null && !"".equals(poorXzqh)) {
                String type = poorXzqh.getType();
                if (type.equals("1")) {
                    aab301 = aab301.substring(0, 2);
                } else if (type.equals("2")) {
                    aab301 = aab301.substring(0, 4);
                } else if (type.equals("3")) {
                    aab301 = aab301.substring(0, 6);
                } else if (type.equals("4")) {
                    aab301 = aab301.substring(0, 6);
                } else if (type.equals("5")) {
                    aab301 = aab301.substring(0, 6);
                }
            }
            aab301 =  aab301Substr(aab301)+ "%";

            //List<CentralizedSettlements> listeach  = (List<CentralizedSettlements>)EHCacheUtils.getCache(cacheManager,"op");
            //if(listeach == null){
            List<CentralizedSettlements>    listeach = centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301);
            // EHCacheUtils.setCache(cacheManager,"op",listeach);
            // }
            return listeach;
        }
    }



    //获取aab301的截取
    public  String aab301Substr(String aab301){
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else   if(poorXzqh!=null && !"".equals(poorXzqh)){
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
