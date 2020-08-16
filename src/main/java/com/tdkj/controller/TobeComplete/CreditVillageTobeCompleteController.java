package com.tdkj.controller.TobeComplete;

import com.tdkj.model.CreditVillage;
import com.tdkj.model.DataTableResultVO;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.CreditVillage.CreditVillageService;
import com.tdkj.service.Ztree.ZtreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 信用乡村待完善信息
 */

@RestController
@RequestMapping("CreditVillageToComplete")
public class CreditVillageTobeCompleteController {

    @Autowired
    private CreditVillageService creditVillageService;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 查询信用乡村页面待完善   列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "query", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String query(HttpServletRequest request, CreditVillage creditVillage, HttpServletResponse response) {
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if (!"".equals(creditVillage.getAab301()) && creditVillage.getAab301() != null) {
                aab301 = aab301Substr(creditVillage.getAab301()) + "%";
            } else {
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 = aab301Substr(aab301) + "%";
            }
            creditVillage.setAab301(aab301);

            //信用乡村名称
            if (!"".equals(creditVillage.getCvg002()) && creditVillage.getCvg002() != null) {
                creditVillage.setCvg002("%" + creditVillage.getCvg002() + "%");
            }

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

            creditVillage.setIstobecomplete("1");//待完善查询   设置该值为1  虚拟字段

            List<CreditVillage> list = creditVillageService.queryCreditVillageByAab301(creditVillage, startRecord + "", pageSize + "");
            String countlist = creditVillageService.queryAllByAab301(creditVillage);
            DataTableResultVO<CreditVillage> result = new DataTableResultVO<>();
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
}
