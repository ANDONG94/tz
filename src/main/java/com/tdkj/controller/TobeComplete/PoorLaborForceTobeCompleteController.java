package com.tdkj.controller.TobeComplete;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.*;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hedd on 2019/5/25.
 * 待完善劳动力
 */
@Controller
@RequestMapping("PoorLaborForceComplete")
public class PoorLaborForceTobeCompleteController {
    @Autowired
    private PoorLaborForceService poorLaborForceService;
    @Autowired
    private EmploymentSituationService employmentSituationService;
    @Autowired
    private EntrepreneurshipService entrepreneurshipService;
    @Autowired
    private TrainingSituationService trainingSituationService;
    @Autowired
    private TechnicalSchoolsService technicalSchoolsService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力   待完善页面
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "tobecomplete/poor_labor_force_tobe_complete_list";
    }


    /**
     * 查询劳动力   待完善列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryPoorLaborForceAccountByAab301")
    @ResponseBody
    public String queryPoorLaborForceAccountByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null) {
                aab301 = aab301Substr(poorLaborForce.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            poorLaborForce.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start) + 1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex) + 1) * pageSize;
            }
            if (!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null) {
                poorLaborForce.setPlf004("%" + poorLaborForce.getPlf004() + "%");
            }

            poorLaborForce.setIstobecomplete("1");//待完善 设置值为1

            List<PoorLaborForce> list = poorLaborForceService.queryPoorLaborForceAccountByAab301(poorLaborForce, startRecord + "", pageSize + "");
            String countlist = poorLaborForceService.queryAllPoorLaborForceAccoutByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result = new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 进入重复劳动力确认  页面
     *
     * @return
     */
    @RequestMapping("repeatWorklist")
    public String repeatWorklist() {
        return "tobecomplete/poor_labor_force_repeat_list";
    }


    /**
     * 查询劳动力   重复劳动力确认   列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryRepeatPoorLaborForceByAab301")
    @ResponseBody
    public String queryRepeatPoorLaborForceByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null) {
                aab301 = aab301Substr(poorLaborForce.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            poorLaborForce.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start) + 1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex) + 1) * pageSize;
            }
            if (!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null) {
                poorLaborForce.setPlf004("%" + poorLaborForce.getPlf004() + "%");
            }

            List<PoorLaborForce> list = poorLaborForceService.queryRepeatPoorLaborForceByAab301(poorLaborForce, startRecord + "", pageSize + "");
            String countlist = poorLaborForceService.queryRepeatPoorLaborForceAccoutByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result = new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //获取aab301的截取
    public String aab301Substr(String aab301) {
        PoorXzqh poorXzqh = ztreeService.queryPoorXzqhSub(aab301);
        if (aab301.equals("610180000000")) {
            aab301 = aab301.substring(0, 5);
        } else if (poorXzqh != null && !"".equals(poorXzqh)) {
            String type = poorXzqh.getType();
            if (type.equals("1")) {
                aab301 = aab301.substring(0, 2);
            } else if (type.equals("2")) {
                aab301 = aab301.substring(0, 4);
            } else if (type.equals("3")) {
                aab301 = aab301.substring(0, 6);
            } else if (type.equals("4")) {
                aab301 = aab301.substring(0, 9);
            } else if (type.equals("5")) {
                aab301 = aab301;
            }
        }
        return aab301;
    }

    /**
     * 批量   删除劳动力信息
     *
     * @return
     */
    @RequestMapping("delAllWorkerByIds")
    @ResponseBody
    public String delAllPoorWorkByIds(HttpServletRequest request, @RequestParam(value = "plf001s[]") String[] plf001s, ModelMap modelMap) {
        int s = 0;
        FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
        if (!"".equals(plf001s) && plf001s != null) {
            //循环删除劳动力
            for (int i = 0; i < plf001s.length; i++) {
                employmentSituationService.deleteEmploymentSituationByEys002(plf001s[i], fpUser.getRealname());
                entrepreneurshipService.deleteEntrepreneurshipByEpp002(plf001s[i], fpUser.getRealname());
                trainingSituationService.deleteTrainingSituationByTsn010(plf001s[i], fpUser.getRealname());
                technicalSchoolsService.deleteTechnicalSchoolsByThs002(plf001s[i], fpUser.getRealname());
                poorLaborForceService.deleteByPrimaryKey(plf001s[i]);//删除劳动力
                s++;
            }
        }
        if (s > 0) {
            return "yes";
        } else {
            return "no";
        }
    }

    /**
     * 进入待确认劳动力页面
     *
     * @return
     */
    @RequestMapping("tobecomfirelist")
    public String tobecomfirelist() {
        return "tobecomfire/poor_labor_force_tobe_comfire_list";
    }


    /**
     * 查询待确认劳动力列表
     *
     * @param response
     * @return
     */
    @RequestMapping("queryPoorLaborForceToBeComfireByAab301")
    @ResponseBody
    public String queryPoorLaborForceToBeComfireByAab301(HttpServletRequest request, PoorLaborForce poorLaborForce, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null) {
                aab301 = aab301Substr(poorLaborForce.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            poorLaborForce.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start) + 1;
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex) + 1) * pageSize;
            }
            if (!"".equals(poorLaborForce.getPlf004()) && poorLaborForce.getPlf004() != null) {
                poorLaborForce.setPlf004("%" + poorLaborForce.getPlf004() + "%");
            }

            List<PoorLaborForce> list = poorLaborForceService.queryPoorLaborForceToBeComfireByAab301(poorLaborForce, startRecord + "", pageSize + "");
            String countlist = poorLaborForceService.queryAllPoorLaborForceToBeComfireByAab301(poorLaborForce);
            DataTableResultVO<PoorLaborForce> result = new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
