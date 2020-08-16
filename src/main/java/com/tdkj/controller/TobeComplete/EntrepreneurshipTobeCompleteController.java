package com.tdkj.controller.TobeComplete;

import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.EppPlf;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.EntrepreneurshipService;
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
 * 劳动力自主创业  待完善
 */
@Controller
@RequestMapping("EntrepreneurshipTobeComplete")
public class EntrepreneurshipTobeCompleteController {

    @Autowired
    private EntrepreneurshipService entrepreneurshipService;

    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入劳动力台创业  待完善页面
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "tobecomplete/entrepreneur_ship_tobe_complete_list";
    }


    /**
     * 查询劳动力创业   待完善列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("queryEmploymentShipAccountByAab301")
    @ResponseBody
    public String queryEmploymentShipAccountByAab301(HttpServletRequest request, EppPlf eppPlf, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(eppPlf.getAab301()) && eppPlf.getAab301() != null) {
                aab301 = aab301Substr(eppPlf.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            eppPlf.setAab301(aab301);

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
            if (!"".equals(eppPlf.getPlf004()) && eppPlf.getPlf004() != null) {
                eppPlf.setPlf004("%" + eppPlf.getPlf004() + "%");
            }

            eppPlf.setIstobecomplete("1");//待完善设置为1
            List<EppPlf> list = entrepreneurshipService.queryEmploymentShipAccountByAab301(eppPlf, startRecord + "", pageSize + "");
            String countlist = entrepreneurshipService.queryAllEmploymentShipByAab301(eppPlf);
            DataTableResultVO<EppPlf> result = new DataTableResultVO<>();
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
     * 根据劳动力id  删除创业信息
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("deleteEntrepreneurshipByEpp002")
    @ResponseBody
    public String deleteEntrepreneurshipByEpp002(HttpServletRequest request, String epp002, ModelMap modelMap) {
        int s = 0;
        if (!"".equals(epp002) && epp002 != null) {
            FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
            entrepreneurshipService.deleteEntrepreneurshipByEpp002(epp002, fpUser.getRealname());
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
