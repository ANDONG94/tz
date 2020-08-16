package com.tdkj.controller.InformationCollection;

import com.tdkj.dto.message;
import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.*;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.Subsidy.EmploymentSubsidyService;
import com.tdkj.service.Subsidy.TechnicalSchoolSubsidyService;
import com.tdkj.service.Subsidy.TrainingSubsidyService;
import com.tdkj.service.Subsidy.VentureSubsidyService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.DateUtil;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
@Controller
@RequestMapping("poorwork")
public class PoorWorkController {

    @Autowired
    private PoorWorkService poorWorkService;
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
    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    private PoorChangeRecordService poorChangeRecordService;
    @Autowired
    private EmploymentSubsidyService employmentSubsidyService;
    @Autowired
    private VentureSubsidyService ventureSubsidyService;
    @Autowired
    private TrainingSubsidyService trainingSubsidyService;
    @Autowired
    private TechnicalSchoolSubsidyService technicalSchoolSubsidyService;


    /**
     * 点击贫困劳动力菜单 进入贫困劳动力页面
     */
    @RequestMapping("list")
    public String list(){
        return "InformationPw/PoorWork";
    }


    @RequestMapping("query")
    @ResponseBody
    public String queryPoorWorkList(HttpServletRequest request,PoorHouseholds poorHouseholds, HttpServletResponse response){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(poorHouseholds.getAab301()) && poorHouseholds.getAab301() != null){
                aab301 =  aab301Substr(poorHouseholds.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorHouseholds.setAab301(aab301);

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
            if(!"".equals(poorHouseholds.getPhd002()) && poorHouseholds.getPhd002() != null){
                poorHouseholds.setPhd002("%"+poorHouseholds.getPhd002()+"%");
            }
            if(!"".equals(poorHouseholds.getPhd003()) && poorHouseholds.getPhd003() != null){
                poorHouseholds.setPhd003(poorHouseholds.getPhd003().replaceAll(" ", ""));
            }
            if(!"".equals(poorHouseholds.getPlf004()) && poorHouseholds.getPlf004() != null){
                poorHouseholds.setPhd003(poorHouseholds.getPlf004().replaceAll(" ", "").replaceAll("%",""));
            }
            List<PoorHouseholds> list= poorWorkService.queryAllPoorWorkByAab301(poorHouseholds,startRecord+"",pageSize+"");
            String countlist = poorWorkService.queryAllByAab301(poorHouseholds);
            DataTableResultVO<PoorHouseholds> result=new DataTableResultVO<>();
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
     * 贫困户的导出  excel
     * @param
     * @param response
     */
    @RequestMapping(value = "/exporpoorwork")
    public void ExportBankCheckInfo(HttpServletRequest request,String treeid,String phd002_scan,String phd003_scan,String phd012_scan, HttpServletResponse response) throws Exception {

       // excelService.export(treeid,phd002_scan,phd003_scan,phd012_scan,response);
/*
        List<PoorHouseholds> errLst = poorWorkService.queryAllPoorWorkByAab301("6101%","","","","0","100");
*/

       /* //定义导出excel的名字
        String excelName="贫困户信息";
        // 获取需要转出的excle表头的map字段
        LinkedHashMap<String, String> fieldMap =new LinkedHashMap<String, String>() ;
        fieldMap.put("phd003", "姓名");
        fieldMap.put("phd002", "身份证");
        fieldMap.put("phd004", "性别");

        //导出用户相关信息
        new ExportExcelUtils().export(excelName, errLst, fieldMap, response);*/

    }


    /**
     * 根据身份证号查询该人员是否已经登记
     * @param request
     * @param phd003
     * @return
     */
    @RequestMapping("queryPoorByIdCard")
    @ResponseBody
    public String queryPoorByIdCard(HttpServletRequest request, String phd003){
        String flag = "";
        if(!"".equals(phd003) && phd003 != null){
            String count = poorWorkService.queryPoorByIdCard(phd003);
            if(!"0".equals(count)){
               flag = "yes";
            }else{
                flag = "no";
            }
        }
        return flag;
    }


    /**
     * 保存贫困户信息
     * @return
     */
    @RequestMapping("savePoorWork")
    @ResponseBody
    public String savePoorWork(HttpServletRequest request, String treeid,ModelMap modelMap,PoorHouseholds poorHouseholds){
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){
            flag="login";
            return flag;
        }
        if(poorHouseholds != null){
            if(!"".equals(poorHouseholds.getPhd001()) && poorHouseholds.getPhd001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorHouseholds.setAae012(fpUser.getId());
                    poorHouseholds.setUpdateby(fpUser.getRealname());
                    poorHouseholds.setUpdatedate(new Date());
                }
                if(!"".equals(poorHouseholds.getPhd014()) && poorHouseholds.getPhd014() != null  && "0".equals(poorHouseholds.getPhd014())){
                    poorHouseholds.setPhd014("");
                }
                poorWorkService.updateByPrimaryKeySelective(poorHouseholds);
                poorWorkService.F_phd008(poorHouseholds.getPhd001());
               /* //根据贫困户id  查询劳动力，根据劳动力id  查询帮扶措施，给他们分别写入  脱贫状态和 易地搬迁状态
                List<PoorLaborForce> plfList = poorLaborForceService.queryPoorLaborForceByPoorWork(poorHouseholds.getPhd001());
                if(plfList.size()>0){   //说明有劳动力信息
                    String plf001 = "";
                    for (int i=0;i<plfList.size();i++){
                        plf001 = plfList.get(i).getPlf001();
                        poorWorkService.updateEysEppTsnThsByPlf001(plf001,poorHouseholds.getPhd013(),poorHouseholds.getPhd014());
                    }
                }*/
                poorWorkService.updateEysEppTsnThsByPlf001(poorHouseholds.getPhd001());
                flag = "update";
            }else{
                poorHouseholds.setPhd001(UUIDGenerator.generate().toString());
                poorHouseholds.setAae036(new Date());
                poorHouseholds.setDatasource("1");
                poorHouseholds.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorHouseholds.setAae011(fpUser.getId());
                    poorHouseholds.setCreateby(fpUser.getRealname());
                    if(!"".equals(poorHouseholds.getAab301()) && poorHouseholds.getAab301() != null){
                    }else{
                        poorHouseholds.setAab301(fpUser.getAab301());
                    }
                }
               /* if(!"".equals(poorHouseholds.getPhd014()) && poorHouseholds.getPhd014().equals("0") ){
                    poorHouseholds.setPhd014("");
                }*/
                poorWorkService.insertSelective(poorHouseholds);
                flag = poorHouseholds.getPhd001();
            }
       }

        return flag;

    }


    /**
     * 修改贫困户信息
     * @return
     */
    @RequestMapping("updatePoorWork")
    @ResponseBody
    public PoorHouseholds updatePoorWork(HttpServletRequest request, String phd001,ModelMap modelMap){
        PoorHouseholds poorHouseholds = null;
        if(!"".equals(phd001) && phd001 != null){
            poorHouseholds = poorWorkService.selectByPrimaryKey(phd001);
        }
        return poorHouseholds;
    }


    /**
     * 删除贫困户信息
     * @return
     */
    @RequestMapping("delPoorWork")
    @ResponseBody
    public String delPoorWork(HttpServletRequest request, String phd001,ModelMap modelMap){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(phd001) && phd001 != null){
            List<PoorLaborForce> poorLaborForcelist = poorLaborForceService.queryPoorLaborForceByPoorWork(phd001);
            if(poorLaborForcelist !=null && poorLaborForcelist.size()>0){
                //查询出所有劳动力信息，根据劳动力id 删除下面的  就业，培训，创业，技校信息
                for(PoorLaborForce poorLaborForce : poorLaborForcelist){
                    employmentSituationService.deleteEmploymentSituationByEys002(poorLaborForce.getPlf001(),fpUser.getRealname());
                    entrepreneurshipService.deleteEntrepreneurshipByEpp002(poorLaborForce.getPlf001(),fpUser.getRealname());
                    trainingSituationService.deleteTrainingSituationByTsn010(poorLaborForce.getPlf001(),fpUser.getRealname());
                    technicalSchoolsService.deleteTechnicalSchoolsByThs002(poorLaborForce.getPlf001(),fpUser.getRealname());
                }
            }
            PoorHouseholds poor = null;
            poor = poorWorkService.selectByPrimaryKey(phd001);
            poor.setDeleteby(fpUser.getRealname());
            poor.setDeletedate(new Date());
            poorWorkService.updateByPrimaryKeySelective(poor);

           poorLaborForceService.deleteByPlf002(phd001,fpUser.getRealname());//删除劳动力
            s = poorWorkService.deleteByPrimaryKey(phd001);//删除贫困户


        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 批量   删除贫困户信息
     * @return
     */
    @RequestMapping("delAllPoorWorkByIds")
    @ResponseBody
    public String delAllPoorWorkByIds(HttpServletRequest request, @RequestParam(value = "phd001s[]") String[] phd001s, ModelMap modelMap){
        int s = 0;
        if(!"".equals(phd001s) && phd001s != null){
            //根据贫困户id  查询出劳动力信息
            List<PoorLaborForce> poorLaborForcelist =  poorLaborForceService.queryPoorLaborForceByPoorWorkIds(phd001s);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            if(poorLaborForcelist !=null && poorLaborForcelist.size()>0){
                //查询出所有劳动力信息，根据劳动力id 删除下面的  就业，培训，创业，技校信息
                for(PoorLaborForce poorLaborForce : poorLaborForcelist){
                    employmentSituationService.deleteEmploymentSituationByEys002(poorLaborForce.getPlf001(),fpUser.getRealname());
                    entrepreneurshipService.deleteEntrepreneurshipByEpp002(poorLaborForce.getPlf001(),fpUser.getRealname());
                    trainingSituationService.deleteTrainingSituationByTsn010(poorLaborForce.getPlf001(),fpUser.getRealname());
                    technicalSchoolsService.deleteTechnicalSchoolsByThs002(poorLaborForce.getPlf001(),fpUser.getRealname());
                    poorLaborForceService.deleteByPlf002(poorLaborForce.getPlf002(),fpUser.getRealname());//删除劳动力
                }
            }
            //循环删除贫困户
            for (int i=0;i<phd001s.length;i++){
                PoorHouseholds phd = null;
                phd = poorWorkService.selectByPrimaryKey(phd001s[i]);
                phd.setDeleteby(fpUser.getRealname());
                phd.setDeletedate(new Date());
                poorWorkService.updateByPrimaryKeySelective(phd);
                poorWorkService.deleteByPrimaryKey(phd001s[i]);//删除贫困户

                s++;
            }
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }




//***************************************以下是劳动力信息*****************************************************

    /**
     * 根据贫困户信息id  查询劳动力信息列表
     */
    @RequestMapping("queryWorkerByPlf002")
    @ResponseBody
    public List<PoorLaborForce> queryWorkerByPlf002(HttpServletRequest request,String aac002){
        List<PoorLaborForce> list = null;
        if(!"".equals(aac002) && aac002 != null){
            list = poorLaborForceService.queryPoorLaborForceByPoorWork(aac002);
        }
        return  list;
    }


    /**
     * 根据身份证号查询该人员是否已经登记
     * 劳动力
     * @param request
     * @param plf005
     * @return
     */
    @RequestMapping("queryWorkerByIdCard")
    @ResponseBody
    public String queryWorkerByIdCard(HttpServletRequest request, String plf005){
        String flag = "";
        if(!"".equals(plf005) && plf005 != null){
            String count = poorLaborForceService.queryWorkerByIdCard(plf005);
            if(!"0".equals(count)){
                flag = "yes";
            }else{
                flag = "no";
            }
        }
        return flag;
    }

    /**
     * 保存劳动力信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveWorker")
    @ResponseBody
    public String saveWorker(HttpServletRequest request,ModelMap modelMap,String workeraab301,PoorLaborForce poorLaborForce) throws Exception{
        String flag = "";
        //开始先将劳动力置空
        poorLaborForce.setIspoor("");
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(poorLaborForce != null){
            if(!"".equals(poorLaborForce.getPlf001()) && poorLaborForce.getPlf001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorLaborForce.setAae012(fpUser.getId());
                    poorLaborForce.setUpdateby(fpUser.getRealname());
                    poorLaborForce.setUpdatedate(new Date());
                }
                //获取就业创业状态
               /* String plf011 = queryPlf011(poorLaborForce.getPlf001());
                if(!"".equals(plf011) && plf011 != null){
                    poorLaborForce.setPlf011(plf011);
                }*/

                if(!"".equals(poorLaborForce.getPlf025()) && poorLaborForce.getPlf025() != null){
                }else{
                    poorLaborForce.setPlf025("0");
                }

                //获取上学状态，如果有上学状态则说明没有劳动能力
               /* if(!"".equals(poorLaborForce.getPlf015()) && poorLaborForce.getPlf015() != null && !poorLaborForce.getPlf015().equals("null")){
                    poorLaborForce.setIspoor("");//说明不是劳动力
                }else{//说明上学状态没值，则判断成员变更是否有值*/
                if(!"".equals(poorLaborForce.getPlf015()) && poorLaborForce.getPlf015() != null && poorLaborForce.getPlf015().equals("1")){
                    poorLaborForce.setIspoor("");//说明不是劳动力
                }else{//说明上学状态没值，则判断成员变更是否有值
                    if(poorLaborForce.getPlf025().equals("0")){     //说明成员变更没有值   则去判断劳动能力是否有值
                        //获取劳动能力
                        if(!"".equals(poorLaborForce.getPlf018()) && poorLaborForce.getPlf018() != null){
                            //说明劳动能力有值   不等于03  04说明是有劳动能力的
                            int plf008 = Integer.parseInt(poorLaborForce.getPlf008());
                            if(!poorLaborForce.getPlf018().equals("03") && !poorLaborForce.getPlf018().equals("04") &&  !poorLaborForce.getPlf018().equals("05") &&plf008>=16 && plf008< 60 ){
                                poorLaborForce.setIspoor("1");//说明是劳动力
                            }
                        }else{      //说明劳动能力没有值，则根据年龄判断
                            int plf008 = Integer.parseInt(poorLaborForce.getPlf008());
                            if(plf008>=16 && plf008< 60){
                               // poorLaborForce.setIspoor("1");//说明是劳动力
                            }
                        }
                    }

                }

                String phd008 = queryNormalWorkerByPlf002(poorLaborForce.getPlf002());
                PoorHouseholds poorHouseholds = poorWorkService.selectByPrimaryKey(poorLaborForce.getPlf002());
                poorLaborForce.setPhd013(poorHouseholds.getPhd013());
                poorLaborForce.setPhd014(poorHouseholds.getPhd014());

                poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);


                //劳动力数量
                if(!"0".equals(phd008)){
                    poorHouseholds.setPhd008(Integer.parseInt(phd008));
                }else{
                    poorHouseholds.setPhd008(0);
                }
                //有就业创业意愿人数
                String youchuangyecount = havejiuyechuangyecount(poorLaborForce.getPlf002());
                //已就业创业人数
                String yichuangyecount = yijiuyechuangyecount(poorLaborForce.getPlf002());
                //已就业创业人名称
                String yichuangyename = yijiuyechuangyename(poorLaborForce.getPlf002());
                //未就业创业人数
                String weichuangyecount = weijiuyechuangyecount(poorLaborForce.getPlf002());
                //未就业创业人名称
                String weichuangyename = weijiuyechuangyename(poorLaborForce.getPlf002());
                poorHouseholds.setHavejiuyechuangyecount(youchuangyecount);
                poorHouseholds.setYijiuyechuangyecount(yichuangyecount);
                poorHouseholds.setYijiuyechuangyename(yichuangyename);
                poorHouseholds.setWeijiuyechuangyecount(weichuangyecount);
                poorHouseholds.setWeijiuyechuangyename(weichuangyename);

                poorWorkService.updateByPrimaryKeySelective(poorHouseholds);//修改劳动力数量
                poorWorkService.F_phd008(poorHouseholds.getPhd001());
                employmentSituationService.f_jy_cy(poorLaborForce.getPlf001());
                flag = "update";
            }else{
                poorLaborForce.setPlf001(UUIDGenerator.generate().toString());
                poorLaborForce.setAae036(new Date());
                poorLaborForce.setDatasource("1");
                poorLaborForce.setAae100("1");
                if(!"".equals(poorLaborForce.getPlf025()) && poorLaborForce.getPlf025() != null){
                }else{
                    poorLaborForce.setPlf025("0");
                }
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorLaborForce.setAae011(fpUser.getId());
                    poorLaborForce.setCreateby(fpUser.getRealname());
                    if(!"".equals(workeraab301) && workeraab301 != null){
                        poorLaborForce.setAab301(workeraab301);
                    }else{
                        poorLaborForce.setAab301(fpUser.getAab301());
                    }
                }

                //获取上学状态，如果有上学状态则说明没有劳动能力
                if(!"".equals(poorLaborForce.getPlf015()) && poorLaborForce.getPlf015() != null && poorLaborForce.getPlf015().equals("1")){
                    poorLaborForce.setIspoor("");//说明不是劳动力
                }else{//说明上学状态没值，则判断成员变更是否有值
                    if(poorLaborForce.getPlf025().equals("0")){     //说明成员变更没有值   则去判断劳动能力是否有值
                        //获取劳动能力
                        if(!"".equals(poorLaborForce.getPlf018()) && poorLaborForce.getPlf018() != null){
                            //说明劳动能力有值   不等于03  04说明是有劳动能力的
                            int plf008 = Integer.parseInt(poorLaborForce.getPlf008());
                            if(!poorLaborForce.getPlf018().equals("03") && !poorLaborForce.getPlf018().equals("04") && !poorLaborForce.getPlf018().equals("05") &&plf008>=16 && plf008< 60 ){
                                poorLaborForce.setIspoor("1");//说明是劳动力
                            }
                        }else{      //说明劳动能力没有值，则根据年龄判断
                            int plf008 = Integer.parseInt(poorLaborForce.getPlf008());
                            if(plf008>=16 && plf008< 60){
                                // poorLaborForce.setIspoor("1");//说明是劳动力
                            }
                        }
                    }
                }

                String phd008 = queryNormalWorkerByPlf002(poorLaborForce.getPlf002());
                PoorHouseholds poorHouseholds = poorWorkService.selectByPrimaryKey(poorLaborForce.getPlf002());
                poorLaborForce.setPhd013(poorHouseholds.getPhd013());
                poorLaborForce.setPhd014(poorHouseholds.getPhd014());
                poorLaborForceService.insertSelective(poorLaborForce);


                if(!"0".equals(phd008)){
                    poorHouseholds.setPhd008(Integer.parseInt(phd008));
                }else{
                    poorHouseholds.setPhd008(0);
                }
                //有就业创业意愿人数
                String youchuangyecount = havejiuyechuangyecount(poorLaborForce.getPlf002());
                //已就业创业人数
                String yichuangyecount = yijiuyechuangyecount(poorLaborForce.getPlf002());
                //已就业创业人名称
                String yichuangyename = yijiuyechuangyename(poorLaborForce.getPlf002());
                //未就业创业人数
                String weichuangyecount = weijiuyechuangyecount(poorLaborForce.getPlf002());
                //未就业创业人名称
                String weichuangyename = weijiuyechuangyename(poorLaborForce.getPlf002());
                poorHouseholds.setHavejiuyechuangyecount(youchuangyecount);
                poorHouseholds.setYijiuyechuangyecount(yichuangyecount);
                poorHouseholds.setYijiuyechuangyename(yichuangyename);
                poorHouseholds.setWeijiuyechuangyecount(weichuangyecount);
                poorHouseholds.setWeijiuyechuangyename(weichuangyename);

                poorWorkService.updateByPrimaryKeySelective(poorHouseholds);//修改劳动力数量
                flag = poorLaborForce.getPlf001();
                poorWorkService.F_phd008(poorHouseholds.getPhd001());
                employmentSituationService.f_jy_cy(poorLaborForce.getPlf001());
            }
        }
        return flag;
    }


    /**
     * 根据劳动力id 获取就业信息和创业信息  更新劳动力的就业创业状态情况
     */
    public String queryPlf011(String plf001) throws Exception{
        String plf011 = "";//就业创业状态
        //根据劳动力id 获取劳动力的就业信息和创业信息，获取最新的一条，更新劳动力的就创业状态
        List<EmploymentSituation> list =  employmentSituationService.queryEmploymentSituationByEys002(plf001);
        List<Entrepreneurship> listship =  entrepreneurshipService.queryEntrepreneurshipByEpp002(plf001);;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取最新一条就业信息
        String eys004 = "";//就业类型
        String eys009 = "";//就业开始日期
        String eys010 = "";//就业结束日期
        String epp006 = "";//创业开始日期
        String epp007 = "";//创业结束日期
        if(list.size()>0 && list != null){
            //说明有就业信息，判断就业结束时间是否有值，
            eys010 = list.get(0).getEys010();
            if(!"".equals(eys010) && eys010 != null){
            }else{
                //获取到就业开始日期
                eys009 = list.get(0).getEys009();
            }
        }
        if(listship.size()>0 && listship != null){
            //说明有创业信息
            epp007 = listship.get(0).getEpp007();
            if(!"".equals(epp007) && epp007 != null){
            }else{
                epp006 = listship.get(0).getEpp006();
            }
        }

        //如果有就业结束日期 和创业结束日期
        if(!"".equals(eys010) && eys010 != null && !"".equals(epp007) && epp007 != null){
            //去判断是否有创业结束日期，如果有创业结束日期，则比较就业结束日期和创业结束日期那个大
            //说明也有创业结束日期，则哪个大取那个状态赋值给 劳动力的就业创业状态
            int big = DateUtil.parseDate(epp007).compareTo(DateUtil.parseDate(eys010));
            if(big == 1){   //说明epp007大于eys010
                //说明创业时间在后，则取创业状态
                plf011 = "5";   //自主创业
            }else if(big == -1){
                //说明就业结束日期再后，则取就业的状态
                if(queryAa10("AAA006",list.get(0).getEys004()).equals("1")){
                    plf011 = "2";
                }else if(queryAa10("AAA006",list.get(0).getEys004()).equals("2")){
                    plf011 = "4";
                }
            }else if(big == 0){     //说明两个一样大

            }
        }

        //如果有就业开始日期，和创业开始日期  并且都没有结束日期
        if(!"".equals(eys009) && eys009 != null && !"".equals(epp006) && epp006 != null && (("".equals(eys010) || eys010 == null) && ("".equals(epp007) || epp007 == null))){
            //去判断是否有创业结束日期，如果有创业结束日期，则比较就业结束日期和创业结束日期那个大
            //说明也有创业结束日期，则哪个大取那个状态赋值给 劳动力的就业创业状态
            int big = DateUtil.parseDate(eys009).compareTo(DateUtil.parseDate(epp006));
            if(big == 1){   //eys009大于epp006
                //说明就业时间在后，则取就业状态
                plf011 = queryAa10("AAA006",list.get(0).getEys004());
            }else if(big == -1){
                //说明创业结束日期再后，则取创业的状态
                plf011 = "5";
            }else if(big == 0){     //说明两个一样大

            }
        }


//如果有就业开始日期，没有就业结束日期，和有创业开始日期  并且都有创业结束日期，则取就业状态
        if(!"".equals(eys009) && eys009 != null && ("".equals(eys010) || eys010 == null) && ((!"".equals(epp006) && epp006 != null) && (!"".equals(epp007) && epp007 != null))){
            if(queryAa10("AAA006",list.get(0).getEys004()).equals("1")){
                plf011 = "2";
            }else if(queryAa10("AAA006",list.get(0).getEys004()).equals("2")){
                plf011 = "4";
            }
        }

        //如果有就业开始日期，没有就业结束日期，和 没有创业开始日期  并且没有创业结束日期，则取就业状态
        if(!"".equals(eys009) && eys009 != null && ("".equals(eys010) || eys010 == null) && (("".equals(epp006) || epp006 == null) && ("".equals(epp007) || epp007 == null))){
            if(queryAa10("AAA006",list.get(0).getEys004()).equals("1")){
                plf011 = "2";
            }else if(queryAa10("AAA006",list.get(0).getEys004()).equals("2")){
                plf011 = "4";
            }

        }

        //如果有创业开始日期，没有创业结束日期，和有就业开始日期  并且都有有就业结束日期，则取创业状态
        if(!"".equals(eys009) && eys009 != null && ("".equals(eys010) || eys010 == null) && ((!"".equals(epp006) && epp006 != null) && (!"".equals(epp007) && epp007 != null))){
            plf011 = "5";
        }
        return plf011;
    }


    /**
     * 下拉值
     * @param aaa100
     * @param value
     * @return
     */
    public String queryAa10(String aaa100, String value){
        String aaa102 ="";
        List<Aa10> aa10= new ArrayList<Aa10>();
        aa10= aa10Service.queryAa10ByAaa100(aaa100);
        for (Aa10 aa10_type: aa10) {
            if (value.trim().equals(aa10_type.getAaa103().trim())){
                aaa102 = (aa10_type.getAaa102());
                break ;
            }
        }

        return aaa102;
    }

    /**
     * 修改劳动力信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateWorker")
    @ResponseBody
    public PoorLaborForce updateWorker(HttpServletRequest request,String plf001,ModelMap modelMap) throws Exception{
        PoorLaborForce poorLaborForce = null;
        if(!"".equals(plf001) && plf001 != null){
            poorLaborForce = poorLaborForceService.selectByPrimaryKey(plf001);
            //获取就业创业状态   2019-07-11注释了
            /*String plf011 = queryPlf011(plf001);
            if(!"".equals(plf011) && plf011 != null){
                poorLaborForce.setPlf011(plf011);
            }*/
        }

        return poorLaborForce;
    }


    /**
     * 删除劳动力信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteWorker")
    @ResponseBody
    public String deleteWorker(HttpServletRequest request,String plf001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(plf001);
        String plf002 = poorLaborForce.getPlf002();
        if(!"".equals(plf002) && plf002 != null){
            PoorHouseholds poorHouseholds = poorWorkService.selectByPrimaryKey(plf002);
            //有就业创业意愿人数
            String youchuangyecount = havejiuyechuangyecount(plf002);
            //已就业创业人数
            String yichuangyecount = yijiuyechuangyecount(plf002);
            //已就业创业人名称
            String yichuangyename = yijiuyechuangyename(plf002);
            //未就业创业人数
            String weichuangyecount = weijiuyechuangyecount(plf002);
            //未就业创业人名称
            String weichuangyename = weijiuyechuangyename(plf002);

            if(poorHouseholds != null){
                poorHouseholds.setHavejiuyechuangyecount(youchuangyecount);
                poorHouseholds.setYijiuyechuangyecount(yichuangyecount);
                poorHouseholds.setYijiuyechuangyename(yichuangyename);
                poorHouseholds.setWeijiuyechuangyecount(weichuangyecount);
                poorHouseholds.setWeijiuyechuangyename(weichuangyename);

                String phd008 = queryNormalWorkerByPlf002(plf002);
                if(!"0".equals(phd008)){
                    poorHouseholds.setPhd008(Integer.parseInt(phd008));
                }else{
                    poorHouseholds.setPhd008(0);
                }

                poorWorkService.updateByPrimaryKeySelective(poorHouseholds);
                poorWorkService.F_phd008(poorHouseholds.getPhd001());
            }

        }

        if(!"".equals(plf001) && plf001 != null){
            //根据劳动力id   删除就业   创业   培训   技校信息
            employmentSituationService.deleteEmploymentSituationByEys002(plf001,fpUser.getRealname());
            entrepreneurshipService.deleteEntrepreneurshipByEpp002(plf001,fpUser.getRealname());
            trainingSituationService.deleteTrainingSituationByTsn010(plf001,fpUser.getRealname());
            technicalSchoolsService.deleteTechnicalSchoolsByThs002(plf001,fpUser.getRealname());
            //保存删除信息
            poorLaborForce.setDeleteby(fpUser.getRealname());
            poorLaborForce.setDeletedate(new Date());
            poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);

            s = poorLaborForceService.deleteByPrimaryKey(plf001);//删除劳动力
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据贫困户id 判断该贫困户下有多少个劳动力属于正常劳动力
     * phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String queryNormalWorkerByPlf002(String phd001){
        String normalCount = poorLaborForceService.queryNormalWorkerByPlf002(phd001);
        return normalCount;
    }

    /**
     *   根据贫困户id 判断该贫困户下有  有就业创业意愿人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String havejiuyechuangyecount(String phd001){
        String normalCount = poorLaborForceService.havejiuyechuangyecount(phd001);
        return normalCount;
    }

    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String yijiuyechuangyecount(String phd001){
        String normalCount = poorLaborForceService.yijiuyechuangyecount(phd001);
        return normalCount;
    }

    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String yijiuyechuangyename(String phd001){
        String normalCount = poorLaborForceService.yijiuyechuangyename(phd001);
        return normalCount;
    }

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String weijiuyechuangyecount(String phd001){
        String normalCount = poorLaborForceService.weijiuyechuangyecount(phd001);
        return normalCount;
    }

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    public String weijiuyechuangyename(String phd001){
        String normalCount = poorLaborForceService.weijiuyechuangyename(phd001);
        return normalCount;
    }



//***************************************以下是就业信息*****************************************************

    /**
     * 根据劳动力信息id  查询就业信息列表
     */
    @RequestMapping("queryJiuYeByEys002")
    @ResponseBody
    public List<EmploymentSituation> queryJiuYeByEys002(HttpServletRequest request,String eys002){
        List<EmploymentSituation> list = null;
        if(!"".equals(eys002) && eys002 != null){
            list = employmentSituationService.queryEmploymentSituationByEys002(eys002);
        }
        return  list;
    }


    /**
     * 保存就业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveJiuYe")
    @ResponseBody
    public String saveJiuYe(HttpServletRequest request,ModelMap modelMap,String jiuyeaab301,EmploymentSituation employmentSituation) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(employmentSituation != null){
            if(!"".equals(employmentSituation.getEys001()) && employmentSituation.getEys001() != null){
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(employmentSituation.getEys002());
                if(poorLaborForce != null){
                    employmentSituation.setPhd013(poorLaborForce.getPhd013());
                    employmentSituation.setPhd014(poorLaborForce.getPhd014());
                    employmentSituation.setPlf004(poorLaborForce.getPlf004());
                    employmentSituation.setPlf005(poorLaborForce.getPlf005());
                }

                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSituation.setAae012(fpUser.getId());
                    employmentSituation.setUpdateby(fpUser.getRealname());
                    employmentSituation.setUpdatedate(new Date());
                }

                //根据劳动力id  获取最新的一条就业信息，和当前的比较，看那个是最新的
                EmploymentSituation emp = employmentSituationService.queryNewTrainSituation(employmentSituation.getEys002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getEys009()) && emp.getEys009() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getEys009()).getTime() - DateUtil.parseDate(employmentSituation.getEys009()).getTime()>0){
                            employmentSituation.setIsnew("0");
                        }else{
                            employmentSituation.setIsnew("1");
                            //将原来的是否最新修改为0
                            employmentSituationService.updateIsNewByEys002(employmentSituation.getEys002());
                        }
                    }
                }else{
                    employmentSituation.setIsnew("1");  //说明之前没有值
                }

                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);
                //获取就业创业状态
               // String plf011 = queryPlf011(employmentSituation.getEys002());
               // PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(employmentSituation.getEys002());
                //获取就业创业状态
               /* if(!"".equals(plf011) && plf011 != null){
                    poorLaborForce.setPlf011(plf011);
                    poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);
                }*/
                //根据phd001 更新就业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = "update";
            }else{
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(employmentSituation.getEys002());
                if(poorLaborForce != null){
                    employmentSituation.setPhd013(poorLaborForce.getPhd013());
                    employmentSituation.setPhd014(poorLaborForce.getPhd014());
                    employmentSituation.setPlf004(poorLaborForce.getPlf004());
                    employmentSituation.setPlf005(poorLaborForce.getPlf005());
                }
                employmentSituation.setEys001(UUIDGenerator.generate().toString());
                employmentSituation.setAae036(new Date());
                employmentSituation.setDatasource("1");
                employmentSituation.setAae100("1");

                FpUser fpUser = null;
                if(!"".equals(employmentSituation.getEys009()) && employmentSituation.getEys009() != null){
                    String eys013 = employmentSituation.getEys009().substring(0,4);
                    employmentSituation.setEys013(eys013);//年度
                }
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSituation.setAae011(fpUser.getId());
                    employmentSituation.setCreateby(fpUser.getRealname());
                    if(!"".equals(jiuyeaab301) && jiuyeaab301 != null){
                        employmentSituation.setAab301(jiuyeaab301);
                    }else{
                        employmentSituation.setAab301(fpUser.getAab301());
                    }
                }
                //根据劳动力id  获取最新的一条就业信息，和当前的比较，看那个是最新的
                EmploymentSituation emp = employmentSituationService.queryNewTrainSituation(employmentSituation.getEys002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getEys009()) && emp.getEys009() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getEys009()).getTime() - DateUtil.parseDate(employmentSituation.getEys009()).getTime()>0){
                            employmentSituation.setIsnew("0");
                        }else{
                            employmentSituation.setIsnew("1");
                            //将原来的是否最新修改为0
                            employmentSituationService.updateIsNewByEys002(employmentSituation.getEys002());
                        }
                    }
                }else{
                    employmentSituation.setIsnew("1");  //说明之前没有值
                }

                employmentSituationService.insertSelective(employmentSituation);
                //根据phd001 更新就业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                //获取就业创业状态
               /* String plf011 = queryPlf011(employmentSituation.getEys002());
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(employmentSituation.getEys002());
                //获取就业创业状态
                if(!"".equals(plf011) && plf011 != null){
                    poorLaborForce.setPlf011(plf011);
                    poorLaborForceService.updateByPrimaryKeySelective(poorLaborForce);
                }*/

                flag = employmentSituation.getEys001();
            }
        }
        employmentSituationService.f_jy_cy(employmentSituation.getEys002());
        return flag;
    }


    /**
     * 修改就业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateJiuye")
    @ResponseBody
    public EmploymentSituation updateJiuye(HttpServletRequest request,String eys001,ModelMap modelMap){
        EmploymentSituation employmentSituation = null;
        if(!"".equals(eys001) && eys001 != null){
            employmentSituation = employmentSituationService.selectByPrimaryKey(eys001);
        }
        return employmentSituation;
    }


    /**
     * 删除就业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteJiuye")
    @ResponseBody
    public String deleteJiuye(HttpServletRequest request,String eys001){
        int s = 0;
        if(!"".equals(eys001) && eys001 != null){
            EmploymentSituation employmentSituation = null;
            employmentSituation = employmentSituationService.selectByPrimaryKey(eys001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            employmentSituation.setDeleteby(fpUser.getRealname());
            employmentSituation.setDeletedate(new Date());
            employmentSituationService.updateByPrimaryKeySelective(employmentSituation);
            s = employmentSituationService.deleteByPrimaryKey(eys001);
            employmentSituationService.f_jy_cy(employmentSituation.getEys002());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 删除就业信息
     * 根据劳动力 id
     * @return
     */
    @RequestMapping("deleteJiuyeByPlf001")
    @ResponseBody
    public String deleteJiuyeByPlf001(HttpServletRequest request,String plf001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(plf001) && plf001 != null){
            s = employmentSituationService.deleteEmploymentSituationByEys002(plf001,fpUser.getRealname());

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


//***************************************以下是创业信息*****************************************************

    /**
     * 根据劳动力信息id  查询创业信息列表
     */
    @RequestMapping("queryEntrepreneurshipByEpp002")
    @ResponseBody
    public List<Entrepreneurship> queryEntrepreneurshipByEpp002(HttpServletRequest request,String epp002){
        List<Entrepreneurship> list = null;
        if(!"".equals(epp002) && epp002 != null){
            list = entrepreneurshipService.queryEntrepreneurshipByEpp002(epp002);
        }
        return  list;
    }


    /**
     * 保存创业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveChuangYe")
    @ResponseBody
    public String saveChuangYe(HttpServletRequest request,ModelMap modelMap,String chuangyeaab301,Entrepreneurship entrepreneurship) throws Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(entrepreneurship != null){
            if(!"".equals(entrepreneurship.getEpp001()) && entrepreneurship.getEpp001() != null){
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(entrepreneurship.getEpp002());
                if(poorLaborForce != null){
                    entrepreneurship.setPhd013(poorLaborForce.getPhd013());
                    entrepreneurship.setPhd014(poorLaborForce.getPhd014());
                    entrepreneurship.setPlf004(poorLaborForce.getPlf004());
                    entrepreneurship.setPlf005(poorLaborForce.getPlf005());
                }

                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    entrepreneurship.setAae012(fpUser.getId());
                    entrepreneurship.setUpdateby(fpUser.getRealname());
                    entrepreneurship.setUpdatedate(new Date());
                }

                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                Entrepreneurship emp = entrepreneurshipService.queryNewEntrepreneurship(entrepreneurship.getEpp002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getEpp006()) && emp.getEpp006() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getEpp006()).getTime() - DateUtil.parseDate(entrepreneurship.getEpp006()).getTime()>0){
                            entrepreneurship.setIsnew("0");
                        }else{
                            entrepreneurship.setIsnew("1");
                            //把之前的创业信息修改为不是最新的 isnew=0
                            entrepreneurshipService.updateIsNewByEpp002(entrepreneurship.getEpp002());
                        }
                    }
                }else{
                    entrepreneurship.setIsnew("1");  //说明之前没有值
                }

                entrepreneurshipService.updateByPrimaryKeySelective(entrepreneurship);
                //获取就业创业状态
               /* String plf011 = queryPlf011(entrepreneurship.getEpp002());
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(entrepreneurship.getEpp002());
                //获取就业创业状态
                if(!"".equals(plf011) && plf011 != null){
                    poorLaborForce.setPlf011(plf011);
                }*/
            //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = "update";
            }else{
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(entrepreneurship.getEpp002());
                if(poorLaborForce != null){
                    entrepreneurship.setPhd013(poorLaborForce.getPhd013());
                    entrepreneurship.setPhd014(poorLaborForce.getPhd014());
                    entrepreneurship.setPlf004(poorLaborForce.getPlf004());
                    entrepreneurship.setPlf005(poorLaborForce.getPlf005());
                }
                entrepreneurship.setEpp001(UUIDGenerator.generate().toString());
                entrepreneurship.setAae036(new Date());
                entrepreneurship.setDatasource("1");//1代表录入，2代表导入
                entrepreneurship.setAae100("1");
                FpUser fpUser = null;
                if(!"".equals(entrepreneurship.getEpp006()) && entrepreneurship.getEpp006() != null){
                    String epp009 = entrepreneurship.getEpp006().substring(0,4);
                    entrepreneurship.setEpp009(epp009);//年度
                }
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    entrepreneurship.setAae011(fpUser.getId());
                    entrepreneurship.setCreateby(fpUser.getRealname());
                    if(!"".equals(chuangyeaab301) && chuangyeaab301 != null){
                        entrepreneurship.setAab301(chuangyeaab301);
                    }else{
                        entrepreneurship.setAab301(fpUser.getAab301());
                    }
                }
                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                Entrepreneurship emp = entrepreneurshipService.queryNewEntrepreneurship(entrepreneurship.getEpp002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getEpp006()) && emp.getEpp006() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getEpp006()).getTime() - DateUtil.parseDate(entrepreneurship.getEpp006()).getTime()>0){
                            entrepreneurship.setIsnew("0");
                        }else{
                            entrepreneurship.setIsnew("1");
                            //把之前的创业信息修改为不是最新的 isnew=0
                            entrepreneurshipService.updateIsNewByEpp002(entrepreneurship.getEpp002());
                        }
                    }
                }else{
                    entrepreneurship.setIsnew("1");  //说明之前没有值
                }
                entrepreneurshipService.insertSelective(entrepreneurship);
                //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                //获取就业创业状态
               /* String plf011 = queryPlf011(entrepreneurship.getEpp002());
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(entrepreneurship.getEpp002());
                //获取就业创业状态
                if(!"".equals(plf011) && plf011 != null){
                    poorLaborForce.setPlf011(plf011);
                }*/
                flag = entrepreneurship.getEpp001();
            }
        }
        entrepreneurshipService.f_jy_cy(entrepreneurship.getEpp002());
        return flag;
    }

    /**
     * 修改创业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateChuangYe")
    @ResponseBody
    public Entrepreneurship ChuangYe(HttpServletRequest request,String epp001,ModelMap modelMap){
        Entrepreneurship entrepreneurship = null;
        if(!"".equals(epp001) && epp001 != null){
            entrepreneurship = entrepreneurshipService.selectByPrimaryKey(epp001);
        }
        return entrepreneurship;
    }


    /**
     * 删除创业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteChuangYe")
    @ResponseBody
    public String deleteChuangYe(HttpServletRequest request,String epp001){
        int s = 0;
        if(!"".equals(epp001) && epp001 != null){
            Entrepreneurship entrepreneurship = null;
            entrepreneurship = entrepreneurshipService.selectByPrimaryKey(epp001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            entrepreneurship.setDeleteby(fpUser.getRealname());
            entrepreneurship.setDeletedate(new Date());
            entrepreneurshipService.updateByPrimaryKeySelective(entrepreneurship);
            s = entrepreneurshipService.deleteByPrimaryKey(epp001);
            entrepreneurshipService.f_jy_cy(entrepreneurship.getEpp002());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据劳动力id 删除创业信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteEntrepreneurshipByEpp002")
    @ResponseBody
    public String deleteEntrepreneurshipByEpp002(HttpServletRequest request,String epp002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(epp002) && epp002 != null){
            s = entrepreneurshipService.deleteEntrepreneurshipByEpp002(epp002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    //***************************************以下是培训信息*****************************************************


    /**
     * 根据劳动力信息id  查询培训信息列表
     */
    @RequestMapping("queryTrainingSituationByTsn010")
    @ResponseBody
    public List<TrainingSituation> queryTrainingSituationByTsn010(HttpServletRequest request,String tsn010){
        List<TrainingSituation> list = null;
        if(!"".equals(tsn010) && tsn010 != null){
            list = trainingSituationService.queryTrainingSituationByTsn010(tsn010);
        }
        return  list;
    }


    /**
     * 保存培训信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("savePeiXun")
    @ResponseBody
    public String savePeiXun(HttpServletRequest request,ModelMap modelMap,String peixunaab301,TrainingSituation trainingSituation) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(trainingSituation != null){
            if(!"".equals(trainingSituation.getTsn001()) && trainingSituation.getTsn001() != null){

                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(trainingSituation.getTsn010());
                if(poorLaborForce != null){
                    trainingSituation.setPhd013(poorLaborForce.getPhd013());
                    trainingSituation.setPhd014(poorLaborForce.getPhd014());
                    trainingSituation.setPlf004(poorLaborForce.getPlf004());
                    trainingSituation.setPlf005(poorLaborForce.getPlf005());
                }

                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    trainingSituation.setAae012(fpUser.getId());
                    trainingSituation.setUpdateby(fpUser.getRealname());
                    trainingSituation.setUpdatedate(new Date());
                }

                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                TrainingSituation emp = trainingSituationService.queryNewTrainSituation(trainingSituation.getTsn010());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getTsn007()) && emp.getTsn007() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getTsn007()).getTime() - DateUtil.parseDate(trainingSituation.getTsn007()).getTime()>0){
                            trainingSituation.setIsnew("0");
                        }else{
                            //把之前的创业信息修改为不是最新的 isnew=0
                            trainingSituation.setIsnew("1");
                            trainingSituationService.updateIsNewByTsn010(trainingSituation.getTsn010());
                        }
                    }
                }else{
                    trainingSituation.setIsnew("1");  //说明之前没有值
                }

                trainingSituationService.updateByPrimaryKeySelective(trainingSituation);
                //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = "update";
            }else{
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(trainingSituation.getTsn010());
                if(poorLaborForce != null){
                    trainingSituation.setPhd013(poorLaborForce.getPhd013());
                    trainingSituation.setPhd014(poorLaborForce.getPhd014());
                    trainingSituation.setPlf004(poorLaborForce.getPlf004());
                    trainingSituation.setPlf005(poorLaborForce.getPlf005());
                }
                trainingSituation.setTsn001(UUIDGenerator.generate().toString());
                trainingSituation.setAae036(new Date());
                FpUser fpUser = null;
                trainingSituation.setDatasource("1");//1代表录入，2代表导入
                trainingSituation.setAae100("1");
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    trainingSituation.setAae011(fpUser.getId());
                    trainingSituation.setCreateby(fpUser.getRealname());
                    if(!"".equals(peixunaab301) && peixunaab301 != null){
                        trainingSituation.setAab301(peixunaab301);
                    }else{
                        trainingSituation.setAab301(fpUser.getAab301());
                    }
                }

                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                TrainingSituation emp = trainingSituationService.queryNewTrainSituation(trainingSituation.getTsn010());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getTsn007()) && emp.getTsn007() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getTsn007()).getTime() - DateUtil.parseDate(trainingSituation.getTsn007()).getTime()>0){
                            trainingSituation.setIsnew("0");
                        }else{
                            //把之前的创业信息修改为不是最新的 isnew=0
                            trainingSituation.setIsnew("1");
                            trainingSituationService.updateIsNewByTsn010(trainingSituation.getTsn010());
                        }
                    }
                }else{
                    trainingSituation.setIsnew("1");  //说明之前没有值
                }

                trainingSituationService.insertSelective(trainingSituation);
                //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = trainingSituation.getTsn001();
            }
        }
        return flag;
    }

    /**
     * 修改培训信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updatePeiXun")
    @ResponseBody
    public TrainingSituation updatePeiXun(HttpServletRequest request,String tsn001,ModelMap modelMap){
        TrainingSituation trainingSituation = null;
        if(!"".equals(tsn001) && tsn001 != null){
            trainingSituation = trainingSituationService.selectByPrimaryKey(tsn001);
        }
        return trainingSituation;
    }


    /**
     * 删除培训信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deletePeiXun")
    @ResponseBody
    public String deletePeiXun(HttpServletRequest request,String tsn001){
        int s = 0;
        if(!"".equals(tsn001) && tsn001 != null){
            TrainingSituation trainingSituation = null;
            trainingSituation = trainingSituationService.selectByPrimaryKey(tsn001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            trainingSituation.setDeleteby(fpUser.getRealname());
            trainingSituation.setDeletedate(new Date());
            trainingSituationService.updateByPrimaryKeySelective(trainingSituation);

            s = trainingSituationService.deleteByPrimaryKey(tsn001);

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据劳动力id 删除培训信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteTrainingSituationByTsn010")
    @ResponseBody
    public String deleteTrainingSituationByTsn010(HttpServletRequest request,String tsn010){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(tsn010) && tsn010 != null){
            s = trainingSituationService.deleteTrainingSituationByTsn010(tsn010,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    //***************************************以下是技工院校信息*****************************************************

    /**
     * 根据劳动力信息id  查询技工院校信息列表
     */
    @RequestMapping("queryTechnicalSchoolsByThs002")
    @ResponseBody
    public List<TechnicalSchools> queryTechnicalSchoolsByThs002(HttpServletRequest request,String ths002){
        List<TechnicalSchools> list = null;
        if(!"".equals(ths002) && ths002 != null){
            list = technicalSchoolsService.queryTechnicalSchoolsByThs002(ths002);
        }
        return  list;
    }

    /**
     * 保存技工院校信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveSchool")
    @ResponseBody
    public String saveSchool(HttpServletRequest request,ModelMap modelMap,String schoolaab301,TechnicalSchools technicalSchools) throws Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(technicalSchools != null){
            if(!"".equals(technicalSchools.getThs001()) && technicalSchools.getThs001() != null){
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(technicalSchools.getThs002());
                if(poorLaborForce != null){
                    technicalSchools.setPhd013(poorLaborForce.getPhd013());
                    technicalSchools.setPhd014(poorLaborForce.getPhd014());
                    technicalSchools.setPlf004(poorLaborForce.getPlf004());
                    technicalSchools.setPlf005(poorLaborForce.getPlf005());
                }

                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    technicalSchools.setAae012(fpUser.getId());
                    technicalSchools.setUpdateby(fpUser.getRealname());
                    technicalSchools.setUpdatedate(new Date());
                }
                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                TechnicalSchools emp = technicalSchoolsService.queryNewTechnicalSchools(technicalSchools.getThs002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getThs005()) && emp.getThs005() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getThs005()).getTime() - DateUtil.parseDate(technicalSchools.getThs005()).getTime()>0){
                            technicalSchools.setIsnew("0");
                        }else{
                            technicalSchools.setIsnew("1");
                            //将是原来的否最新修改为0
                            technicalSchoolsService.updateIsNewByThs002(technicalSchools.getThs002());
                        }
                    }
                }else{
                    technicalSchools.setIsnew("1");//说明之前没有值
                }

                technicalSchoolsService.updateByPrimaryKeySelective(technicalSchools);
                //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = "update";
            }else{
                //根据劳动力id 获取劳动力信息
                PoorLaborForce poorLaborForce = poorLaborForceService.selectByPrimaryKey(technicalSchools.getThs002());
                if(poorLaborForce != null){
                    technicalSchools.setPhd013(poorLaborForce.getPhd013());
                    technicalSchools.setPhd014(poorLaborForce.getPhd014());
                    technicalSchools.setPlf004(poorLaborForce.getPlf004());
                    technicalSchools.setPlf005(poorLaborForce.getPlf005());
                }
                technicalSchools.setThs001(UUIDGenerator.generate().toString());
                technicalSchools.setAae036(new Date());
                technicalSchools.setDatasource("1");//1代表录入，2代表导入
                technicalSchools.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    technicalSchools.setAae011(fpUser.getId());
                    technicalSchools.setCreateby(fpUser.getRealname());
                    if(!"".equals(schoolaab301) && schoolaab301 != null){
                        technicalSchools.setAab301(schoolaab301);
                    }else{
                        technicalSchools.setAab301(fpUser.getAab301());
                    }
                }

                //根据劳动力id  获取最新的一条创业信息，和当前的比较，看那个是最新的
                TechnicalSchools emp = technicalSchoolsService.queryNewTechnicalSchools(technicalSchools.getThs002());
                //比较两个日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(emp != null){
                    if(!"".equals(emp.getThs005()) && emp.getThs005() != null){ //说明数据库中的值大
                        if(DateUtil.parseDate(emp.getThs005()).getTime() - DateUtil.parseDate(technicalSchools.getThs005()).getTime()>0){
                            technicalSchools.setIsnew("0");
                        }else{
                            technicalSchools.setIsnew("1");
                            //将是原来的否最新修改为0
                            technicalSchoolsService.updateIsNewByThs002(technicalSchools.getThs002());
                        }
                    }
                }else{
                    technicalSchools.setIsnew("1");//说明之前没有值
                }

                technicalSchoolsService.insertSelective(technicalSchools);
                //根据phd001 更新创业phd014_19 是否19年剩余贫困户
                poorWorkService.updateEysEppTsnThsByPlf001(poorLaborForce.getPlf002());
                flag = technicalSchools.getThs001();
            }
        }
        return flag;
    }

    /**
     * 修改技工院校信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateSchool")
    @ResponseBody
    public TechnicalSchools updateSchool(HttpServletRequest request,String ths001,ModelMap modelMap){
        TechnicalSchools technicalSchools = null;
        if(!"".equals(ths001) && ths001 != null){
            technicalSchools = technicalSchoolsService.selectByPrimaryKey(ths001);
        }
        return technicalSchools;
    }


    /**
     * 删除技工院校信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteSchool")
    @ResponseBody
    public String deleteSchool(HttpServletRequest request,String ths001){
        int s = 0;
        if(!"".equals(ths001) && ths001 != null){
            TechnicalSchools technicalSchools = null;
            technicalSchools = technicalSchoolsService.selectByPrimaryKey(ths001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            technicalSchools.setDeleteby(fpUser.getRealname());
            technicalSchools.setDeletedate(new Date());
            technicalSchoolsService.updateByPrimaryKeySelective(technicalSchools);

            s = technicalSchoolsService.deleteByPrimaryKey(ths001);

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据劳动力id 删除技工院校信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteTechnicalSchoolsByThs002")
    @ResponseBody
    public String deleteTechnicalSchoolsByThs002(HttpServletRequest request,String ths002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(ths002) && ths002 != null){
            s = technicalSchoolsService.deleteTechnicalSchoolsByThs002(ths002,fpUser.getRealname());

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







    /**
     * 保存户主信息变更
     * @return
     */
    @RequestMapping("savePoorChange")
    @ResponseBody
    public String savePoorChange(HttpServletRequest request, String treeid,ModelMap modelMap,PoorHouseholds poorHouseholds){
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){
            flag="login";
            return flag;
        }
        if(poorHouseholds != null){
            if(!"".equals(poorHouseholds.getPhd001()) && poorHouseholds.getPhd001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorHouseholds.setAae012(fpUser.getId());
                    poorHouseholds.setUpdateby(fpUser.getRealname());
                    poorHouseholds.setUpdatedate(new Date());
                }
                PoorHouseholds poorold = poorWorkService.selectByPrimaryKey(poorHouseholds.getPhd001());
                poorold.setPhd002(poorHouseholds.getPhd002());
                poorold.setPhd003(poorHouseholds.getPhd003());
                poorold.setPhd004(poorHouseholds.getPhd004());
                poorold.setPhd005(poorHouseholds.getPhd005());
                poorold.setPhd006(poorHouseholds.getPhd006());



                PoorChangeRecord poorChangeRecord = new PoorChangeRecord();
                poorChangeRecord.setPcr001(UUIDGenerator.generate().toString());
                poorChangeRecord.setPcr002(poorHouseholds.getPhd001());
                poorChangeRecord.setPcr003(poorHouseholds.getPhd002_old());
                poorChangeRecord.setPcr004(poorHouseholds.getPhd003_old());
                poorChangeRecord.setPcr006(poorHouseholds.getPhd002());
                poorChangeRecord.setPcr007(poorHouseholds.getPhd003());
                poorChangeRecord.setPcr008(poorHouseholds.getChangereason());
                poorChangeRecord.setAae036(new Date());
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    poorChangeRecord.setAae011(fpUser.getId());
                    poorChangeRecord.setCreateby(fpUser.getRealname());
                }
                poorChangeRecordService.insertSelective(poorChangeRecord);
                poorWorkService.updateByPrimaryKeySelective(poorold);
                flag = "update";
            }
        }
        return flag;

    }



    /**
     * 根 户主id  查询户主变更列表
     */
    @RequestMapping("queryPoorChangeByPcr002")
    @ResponseBody
    public List<PoorChangeRecord> queryPoorChangeByPcr002(HttpServletRequest request,String phd001){
        List<PoorChangeRecord> list = null;
        if(!"".equals(phd001) && phd001 != null){
            list = poorChangeRecordService.queryPoorChangeByPcr002(phd001);
        }
        return  list;
    }


/**********************************以下是就业补贴信息*************************************************************************************************/

    /**
     * 根据劳动力信息id  查询就业信息列表
     */
    @RequestMapping("queryJiuYeSubsidyByEts003")
    @ResponseBody
    public List<EmploymentSubsidy> queryJiuYeSubsidyByEts003(HttpServletRequest request,String ets003){
        List<EmploymentSubsidy> list = null;
        if(!"".equals(ets003) && ets003 != null){
            list = employmentSubsidyService.querEmploymentSubsidyByEts003(ets003);
        }
        return  list;
    }


    @RequestMapping("/kill")
    @ResponseBody
    public String kill(HttpServletRequest request,ModelMap modelMap,String jiuyeaab301,EmploymentSubsidy employmentSubsidy){
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(employmentSubsidy != null){
            if(!"".equals(employmentSubsidy.getEts001()) && employmentSubsidy.getEts001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSubsidy.setAae012(fpUser.getId());
                    employmentSubsidy.setUpdateby(fpUser.getRealname());
                    employmentSubsidy.setUpdatedate(new Date());
                }

                employmentSubsidyService.updateByPrimaryKeySelective(employmentSubsidy);
                String ets002 = employmentSubsidy.getEts002();//就业id
                EmploymentSubsidy subsidycount = employmentSubsidyService.querEmploymentSubsidyCountByEts002(ets002);

                EmploymentSituation employmentSituation = employmentSituationService.selectByPrimaryKey(ets002);
                employmentSituation.setEys020(subsidycount.getEts005());
                employmentSituation.setEys021(subsidycount.getEts006());
                employmentSituation.setEys023(subsidycount.getEts008());
                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);

                flag = "update";
            }else{
                employmentSubsidy.setEts001(UUIDGenerator.generate().toString());
                employmentSubsidy.setAae036(new Date());
                employmentSubsidy.setDatasource("1");
                employmentSubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSubsidy.setAae011(fpUser.getId());
                    employmentSubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(jiuyeaab301) && jiuyeaab301 != null){
                        employmentSubsidy.setAab301(jiuyeaab301);
                    }else{
                        employmentSubsidy.setAab301(fpUser.getAab301());
                    }
                }

                employmentSubsidyService.insertSelective(employmentSubsidy);

                String ets002 = employmentSubsidy.getEts002();//就业id
                EmploymentSubsidy subsidycount = employmentSubsidyService.querEmploymentSubsidyCountByEts002(ets002);

                EmploymentSituation employmentSituation = employmentSituationService.selectByPrimaryKey(ets002);
                employmentSituation.setEys020(subsidycount.getEts005());
                employmentSituation.setEys021(subsidycount.getEts006());
                employmentSituation.setEys023(subsidycount.getEts008());
                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);

                flag = employmentSubsidy.getEts001();
            }
        }
        employmentSituationService.updateByPrimaryKeySelectiveKill();
        return flag;
    }


    /**
     * 保存就业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveJiuYeSubsidy")
    @ResponseBody
    public String saveJiuYeSubsidy(HttpServletRequest request,ModelMap modelMap,String jiuyeaab301,EmploymentSubsidy employmentSubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(employmentSubsidy != null){
            if(!"".equals(employmentSubsidy.getEts001()) && employmentSubsidy.getEts001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSubsidy.setAae012(fpUser.getId());
                    employmentSubsidy.setUpdateby(fpUser.getRealname());
                    employmentSubsidy.setUpdatedate(new Date());
                }

                employmentSubsidyService.updateByPrimaryKeySelective(employmentSubsidy);
                String ets002 = employmentSubsidy.getEts002();//就业id
                EmploymentSubsidy subsidycount = employmentSubsidyService.querEmploymentSubsidyCountByEts002(ets002);

                EmploymentSituation employmentSituation = employmentSituationService.selectByPrimaryKey(ets002);
                employmentSituation.setEys020(subsidycount.getEts005());
                employmentSituation.setEys021(subsidycount.getEts006());
                employmentSituation.setEys023(subsidycount.getEts008());
                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);

                flag = "update";
            }else{
                employmentSubsidy.setEts001(UUIDGenerator.generate().toString());
                employmentSubsidy.setAae036(new Date());
                employmentSubsidy.setDatasource("1");
                employmentSubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    employmentSubsidy.setAae011(fpUser.getId());
                    employmentSubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(jiuyeaab301) && jiuyeaab301 != null){
                        employmentSubsidy.setAab301(jiuyeaab301);
                    }else{
                        employmentSubsidy.setAab301(fpUser.getAab301());
                    }
                }

                employmentSubsidyService.insertSelective(employmentSubsidy);

                String ets002 = employmentSubsidy.getEts002();//就业id
                EmploymentSubsidy subsidycount = employmentSubsidyService.querEmploymentSubsidyCountByEts002(ets002);

                EmploymentSituation employmentSituation = employmentSituationService.selectByPrimaryKey(ets002);
                employmentSituation.setEys020(subsidycount.getEts005());
                employmentSituation.setEys021(subsidycount.getEts006());
                employmentSituation.setEys023(subsidycount.getEts008());
                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);

                flag = employmentSubsidy.getEts001();
            }
        }
        return flag;
    }


    /**
     * 修改就业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateJiuYeSubsidy")
    @ResponseBody
    public EmploymentSubsidy updateJiuYeSubsidy(HttpServletRequest request,String ets001,ModelMap modelMap){
        EmploymentSubsidy employmentSubsidy = null;
        if(!"".equals(ets001) && ets001 != null){
            employmentSubsidy = employmentSubsidyService.selectByPrimaryKey(ets001);
        }
        return employmentSubsidy;
    }

    /**
     * 根据  补贴 id
     * 删除就业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteJiuYeSubsidyByEts001")
    @ResponseBody
    public String deleteJiuYeSubsidyByEts001(HttpServletRequest request,String ets001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(ets001) && ets001 != null){

            s = employmentSubsidyService.deleteByPrimaryKey(ets001,fpUser.getRealname());

            EmploymentSubsidy subsidy = employmentSubsidyService.selectByPrimaryKey(ets001);
            String ets002 = subsidy.getEts002();//就业id
            if(!"".equals(ets002) && ets002 != null){
                EmploymentSubsidy subsidycount = employmentSubsidyService.querEmploymentSubsidyCountByEts002(ets002);

                EmploymentSituation employmentSituation = employmentSituationService.selectByPrimaryKey(ets002);
                if(subsidy != null && subsidycount != null){
                    employmentSituation.setEys020(subsidycount.getEts005());
                    employmentSituation.setEys021(subsidycount.getEts006());
                    employmentSituation.setEys023(subsidycount.getEts008());
                }else{
                    employmentSituation.setEys020("");
                    employmentSituation.setEys021("");
                    employmentSituation.setEys023("");
                }

                employmentSituationService.updateByPrimaryKeySelective(employmentSituation);
            }

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据劳动力  就业id
     * 删除就业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteJiuYeSubsidyByEts002")
    @ResponseBody
    public String deleteJiuYeSubsidyByEts002(HttpServletRequest request,String ets002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(ets002) && ets002 != null){
            s = employmentSubsidyService.deleteJiuYeSubsidyByEts002(ets002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }



/**********************************以下是创业补贴信息*************************************************************************************************/

    /**
     * 根据劳动力信息id  查询创业补贴信息列表
     */
    @RequestMapping("queryChuangYeSubsidyByVes003")
    @ResponseBody
    public List<VentureSubsidy> queryChuangYeSubsidyByVes003(HttpServletRequest request,String ves003){
        List<VentureSubsidy> list = null;
        if(!"".equals(ves003) && ves003 != null){
            list = ventureSubsidyService.queryChuangYeSubsidyByVes003(ves003);
        }
        return  list;
    }


    /**
     * 保存创业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveChuangYeSubsidy")
    @ResponseBody
    public String saveChuangYeSubsidy(HttpServletRequest request,ModelMap modelMap,String chuangyeaab301,VentureSubsidy ventureSubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(ventureSubsidy != null){
            if(!"".equals(ventureSubsidy.getVes001()) && ventureSubsidy.getVes001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    ventureSubsidy.setAae012(fpUser.getId());
                    ventureSubsidy.setUpdateby(fpUser.getRealname());
                    ventureSubsidy.setUpdatedate(new Date());
                }

                ventureSubsidyService.updateByPrimaryKeySelective(ventureSubsidy);
                String ves002 =ventureSubsidy.getVes002();
                VentureSubsidy subsidy = ventureSubsidyService.queryChuangYeSubsidyCountByVes002(ves002);

                Entrepreneurship entrepreneurship = entrepreneurshipService.selectByPrimaryKey(ves002);
                entrepreneurship.setEpp014(subsidy.getVes005());
                entrepreneurship.setEpp015(subsidy.getVes006());
                entrepreneurship.setEpp016(subsidy.getVes010());
                entrepreneurshipService.updateByPrimaryKeySelective(entrepreneurship);

                flag = "update";
            }else{
                ventureSubsidy.setVes001(UUIDGenerator.generate().toString());
                ventureSubsidy.setAae036(new Date());
                ventureSubsidy.setDatasource("1");
                ventureSubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    ventureSubsidy.setAae011(fpUser.getId());
                    ventureSubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(chuangyeaab301) && chuangyeaab301 != null){
                        ventureSubsidy.setAab301(chuangyeaab301);
                    }else{
                        ventureSubsidy.setAab301(fpUser.getAab301());
                    }
                }

                ventureSubsidyService.insertSelective(ventureSubsidy);
                //根据创业id 查该人员的所有补贴信息，，保存到创业表中
                String ves002 =ventureSubsidy.getVes002();
                VentureSubsidy subsidy = ventureSubsidyService.queryChuangYeSubsidyCountByVes002(ves002);

                Entrepreneurship entrepreneurship = entrepreneurshipService.selectByPrimaryKey(ves002);
                entrepreneurship.setEpp014(subsidy.getVes005());
                entrepreneurship.setEpp015(subsidy.getVes006());
                entrepreneurship.setEpp016(subsidy.getVes010());
                entrepreneurshipService.updateByPrimaryKeySelective(entrepreneurship);

                flag = ventureSubsidy.getVes001();
            }
        }
        return flag;
    }


    /**
     * 修改创业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateChuangYeSubsidy")
    @ResponseBody
    public VentureSubsidy updateChuangYeSubsidy(HttpServletRequest request,String ves001,ModelMap modelMap){
        VentureSubsidy ventureSubsidy = null;
        if(!"".equals(ves001) && ves001 != null){
            ventureSubsidy = ventureSubsidyService.selectByPrimaryKey(ves001);
        }
        return ventureSubsidy;
    }

    /**
     * 根据  补贴 id
     * 删除创业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteChuangYeSubsidyByVes001")
    @ResponseBody
    public String deleteChuangYeSubsidyByVes001(HttpServletRequest request,String ves001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(ves001) && ves001 != null){
            s = ventureSubsidyService.deleteByPrimaryKey(ves001,fpUser.getRealname());

            VentureSubsidy ventureSubsidy = ventureSubsidyService.selectByPrimaryKey(ves001);
            String ves002 =ventureSubsidy.getVes002();
            if(!"".equals(ves002) && ves002 != null){
                VentureSubsidy subsidy = ventureSubsidyService.queryChuangYeSubsidyCountByVes002(ves002);

                Entrepreneurship entrepreneurship = entrepreneurshipService.selectByPrimaryKey(ves002);
                if(subsidy != null && ventureSubsidy != null){
                    entrepreneurship.setEpp014(subsidy.getVes005());//一次性
                    entrepreneurship.setEpp015(subsidy.getVes006());//创业担保贷款
                    entrepreneurship.setEpp016(subsidy.getVes010());//个人领取社保补贴
                }else{
                    entrepreneurship.setEpp014("");
                    entrepreneurship.setEpp015("");
                    entrepreneurship.setEpp016("");
                }

                entrepreneurshipService.updateByPrimaryKeySelective(entrepreneurship);
            }
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据  创业id
     * 删除创业补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteChuangYeSubsidyByVes002")
    @ResponseBody
    public String deleteChuangYeSubsidyByVes002(HttpServletRequest request,String ves002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(ves002) && ves002 != null){
            s = ventureSubsidyService.deleteChuangYeSubsidyByVes002(ves002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }



/**********************************以下是培训补贴信息*************************************************************************************************/

    /**
     * 根据劳动力信息id  查询培训补贴信息列表
     */
    @RequestMapping("queryPeiXunSubsidyByTgs003")
    @ResponseBody
    public List<TrainingSubsidy> queryPeiXunSubsidyByTgs003(HttpServletRequest request,String tgs003){
        List<TrainingSubsidy> list = null;
        if(!"".equals(tgs003) && tgs003 != null){
            list = trainingSubsidyService.queryPeiXunSubsidyByTgs003(tgs003);
        }
        return  list;
    }


    /**
     * 保存培训补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("savePeiXunSubsidy")
    @ResponseBody
    public String savePeiXunSubsidy(HttpServletRequest request,ModelMap modelMap,String peixunaab301,TrainingSubsidy trainingSubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(trainingSubsidy != null){
            if(!"".equals(trainingSubsidy.getTgs001()) && trainingSubsidy.getTgs001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    trainingSubsidy.setAae012(fpUser.getId());
                    trainingSubsidy.setUpdateby(fpUser.getRealname());
                    trainingSubsidy.setUpdatedate(new Date());
                }

                trainingSubsidyService.updateByPrimaryKeySelective(trainingSubsidy);
                //根据培训id 查该人员的所有补贴信息，
                String tgs002 =trainingSubsidy.getTgs002();
                TrainingSubsidy subsidy = trainingSubsidyService.queryPeiXunSubsidyCountByTgs002(tgs002);

                TrainingSituation trainingSituation = trainingSituationService.selectByPrimaryKey(tgs002);
                trainingSituation.setTsn016(subsidy.getTgs005());
                trainingSituationService.updateByPrimaryKeySelective(trainingSituation);

                flag = "update";
            }else{
                trainingSubsidy.setTgs001(UUIDGenerator.generate().toString());
                trainingSubsidy.setAae036(new Date());
                trainingSubsidy.setDatasource("1");
                trainingSubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    trainingSubsidy.setAae011(fpUser.getId());
                    trainingSubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(peixunaab301) && peixunaab301 != null){
                        trainingSubsidy.setAab301(peixunaab301);
                    }else{
                        trainingSubsidy.setAab301(fpUser.getAab301());
                    }
                }

                trainingSubsidyService.insertSelective(trainingSubsidy);

                //根据培训id 查该人员的所有补贴信息，
                String tgs002 =trainingSubsidy.getTgs002();
                TrainingSubsidy subsidy = trainingSubsidyService.queryPeiXunSubsidyCountByTgs002(tgs002);

                TrainingSituation trainingSituation = trainingSituationService.selectByPrimaryKey(tgs002);
                trainingSituation.setTsn016(subsidy.getTgs005());
                trainingSituationService.updateByPrimaryKeySelective(trainingSituation);

                flag = trainingSubsidy.getTgs001();
            }
        }
        return flag;
    }


    /**
     * 修改培训补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updatePeiXunSubsidy")
    @ResponseBody
    public TrainingSubsidy updatePeiXunSubsidy(HttpServletRequest request,String tgs001,ModelMap modelMap){
        TrainingSubsidy trainingSubsidy = null;
        if(!"".equals(tgs001) && tgs001 != null){
            trainingSubsidy = trainingSubsidyService.selectByPrimaryKey(tgs001);
        }
        return trainingSubsidy;
    }

    /**
     * 根据  补贴 id
     * 删除培训补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deletePeiXunSubsidyByTgs001")
    @ResponseBody
    public String deletePeiXunSubsidyByTgs001(HttpServletRequest request,String tgs001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(tgs001) && tgs001 != null){
            s = trainingSubsidyService.deleteByPrimaryKey(tgs001,fpUser.getRealname());

            TrainingSubsidy trainingSubsidy = trainingSubsidyService.selectByPrimaryKey(tgs001);
            //根据培训id 查该人员的所有补贴信息，
            String tgs002 =trainingSubsidy.getTgs002();
            if(!"".equals(tgs002) && tgs002 != null){
                TrainingSubsidy subsidy = trainingSubsidyService.queryPeiXunSubsidyCountByTgs002(tgs002);

                TrainingSituation trainingSituation = trainingSituationService.selectByPrimaryKey(tgs002);
                if(subsidy != null && trainingSubsidy != null){
                    trainingSituation.setTsn016(subsidy.getTgs005());
                }else{
                    trainingSituation.setTsn016("");
                }

                trainingSituationService.updateByPrimaryKeySelective(trainingSituation);
            }

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据  培训id
     * 删除培训补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deletePeiXunSubsidyByTgs002")
    @ResponseBody
    public String deletePeiXunSubsidyByTgs002(HttpServletRequest request,String tgs002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(tgs002) && tgs002 != null){
            s = trainingSubsidyService.deletePeiXunSubsidyByTgs002(tgs002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }



/**********************************以下是技工院校补贴信息*************************************************************************************************/

    /**
     * 根据劳动力信息id  查询技校补贴信息列表
     */
    @RequestMapping("querySchoolSubsidyByTss003")
    @ResponseBody
    public List<TechnicalSchoolSubsidy> querySchoolSubsidyByTss003(HttpServletRequest request,String tss003){
        List<TechnicalSchoolSubsidy> list = null;
        if(!"".equals(tss003) && tss003 != null){
            list = technicalSchoolSubsidyService.querySchoolSubsidyByTss003(tss003);
        }
        return  list;
    }


    /**
     * 保存技校补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("saveSchoolSubsidy")
    @ResponseBody
    public String saveSchoolSubsidy(HttpServletRequest request,ModelMap modelMap,String schoolaab301,TechnicalSchoolSubsidy technicalSchoolSubsidy) throws  Exception{
        String flag = "";
        if(request.getSession().getAttribute("fpUser") == null){   //没有渠道session中的值   提示重新登录
            flag="login";
            return flag;
        }
        if(technicalSchoolSubsidy != null){
            if(!"".equals(technicalSchoolSubsidy.getTss001()) && technicalSchoolSubsidy.getTss001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    technicalSchoolSubsidy.setAae012(fpUser.getId());
                    technicalSchoolSubsidy.setUpdateby(fpUser.getRealname());
                    technicalSchoolSubsidy.setUpdatedate(new Date());
                }

                technicalSchoolSubsidyService.updateByPrimaryKeySelective(technicalSchoolSubsidy);
                String tss002 = technicalSchoolSubsidy.getTss002();
                TechnicalSchoolSubsidy subsidy = technicalSchoolSubsidyService.querySchoolSubsidyCountByTss002(tss002);

                TechnicalSchools technicalSchools = technicalSchoolsService.selectByPrimaryKey(tss002);
                technicalSchools.setThs015(subsidy.getTss005());
                technicalSchools.setThs014(subsidy.getTss006());
                technicalSchools.setThs016(subsidy.getTss007());
                technicalSchoolsService.updateByPrimaryKeySelective(technicalSchools);

                flag = "update";
            }else{
                technicalSchoolSubsidy.setTss001(UUIDGenerator.generate().toString());
                technicalSchoolSubsidy.setAae036(new Date());
                technicalSchoolSubsidy.setDatasource("1");
                technicalSchoolSubsidy.setAae100("1");

                FpUser fpUser = null;

                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    technicalSchoolSubsidy.setAae011(fpUser.getId());
                    technicalSchoolSubsidy.setCreateby(fpUser.getRealname());
                    if(!"".equals(schoolaab301) && schoolaab301 != null){
                        technicalSchoolSubsidy.setAab301(schoolaab301);
                    }else{
                        technicalSchoolSubsidy.setAab301(fpUser.getAab301());
                    }
                }

                technicalSchoolSubsidyService.insertSelective(technicalSchoolSubsidy);

                String tss002 = technicalSchoolSubsidy.getTss002();
                TechnicalSchoolSubsidy subsidy = technicalSchoolSubsidyService.querySchoolSubsidyCountByTss002(tss002);

                TechnicalSchools technicalSchools = technicalSchoolsService.selectByPrimaryKey(tss002);
                technicalSchools.setThs015(subsidy.getTss005());
                technicalSchools.setThs014(subsidy.getTss006());
                technicalSchools.setThs016(subsidy.getTss007());
                technicalSchoolsService.updateByPrimaryKeySelective(technicalSchools);

                flag = technicalSchoolSubsidy.getTss001();
            }
        }
        return flag;
    }




    /**
     * 修改技校补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("updateSchoolSubsidy")
    @ResponseBody
    public TechnicalSchoolSubsidy updateSchoolSubsidy(HttpServletRequest request,String tss001,ModelMap modelMap){
        TechnicalSchoolSubsidy technicalSchoolSubsidy = null;
        if(!"".equals(tss001) && tss001 != null){
            technicalSchoolSubsidy = technicalSchoolSubsidyService.selectByPrimaryKey(tss001);
        }
        return technicalSchoolSubsidy;
    }

    /**
     * 根据  补贴 id
     * 删除技校补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteSchoolSubsidyByTss001")
    @ResponseBody
    public String deleteSchoolSubsidyByTss001(HttpServletRequest request,String tss001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(tss001) && tss001 != null){
            s = technicalSchoolSubsidyService.deleteByPrimaryKey(tss001,fpUser.getRealname());

            TechnicalSchoolSubsidy technicalSchoolSubsidy =technicalSchoolSubsidyService.selectByPrimaryKey(tss001);
            String tss002 = technicalSchoolSubsidy.getTss002();
            if(!"".equals(tss002) && tss002 != null){
                TechnicalSchoolSubsidy subsidy = technicalSchoolSubsidyService.querySchoolSubsidyCountByTss002(tss002);

                TechnicalSchools technicalSchools = technicalSchoolsService.selectByPrimaryKey(tss002);
                if(subsidy != null && technicalSchoolSubsidy != null){
                    technicalSchools.setThs015(subsidy.getTss005());
                    technicalSchools.setThs014(subsidy.getTss006());
                    technicalSchools.setThs016(subsidy.getTss007());
                }else{
                    technicalSchools.setThs015("");
                    technicalSchools.setThs014("");
                    technicalSchools.setThs016("");
                }

                technicalSchoolsService.updateByPrimaryKeySelective(technicalSchools);
            }

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据  培训id
     * 删除技校补贴信息
     * poorLaborForce
     * @return
     */
    @RequestMapping("deleteSchoolSubsidyByTss002")
    @ResponseBody
    public String deleteSchoolSubsidyByTss002(HttpServletRequest request,String tss002){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(tss002) && tss002 != null){
            s = technicalSchoolSubsidyService.deleteSchoolSubsidyByTss002(tss002,fpUser.getRealname());
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * g根据aab301 查询   总户数，贫困人口数，贫困劳动力数
     * @throws Exception
     */
    @RequestMapping("/queryZtreeCountNew")
    @ResponseBody
    public PoorXzqh queryZtreeCountNew(HttpServletRequest request, Model model, String aab301) throws Exception {
        aab301 =  aab301Substr(aab301)+ "%";
        PoorLaborForce poorLaborForce = new PoorLaborForce();
        poorLaborForce.setAab301(aab301);
        String workercount = poorLaborForceService.queryAllPoorPersonAccountByAab301(poorLaborForce);//所有贫困人口
        PoorHouseholds poorHouseholds = new PoorHouseholds();
        poorHouseholds.setAab301(aab301);
        String poorcount = poorWorkService.queryAllAccoutByAab301(poorHouseholds);//贫困户数
        String poorworkercount = poorLaborForceService.queryAllPoorWorkerAccountByAab301(poorLaborForce);//贫困劳动力数
        PoorXzqh poorXzqh = new PoorXzqh();
        poorXzqh.setPoor_hu(poorcount);
        poorXzqh.setConfirm_worker(workercount);//所有贫困户人数
        poorXzqh.setConfirm_poor_hu(poorworkercount);//贫困劳动力数
        return poorXzqh;
    }

}
