package com.tdkj.controller.TobeComplete;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.model.ThsPlf;
import com.tdkj.service.InformationCollection.TechnicalSchoolsService;
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

/**
 * Created by len on 2019-06-02.
 * 劳动力技工院 待完善
 */
@Controller
@RequestMapping("TechnicalSchoolTobeComplete")
public class TechnicalSchoolTobeCompleteController {
    @Autowired
    private TechnicalSchoolsService technicalSchoolsService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力技工院校   待完善页面
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "tobecomplete/technical_school_tobe_complete_list";
    }


    /**
     * 查询劳动力技工院校 待完善列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryTechnicalSchoolAccountByAab301")
    @ResponseBody
    public String queryTechnicalSchoolAccountByAab301(HttpServletRequest request, ThsPlf thsPlf, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(thsPlf.getAab301()) && thsPlf.getAab301() != null) {
                aab301 = aab301Substr(thsPlf.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            thsPlf.setAab301(aab301);
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
            if (!"".equals(thsPlf.getPlf004()) && thsPlf.getPlf004() != null) {
                thsPlf.setPlf004("%" + thsPlf.getPlf004() + "%");
            }

            thsPlf.setIstobecomplete("1");//待完善设置为1
            List<ThsPlf> list = technicalSchoolsService.queryTechnicalSchoolAccountByAab301(thsPlf, startRecord + "", pageSize + "");
            String countlist = technicalSchoolsService.queryAllTechnicalSchoolByAab301(thsPlf);
            DataTableResultVO<ThsPlf> result = new DataTableResultVO<>();
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
     * 根据劳动力id  删除技工院校信息
     *
     * @param request
     * @param eys002
     * @param modelMap
     * @return
     */
    @RequestMapping("deleteTechnicalSchoolsByThs002")
    @ResponseBody
    public String deleteTechnicalSchoolsByThs002(HttpServletRequest request, String ths002, ModelMap modelMap) {
        int s = 0;
        if (!"".equals(ths002) && ths002 != null) {
            FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
            technicalSchoolsService.deleteTechnicalSchoolsByThs002(ths002, fpUser.getRealname());
            s = 1;
        }
        if (s > 0) {
            return "yes";
        } else {
            return "no";
        }
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
}
