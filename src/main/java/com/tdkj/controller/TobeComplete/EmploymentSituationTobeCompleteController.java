package com.tdkj.controller.TobeComplete;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.EysPlf;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
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

/**
 * Created by len on 2019-05-27.
 * 劳动力就业待审核
 */
@Controller
@RequestMapping("EmploymentSituationTobeComplete")
public class EmploymentSituationTobeCompleteController {

    @Autowired
    private EmploymentSituationService employmentSituationService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力台j就业台账页面
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "tobecomplete/employment_situation_tobe_complete_list";
    }


    /**
     * 查询劳动力就业台账列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryEmploymentSituationAccountByAab301")
    @ResponseBody
    public String queryEmploymentSituationAccountByAab301(HttpServletRequest request, EysPlf eysPlf, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(eysPlf.getAab301()) && eysPlf.getAab301() != null) {
                aab301 = aab301Substr(eysPlf.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            eysPlf.setAab301(aab301);

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
            if (!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null) {
                eysPlf.setPlf004("%" + eysPlf.getPlf004() + "%");
            }

            eysPlf.setIstobecomplete("1");//待审核设置为1
            List<EysPlf> list = employmentSituationService.queryEmploymentSituationAccountByAab301(eysPlf, startRecord + "", pageSize + "");
            String countlist = employmentSituationService.queryAllEmploymentSituationByAab301(eysPlf);
            DataTableResultVO<EysPlf> result = new DataTableResultVO<>();
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
     * 根据劳动力id  删除就业信息
     *
     * @param request
     * @param eys002
     * @param modelMap
     * @return
     */
    @RequestMapping("deleteEmploymentSituationByEys002")
    @ResponseBody
    public String deleteEmploymentSituationByEys002(HttpServletRequest request, String eys002, ModelMap modelMap) {
        int s = 0;
        if (!"".equals(eys002) && eys002 != null) {
            FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
            employmentSituationService.deleteEmploymentSituationByEys002(eys002, fpUser.getRealname());
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
