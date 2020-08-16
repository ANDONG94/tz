package com.tdkj.controller.FenhuHehu;

import com.tdkj.model.*;
import com.tdkj.service.CreditVillage.CreditVillageService;
import com.tdkj.service.FenhuHehu.SplitAndPoorService;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.InformationCollection.PoorWorkService;
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
 * Created by hedd on 2019/10/9.
 * 分户合户
 */
@Controller
@RequestMapping("splitAndPoor")
public class SplitAndPoorController {

    @Autowired
    private SplitAndPoorService splitAndPoorService;
    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private PoorLaborForceService poorLaborForceService;
    @Autowired
    private ZtreeService ztreeService;


    /**
     * 进入分户合户页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "FenhuHehu/split_and_poor_list";
    }


    /**
     * 查询   分户列表  页面列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("querySplitAndPoorByAab301")
    @ResponseBody
    public String querySplitAndPoorByAab301(HttpServletRequest request, SplitAndPoor splitAndPoor, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 ="";
            if(!"".equals(splitAndPoor.getAab301()) && splitAndPoor.getAab301() != null){
                aab301 =  aab301Substr(splitAndPoor.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            splitAndPoor.setAab301(aab301);

            /*//名称
            if(!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null){
                eysPlf.setPlf004("%"+eysPlf.getPlf004()+"%");
            }*/

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

            List<SplitAndPoor> list= splitAndPoorService.querySplitAndPoorByAab301(splitAndPoor,startRecord+"",pageSize+"");
            String countlist= splitAndPoorService.queryAllSplitAndPoorByAab301(splitAndPoor);
            DataTableResultVO<SplitAndPoor> result=new DataTableResultVO<>();
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
     * 根据 贫困户身份证号  查询该贫困户下的 劳动力 列表
     */
    @RequestMapping("queryWorkerByPhd003")
    @ResponseBody
    public List<PoorLaborForce> queryWorkerByPhd003(HttpServletRequest request,String phd003){
        List<PoorLaborForce> list = null;
        if(!"".equals(phd003) && phd003 != null){
            PoorHouseholds poorHouseholds = poorWorkService.selectByPrimaryidCard(phd003);
            if(poorHouseholds != null){
                String phd001 = poorHouseholds.getPhd001();//获取贫困户的id
                //根据贫困户id  查询该户下面的劳动力
                list = poorLaborForceService.queryPoorLaborForceByPoorWork(phd001);
            }
        }
        return  list;
    }


    /**
     * 分户合户    劳动力移动
     * @return
     */
    @RequestMapping("removeWorker")
    @ResponseBody
    public String removeWorker(HttpServletRequest request, String plf001,String phd003_old,String phd003_new,ModelMap modelMap){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(plf001) && plf001 != null){
            //根据plf001 获取到劳动力对象
            PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(plf001);
            //获取到新户主的id
            PoorHouseholds poorHouseholds = poorWorkService.selectByPrimaryidCard(phd003_new);
            //将劳动力的户主和 aab301 进行变更
            poorLaborForce.setPlf002(poorHouseholds.getPhd001());
            poorLaborForce.setAab301(poorHouseholds.getAab301());
            //保存劳动力，完成劳动力的户变更
            poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);

            //保存变更记录到变更记录表
            SplitAndPoor splitAndPoor = new SplitAndPoor();
            //获取到原劳动力的户
            PoorHouseholds phdold = poorWorkService.selectByPrimaryidCard(phd003_old);
            splitAndPoor.setSap001(UUIDGenerator.generate().toString());
            splitAndPoor.setAae100("1");
            splitAndPoor.setAae036(new Date());
            splitAndPoor.setDatasource("1");
            splitAndPoor.setAae100("1");
            splitAndPoor.setSap002(phdold.getPhd002());//原户主名称
            splitAndPoor.setSap003(poorHouseholds.getPhd002());//新户主名称
            splitAndPoor.setSap004(phdold.getPhd003());//原户主身份证号
            splitAndPoor.setSap005(poorHouseholds.getPhd003());//新户主身份证号
            splitAndPoor.setSap006(poorLaborForce.getPlf004());//劳动力名称
            splitAndPoor.setSap007(poorLaborForce.getPlf005());//劳动力身份证号
            splitAndPoor.setOldaab301(phdold.getAab301());//原始户主aab301
            splitAndPoor.setNewaab301(poorHouseholds.getAab301());//新户主aab301
            splitAndPoor.setOldpoorid(phdold.getPhd001());//原始户id
            splitAndPoor.setNewpoorid(poorHouseholds.getPhd001());//新户主id
            if(request.getSession().getAttribute("fpUser") != null){
                splitAndPoor.setAae011(fpUser.getId());
                splitAndPoor.setCreateby(fpUser.getRealname());
                splitAndPoor.setAab301(fpUser.getAab301());
            }
            s = splitAndPoorService.insertSelective(splitAndPoor);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }




    /**
     *  点击设置为新户主   的操作
     * @return
     */
    @RequestMapping("createNewPoor")
    @ResponseBody
    public String createNewPoor(HttpServletRequest request, String plf001,String phd003_old,ModelMap modelMap){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(plf001) && plf001 != null){
            //根据plf001 获取到劳动力对象
            PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(plf001);
            //将劳动力对象设置为新的户主
            PoorHouseholds poor = new PoorHouseholds();
            poor.setPhd001(UUIDGenerator.generate().toString());

            poor.setPhd002(poorLaborForce.getPlf004());//姓名
            poor.setPhd003(poorLaborForce.getPlf005());//身份证号码
            poor.setPhd004(poorLaborForce.getPlf007());//性别
            poor.setPhd005(poorLaborForce.getPlf008());//年龄
            poor.setPhd006(poorLaborForce.getPlf006());//电话号码
            //根据原户主身份证号码查询户主信息
            //获取到原户主的id
            PoorHouseholds poorold = poorWorkService.selectByPrimaryidCard(phd003_old);
            poor.setPhd007(poorold.getPhd007());//家庭住址
            poor.setAab301(poorold.getAab301());
            poor.setPhd012(poorold.getPhd012());//年度
            poor.setPhd010(poorold.getPhd010());//集中安置点名称
            poor.setPhd013(poorold.getPhd013());//易地搬迁
            poor.setPhd014(poorold.getPhd014());//脱贫状态
            poor.setPhd015(poorold.getPhd015());//贫困户属性
            poor.setDatasource(poorold.getDatasource());//数据来源,1录入，2导入
            poor.setAae100("1");//数据来源,1录入，2导入
            poor.setPhd016(poorLaborForce.getPlf023());//是否残疾
            poor.setAae036(new Date());
            poor.setPhd008(1);//劳动力数量
            if(request.getSession().getAttribute("fpUser") != null){
                poor.setAae011(fpUser.getId());
                poor.setCreateby(fpUser.getRealname());
                poor.setAab301(fpUser.getAab301());
            }
            poorWorkService.insertSelective(poor);//保存新的户主信息
            poorold.setPhd008(poorold.getPhd008()-1);
            poorWorkService.updateByPrimaryKeySelective(poorold);//保存就户主信息
            poorLaborForce.setPlf002(poor.getPhd001());//更新劳动力的户主信息
            poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);//更新劳动力信息

            //保存变更记录到变更记录表
            SplitAndPoor splitAndPoor = new SplitAndPoor();
            splitAndPoor.setSap001(UUIDGenerator.generate().toString());
            splitAndPoor.setAae100("1");
            splitAndPoor.setAae036(new Date());
            splitAndPoor.setDatasource("1");
            splitAndPoor.setAae100("1");
            splitAndPoor.setSap002(poorold.getPhd002());//原户主名称
            splitAndPoor.setSap003(poor.getPhd002());//新户主名称
            splitAndPoor.setSap004(poorold.getPhd003());//原户主身份证号
            splitAndPoor.setSap005(poor.getPhd003());//新户主身份证号
            splitAndPoor.setSap006(poorLaborForce.getPlf004());//劳动力名称
            splitAndPoor.setSap007(poorLaborForce.getPlf005());//劳动力身份证号
            splitAndPoor.setOldaab301(poorold.getAab301());//原始户主aab301
            splitAndPoor.setNewaab301(poor.getAab301());//新户主aab301
            splitAndPoor.setOldpoorid(poorold.getPhd001());//原始户id
            splitAndPoor.setNewpoorid(poor.getPhd001());//新户主id
            if(request.getSession().getAttribute("fpUser") != null){
                splitAndPoor.setAae011(fpUser.getId());
                splitAndPoor.setCreateby(fpUser.getRealname());
                splitAndPoor.setAab301(fpUser.getAab301());
            }
            s = splitAndPoorService.insertSelective(splitAndPoor);
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
