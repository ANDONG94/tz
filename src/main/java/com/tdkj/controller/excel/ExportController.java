package com.tdkj.controller.excel;

import com.tdkj.model.*;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据导出
 */
@RestController
@RequestMapping("/import")
public class ExportController {

    @Autowired
    private PoorLaborForceExcelService poorLaborForceExcelService;
    @Autowired
    private PoorWorkExcelService poorWorkExcelService;
    @Autowired
    private EmploymentSintuationExcelService employmentSintuationExcelService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EntrepreneurShipExcelService entrepreneurShipExcelService;
    @Autowired
    private TrainingSituationExcelService trainingSituationExcelService;
    @Autowired
    private TechnicalSchoolsExcelService technicalSchoolsExcelService;
    @Autowired
    private CommunityfactoryExcelService communityfactoryExcelService;
    @Autowired
    private PovertyAlleviationBaseExcelService povertyAlleviationBaseExcelService;
    @Autowired
    private IncubatorBaseAccountExcelService incubatorBaseAccountExcelService;
    @Autowired
    private JobFairAccountExcelService jobFairAccountExcelService;
    @Autowired
    private CreditVillageAccountExcelService creditVillageAccountExcelService;
    /**
     * 贫困户导出
     * @throws Exception
     */
    @RequestMapping(value = "/importPoorWork")
    public void exportXlsxPoorWork(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorHouseholds poorHouseholds = (PoorHouseholds)JSONObject.toBean(jsonobject, PoorHouseholds.class);
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
            if ("".equals(poorHouseholds.getPhd008_yes())) {
                poorHouseholds.setPhd008_yes("99");
            }
            poorHouseholds.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }

        poorWorkExcelService.export(poorHouseholds);
    }

    /**
     * 待帮扶贫困户导出
     * @param param
     * @throws Exception
     */
    @RequestMapping(value = "/exportTobeHelpPoor")
    public void exportTobeHelpPoor(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorHouseholds poorHouseholds = (PoorHouseholds)JSONObject.toBean(jsonobject, PoorHouseholds.class);
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
            if ("".equals(poorHouseholds.getPhd008_yes())) {
                poorHouseholds.setPhd008_yes("99");
            }
            poorHouseholds.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }

        poorWorkExcelService.exportTobeHelpPoor(poorHouseholds);
    }


    /**
     *
     * @param treeid
     * @param phd002_scan
     * @param phd003_scan
     * @param phd012_scan
     * @param response
     * @throws Exception
     * 贫困户待确认导出
     */
    @RequestMapping(value = "/exportToBeComfirePoorWork")
    public void exportToBeComfirePoorWork(String treeid,String phd002_scan,String phd003_scan,String phd012_scan, HttpServletResponse response) throws Exception {
        if(!"".equals(treeid) && treeid != null){
            treeid =  aab301Substr(treeid)+ "%";
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            treeid = fpUser.getAab301();
            treeid =  aab301Substr(treeid)+ "%";
        }
        PoorHouseholds poorHouseholds = new PoorHouseholds();
        poorHouseholds.setAab301(treeid);
        poorWorkExcelService.exportToBeComfirePoorWork(poorHouseholds);
    }


    /**
     * 贫困劳动力导出
     * @param treeid
     * @param phd002_scan
     * @param phd003_scan
     * @param phd012_scan
     * @param response
     * @throws Exception
     */
    @GetMapping("/importPoorLaborForce")
    public void exportXlsxPoorLaborForce(String treeid, String phd002_scan, String phd003_scan, String phd012_scan, HttpServletResponse response) throws Exception{

        PoorLaborForce poorLaborForce = new PoorLaborForce();
        poorLaborForce.setAab301(treeid);
        poorLaborForceExcelService.export(poorLaborForce);
    }

    /**
     * 贫困劳动力台账导出
     * @throws Exception
     */
    @GetMapping("/importPoorLaborForceLedger")
    public void exportXlsxPoorLaborForceLedger(String param) throws Exception{
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorLaborForce poorLaborForce = (PoorLaborForce)JSONObject.toBean(jsonobject, PoorLaborForce.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        poorLaborForceExcelService.exportLedger(poorLaborForce);
    }

    /**
     * 贫困人口导出
     * @throws Exception
     */
    @GetMapping("/importPoorLaborForceAccount")
    public void importPoorLaborForceAccount(String param) throws Exception{
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorLaborForce poorLaborForce = (PoorLaborForce)JSONObject.toBean(jsonobject, PoorLaborForce.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        poorLaborForceExcelService.exportLedgerAccount(poorLaborForce);
    }


    /**
     * 弱贫困劳动力台账导出
     * @throws Exception
     */
    @GetMapping("/exortRuoPoorLaborForceLedger")
    public void exortRuoPoorLaborForceLedger(String param) throws Exception{
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorLaborForce poorLaborForce = (PoorLaborForce)JSONObject.toBean(jsonobject, PoorLaborForce.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        poorLaborForceExcelService.exortRuoPoorLaborForceLedger(poorLaborForce);
    }



/**
 * 未就业创业  劳动力台账导出
 * @throws Exception
 */
    @GetMapping("/exportTobeWorker")
    public void exportTobeWorker(String param) throws Exception{
        JSONObject jsonobject = JSONObject.fromObject(param);
        PoorLaborForce poorLaborForce = (PoorLaborForce)JSONObject.toBean(jsonobject, PoorLaborForce.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorLaborForce.getAab301()) && poorLaborForce.getAab301() != null){
                aab301 =  aab301Substr(poorLaborForce.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            poorLaborForce.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        poorLaborForceExcelService.exportTobeWorker(poorLaborForce);
    }


    /**
     * 贫困户待确认账导出
     * @param treeid
     * @param phd002_scan
     * @param phd003_scan
     * @param phd012_scan
     * @param response
     * @throws Exception
     */
    @GetMapping("/exportToBeComfirePoorLaborForce")
    public void exportToBeComfirePoorLaborForce(String treeid, String phd002_scan, String phd003_scan, String phd012_scan, HttpServletResponse response) throws Exception{
        if(!"".equals(treeid) && treeid != null){
            treeid =  aab301Substr(treeid)+ "%";
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            treeid = fpUser.getAab301();
            treeid =  aab301Substr(treeid)+ "%";
        }
        PoorLaborForce poorLaborForce = new PoorLaborForce();
        poorLaborForce.setAab301(treeid);
        poorLaborForceExcelService.exportToBeComfirePoorLaborForce(poorLaborForce);
    }


    /**
     * 家庭成员导出
     * @param treeid
     * @param phd002_scan
     * @param phd003_scan
     * @param phd012_scan
     * @param response
     * @throws Exception
     */
    @GetMapping("/importPoorLaborForceFamily")
    public void exportXlsxPoorLaborForceFamily(String treeid, String phd002_scan, String phd003_scan,String phd014_19, String phd012_scan, HttpServletResponse response) throws Exception{
        if(!"".equals(treeid) && treeid != null){
            treeid =  aab301Substr(treeid)+ "%";
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            treeid = fpUser.getAab301();
            treeid =  aab301Substr(treeid)+ "%";
        }
        PoorLaborForce poorLaborForce = new PoorLaborForce();
        poorLaborForce.setAab301(treeid);

        poorLaborForceExcelService.exportFamily(poorLaborForce);
    }

    /**
     * 公益性岗位信息台账
     */
    @GetMapping("/exportPubilcPostEmploymentSintuation")
    public void exportPubilcPostEmploymentSintuation(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        EysPlf eysPlf = (EysPlf)JSONObject.toBean(jsonobject, EysPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(eysPlf.getAab301()) && eysPlf.getAab301() != null){
                aab301 =  aab301Substr(eysPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            eysPlf.setAab301(aab301);

            if(!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null){
                eysPlf.setPlf004("%"+eysPlf.getPlf004()+"%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        employmentSintuationExcelService.exportPublicPost(eysPlf);
    }


    /**
     * 就业信息台账
     */
    @GetMapping("/importEmploymentSintuation")
    public void exportXlsxEmploymentSintuation(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        EysPlf eysPlf = (EysPlf)JSONObject.toBean(jsonobject, EysPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(eysPlf.getAab301()) && eysPlf.getAab301() != null){
                aab301 =  aab301Substr(eysPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            eysPlf.setAab301(aab301);

            if(!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null){
                eysPlf.setPlf004("%"+eysPlf.getPlf004()+"%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        employmentSintuationExcelService.export(eysPlf);
    }


    /**
     * 弱劳动力就业信息台账
     */
    @GetMapping("/exportRuoEmploymentSintuation")
    public void exportRuoEmploymentSintuation(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        EysPlf eysPlf = (EysPlf)JSONObject.toBean(jsonobject, EysPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(eysPlf.getAab301()) && eysPlf.getAab301() != null){
                aab301 =  aab301Substr(eysPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            eysPlf.setAab301(aab301);

            if(!"".equals(eysPlf.getPlf004()) && eysPlf.getPlf004() != null){
                eysPlf.setPlf004("%"+eysPlf.getPlf004()+"%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        employmentSintuationExcelService.exportRuoEmploymentSintuation(eysPlf);
    }


    /**
     * 创业信息台账
     */
    @GetMapping("/importEntrepreneurShip")
    public void exportXlsxEntrepreneurShip(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        EppPlf eppPlf = (EppPlf)JSONObject.toBean(jsonobject, EppPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(eppPlf.getAab301()) && eppPlf.getAab301() != null){
                aab301 =  aab301Substr(eppPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            eppPlf.setAab301(aab301);

            if(!"".equals(eppPlf.getPlf004()) && eppPlf.getPlf004() != null){
                eppPlf.setPlf004("%"+eppPlf.getPlf004()+"%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        entrepreneurShipExcelService.export(eppPlf);
    }

    /**
     *培训信息台账
     */
    @GetMapping("/importTrainingSituation")
    public void exportXlsxTrainingSituation(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        TsnPlf tsnPlf = (TsnPlf)JSONObject.toBean(jsonobject, TsnPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(tsnPlf.getAab301()) && tsnPlf.getAab301() != null){
                aab301 =  aab301Substr(tsnPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            tsnPlf.setAab301(aab301);

            if(!"".equals(tsnPlf.getPlf004()) && tsnPlf.getPlf004() != null){
                tsnPlf.setPlf004("%"+tsnPlf.getPlf004()+"%");
            }
    } catch (Exception e){
        e.printStackTrace();
    }
        trainingSituationExcelService.export(tsnPlf);
    }

    /**
     *技工院校信息台账
     */
    @GetMapping("/importTechnicalSchools")
    public void exportXlsxTechnicalSchools(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        ThsPlf thsPlf = (ThsPlf)JSONObject.toBean(jsonobject, ThsPlf.class);
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(thsPlf.getAab301()) && thsPlf.getAab301() != null){
                aab301 =  aab301Substr(thsPlf.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            thsPlf.setAab301(aab301);

            if(!"".equals(thsPlf.getPlf004()) && thsPlf.getPlf004() != null){
                thsPlf.setPlf004("%"+thsPlf.getPlf004()+"%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        technicalSchoolsExcelService.export(thsPlf);
    }



    /**
     * 社区工厂台账
     */
    @GetMapping("/importCommunityfactory")
    public void exportXlsxCommunityfactory(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        CommunityFactory communityFactory= (CommunityFactory)JSONObject.toBean(jsonobject, CommunityFactory.class);
        try {
            String aab301 = "";
            if(!"".equals(communityFactory.getAab301()) && communityFactory.getAab301() != null){
                aab301 =  aab301Substr(communityFactory.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            communityFactory.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        communityfactoryExcelService.export(communityFactory);
    }


    /**
     * 扶贫基地台账
     */
    @GetMapping("/importPovertyalleviationbase")
    public void exportXlsxPovertyalleviationbase(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        PovertyAlleviationBase povertyAlleviationBase= (PovertyAlleviationBase)JSONObject.toBean(jsonobject, PovertyAlleviationBase.class);
        try {
            String aab301 = "";
            if(!"".equals(povertyAlleviationBase.getAab301()) && povertyAlleviationBase.getAab301() != null){
                aab301 =  aab301Substr(povertyAlleviationBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            povertyAlleviationBase.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        povertyAlleviationBaseExcelService.export(povertyAlleviationBase);
    }


    /**
     * 园区台账
     */
    @GetMapping("/importIncubatorBaseAccount")
    public void exportXlsxIncubatorBaseAccount(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        IncubatorBase incubatorBase= (IncubatorBase)JSONObject.toBean(jsonobject, IncubatorBase.class);
        try {
            String aab301 = "";
            if(!"".equals(incubatorBase.getAab301()) && incubatorBase.getAab301() != null){
                aab301 =  aab301Substr(incubatorBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            incubatorBase.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        incubatorBaseAccountExcelService.export(incubatorBase);
    }
    /**
     * 招聘会台账
     */
    @GetMapping("/importJobFairAccount")
    public void exportXlsxJobFairAccount(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        JobFair jobFair= (JobFair)JSONObject.toBean(jsonobject, JobFair.class);
        try {
            String aab301 = "";
            if(!"".equals(jobFair.getAab301()) && jobFair.getAab301() != null){
                aab301 =  aab301Substr(jobFair.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            jobFair.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        jobFairAccountExcelService.export(jobFair);
    }

    /**
     * 信用乡村
     */
    @GetMapping("/importCreditVillageAccount")
    public void exportXlsxCreditVillageAccount(String param) throws Exception {
        JSONObject jsonobject = JSONObject.fromObject(param);
        CreditVillage creditVillage= (CreditVillage)JSONObject.toBean(jsonobject, CreditVillage.class);
        try {
            String aab301 = "";
            if(!"".equals(creditVillage.getAab301()) && creditVillage.getAab301() != null){
                aab301 =  aab301Substr(creditVillage.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            creditVillage.setAab301(aab301);

        } catch (Exception e){
            e.printStackTrace();
        }
        creditVillageAccountExcelService.export(creditVillage);
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
