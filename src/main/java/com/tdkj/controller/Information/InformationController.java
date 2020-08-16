package com.tdkj.controller.Information;

import com.tdkj.model.*;
import com.tdkj.service.Information.*;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2019-04-29.
 * 综合类信息
 */
@Controller
@RequestMapping("Information")
public class InformationController {

    @Autowired
    private InformationService informationService;
    @Autowired
    private EntrepreneurialTeachersService entrepreneurialTeachersService;
    @Autowired
    private LaborBrokeringService laborBrokeringService;
    @Autowired
    private PublicServiceWorkstationService publicServiceWorkstationService;
    @Autowired
    private TakeTheLeadService takeTheLeadService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private LaborExportAgencyService laborExportAgencyService;
    @Autowired
    private ExSituPovertyService exSituPovertyService;
    @Autowired
    private LaborAlleviationCompanyService laborAlleviationCompanyService;
    @Autowired
    private JobInformationBaseService jobInformationBaseService;

    /**
     * 进入综合类页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "information/Information";
    }

    /**
     * 查询就综合类列表
     * @return
     */
    @RequestMapping("queryInformationByAab301")
    @ResponseBody
    public String queryInformationByAab301(HttpServletRequest request, PoorXzqh poorXzqh,HttpServletResponse response){
        try {

            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");
            String aab301 = "";
            if(!"".equals(poorXzqh.getAab301()) && poorXzqh.getAab301() != null){
                aab301 =  poorXzqh.getAab301();
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
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
            List<PoorXzqh> list = null;
            String countlist = "0";
            list= ztreeService.queryInformationByAab301(poorXzqh,startRecord+"",pageSize+"");
            countlist = list.size()+"";
            //countlist = ztreeService.queryAllInformationByAab301(poorXzqh);
            DataTableResultVO<PoorXzqh> result=new DataTableResultVO<>();
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
     * 保存综合类信息
     * @return
     */
    @RequestMapping("saveInformation")
    @ResponseBody
    public String saveInformation(HttpServletRequest request, ModelMap modelMap, Information information){
        String flag = "";
        if(information != null){
            if(!"".equals(information.getIft001()) && information.getIft001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    information.setAae012(fpUser.getId());
                    information.setUpdateby(fpUser.getRealname());
                    information.setUpdatedate(new Date());
                }
                informationService.updateByPrimaryKeySelective(information);
                flag = "update";
            }else{
                information.setIft001(UUIDGenerator.generate().toString());
                information.setAae036(new Date());
                information.setDatasource("1");//1代表录入，2代表导入
                information.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    information.setAae011(fpUser.getId());
                    information.setCreateby(fpUser.getRealname());
                    if(!"".equals(information.getAab301()) && information.getAab301() != null){
                    }else{
                        information.setAab301(fpUser.getAab301());
                    }
                }
                informationService.insertSelective(information);
                flag = "insert";
            }
        }
        return flag;

    }


    /**
     * 修改综合类信息
     * @return
     */
    @RequestMapping("updateInformation")
    @ResponseBody
    public Information updateInformation(HttpServletRequest request, String ift001,ModelMap modelMap){
        Information information = null;
        if(!"".equals(ift001) && ift001 != null){
            information = informationService.selectByPrimaryKey(ift001);
        }
        return information;
    }


    /**
     * 删除综合类信息
     * @return
     */
    @RequestMapping("delInformation")
    @ResponseBody
    public String delInformation(HttpServletRequest request, String ift001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(ift001) && ift001 != null){
            Information information = new Information();
            information = informationService.selectByPrimaryKey(ift001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            information.setDeleteby(fpUser.getRealname());
            information.setDeletedate(new Date());
            information.setAae100("0");
            s = informationService.updateByPrimaryKeySelective(information);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    //***************************创业带头人*****************************************************************************

    /**
     * 保存创业带头人信息
     * @param request
     * @param modelMap
     * @param takeTheLead
     * @return
     */
    @RequestMapping("saveChuangyeLeader")
    @ResponseBody
    public String saveChuangyeLeader(HttpServletRequest request, ModelMap modelMap, TakeTheLead takeTheLead){
        String flag = "";
        if(takeTheLead != null){
            if(!"".equals(takeTheLead.getThl001()) && takeTheLead.getThl001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    takeTheLead.setAae012(fpUser.getId());
                    takeTheLead.setUpdateby(fpUser.getRealname());
                    takeTheLead.setUpdatedate(new Date());
                }
                takeTheLeadService.updateByPrimaryKeySelective(takeTheLead);
                flag = "update";
            }else{
                takeTheLead.setThl001(UUIDGenerator.generate().toString());
                takeTheLead.setAae036(new Date());
                takeTheLead.setDatasource("1");//1代表录入，2代表导入
                takeTheLead.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    takeTheLead.setAae011(fpUser.getId());
                    takeTheLead.setCreateby(fpUser.getRealname());
                    if(!"".equals(takeTheLead.getAab301()) && takeTheLead.getAab301() != null){
                    }else{
                        takeTheLead.setAab301(fpUser.getAab301());
                    }
                }
                takeTheLeadService.insertSelective(takeTheLead);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改创业带头人信息
     * @return
     */
    @RequestMapping("updateChuangyeLeader")
    @ResponseBody
    public TakeTheLead updateChuangyeLeader(HttpServletRequest request, String thl001,ModelMap modelMap){
        TakeTheLead takeTheLead = null;
        if(!"".equals(thl001) && thl001 != null){
            takeTheLead = takeTheLeadService.selectByPrimaryKey(thl001);
        }
        return takeTheLead;
    }


    /**
     * 删除创业带头人信息
     * @return
     */
    @RequestMapping("delChuangyeLeader")
    @ResponseBody
    public String delChuangyeLeader(HttpServletRequest request, String thl001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(thl001) && thl001 != null){
            TakeTheLead takeTheLead = new TakeTheLead();
            takeTheLead = takeTheLeadService.selectByPrimaryKey(thl001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            takeTheLead.setDeleteby(fpUser.getRealname());
            takeTheLead.setDeletedate(new Date());
            takeTheLead.setAae100("0");
            s = takeTheLeadService.updateByPrimaryKeySelective(takeTheLead);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 创业带头人台账
     * @return
     */
    @RequestMapping("chuangyeLeaderList")
    public String chuangyeLeaderList(){
        return "accountInformation/take_the_lead_account_list";
    }


    /**
     * 根据aab301  查询创业带头人信息列表
     */
    @RequestMapping("queryChuangyeLeaderByAab301")
    @ResponseBody
    public String queryChuangyeLeaderByAab301(HttpServletRequest request,TakeTheLead takeTheLead){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(takeTheLead.getAab301()) && takeTheLead.getAab301() != null){
                aab301 =  aab301Substr(takeTheLead.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            takeTheLead.setAab301(aab301);

            if(!"".equals(takeTheLead.getThl003()) && takeTheLead.getThl003() != null){
                takeTheLead.setThl003(takeTheLead.getThl003()+"%");
            }
            if(!"".equals(takeTheLead.getThl012()) && takeTheLead.getThl012() != null){
                takeTheLead.setThl012(takeTheLead.getThl012()+"%");
            }

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<TakeTheLead> list= takeTheLeadService.queryChuangyeLeaderByAab301(takeTheLead,startRecord+"",pageSize+"");
            String countlist = takeTheLeadService.queryAllByAab301(takeTheLead);
            DataTableResultVO<TakeTheLead> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //***************************劳务经济人*****************************************************************************

    /**
     * 保存劳务经济人信息
     * @param request
     * @param modelMap
     * @param laborBrokering
     * @return
     */
    @RequestMapping("saveLaowuPerson")
    @ResponseBody
    public String saveLaowuPerson(HttpServletRequest request, ModelMap modelMap, LaborBrokering laborBrokering){
        String flag = "";
        if(laborBrokering != null){
            if(!"".equals(laborBrokering.getLbk001()) && laborBrokering.getLbk001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborBrokering.setAae012(fpUser.getId());
                    laborBrokering.setUpdateby(fpUser.getRealname());
                    laborBrokering.setUpdatedate(new Date());
                }
                laborBrokeringService.updateByPrimaryKeySelective(laborBrokering);
                flag = "update";
            }else{
                laborBrokering.setLbk001(UUIDGenerator.generate().toString());
                laborBrokering.setAae036(new Date());
                laborBrokering.setDatasource("1");//1代表录入，2代表导入
                laborBrokering.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborBrokering.setAae011(fpUser.getId());
                    laborBrokering.setCreateby(fpUser.getRealname());
                    if(!"".equals(laborBrokering.getAab301()) && laborBrokering.getAab301() != null){
                    }else{
                        laborBrokering.setAab301(fpUser.getAab301());
                    }
                }
                laborBrokeringService.insertSelective(laborBrokering);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改劳务经济人信息
     * @return
     */
    @RequestMapping("updateLaowuPerson")
    @ResponseBody
    public LaborBrokering updateLaowuPerson(HttpServletRequest request, String lbk001,ModelMap modelMap){
        LaborBrokering laborBrokering = null;
        if(!"".equals(lbk001) && lbk001 != null){
            laborBrokering = laborBrokeringService.selectByPrimaryKey(lbk001);
        }
        return laborBrokering;
    }


    /**
     * 删除劳务经济人信息
     * @return
     */
    @RequestMapping("delLaowuPerson")
    @ResponseBody
    public String delLaowuPerson(HttpServletRequest request, String lbk001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(lbk001) && lbk001 != null){
            LaborBrokering laborBrokering = new LaborBrokering();
            laborBrokering = laborBrokeringService.selectByPrimaryKey(lbk001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            laborBrokering.setDeleteby(fpUser.getRealname());
            laborBrokering.setDeletedate(new Date());
            laborBrokering.setAae100("0");
            s = laborBrokeringService.updateByPrimaryKeySelective(laborBrokering);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 劳务经济人台账
     * @return
     */
    @RequestMapping("laowuPersonList")
    public String laowuPersonList(){
        return "accountInformation/labor_brokering_account_list";
    }

    /**
     * 根据aab301  查劳务经济人信息信息列表
     */
    @RequestMapping("querLaowuPersonByAab301")
    @ResponseBody
    public String querLaowuPersonByAab301(HttpServletRequest request,LaborBrokering laborBrokering){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(laborBrokering.getAab301()) && laborBrokering.getAab301() != null){
                aab301 =  aab301Substr(laborBrokering.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            laborBrokering.setAab301(aab301);

            if(!"".equals(laborBrokering.getLbk002()) && laborBrokering.getLbk002() != null){
                laborBrokering.setLbk002(laborBrokering.getLbk002()+"%");
            }
            if(!"".equals(laborBrokering.getLbk008()) && laborBrokering.getLbk008() != null){
                laborBrokering.setLbk008(laborBrokering.getLbk008()+"%");
            }

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<LaborBrokering> list= laborBrokeringService.querLaowuPersonByAab301(laborBrokering,startRecord+"",pageSize+"");
            String countlist = laborBrokeringService.queryAllByAab301(laborBrokering);
            DataTableResultVO<LaborBrokering> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //***************************创业师资力量*****************************************************************************

    /**
     * 创业师资力量 台账页面
     * @return
     */
    @RequestMapping("teacherList")
    public String teacherList(){
        return "accountInformation/entrepreneurial_teachers_account_list";
    }


    /**
     * 保存创业师资力量信息
     * @param request
     * @param modelMap
     * @param entrepreneurialTeachers
     * @return
     */
    @RequestMapping("saveChuangyeTeacher")
    @ResponseBody
    public String saveChuangyeTeacher(HttpServletRequest request, ModelMap modelMap, EntrepreneurialTeachers entrepreneurialTeachers){
        String flag = "";
        if(entrepreneurialTeachers != null){
            if(!"".equals(entrepreneurialTeachers.getEts001()) && entrepreneurialTeachers.getEts001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    entrepreneurialTeachers.setAae012(fpUser.getId());
                    entrepreneurialTeachers.setUpdateby(fpUser.getRealname());
                    entrepreneurialTeachers.setUpdatedate(new Date());
                }
                entrepreneurialTeachersService.updateByPrimaryKeySelective(entrepreneurialTeachers);
                flag = "update";
            }else{
                entrepreneurialTeachers.setEts001(UUIDGenerator.generate().toString());
                entrepreneurialTeachers.setAae036(new Date());
                entrepreneurialTeachers.setDatasource("1");//1代表录入，2代表导入
                entrepreneurialTeachers.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    entrepreneurialTeachers.setAae011(fpUser.getId());
                    entrepreneurialTeachers.setCreateby(fpUser.getRealname());
                    if(!"".equals(entrepreneurialTeachers.getAab301()) && entrepreneurialTeachers.getAab301() != null){
                    }else{
                        entrepreneurialTeachers.setAab301(fpUser.getAab301());
                    }
                }
                entrepreneurialTeachersService.insertSelective(entrepreneurialTeachers);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改创业师资力量信息
     * @return
     */
    @RequestMapping("updateChuangyeTeacher")
    @ResponseBody
    public EntrepreneurialTeachers updateChuangyeTeacher(HttpServletRequest request, String ets001,ModelMap modelMap){
        EntrepreneurialTeachers entrepreneurialTeachers = null;
        if(!"".equals(ets001) && ets001 != null){
            entrepreneurialTeachers = entrepreneurialTeachersService.selectByPrimaryKey(ets001);
        }
        return entrepreneurialTeachers;
    }


    /**
     * 删除创业师资力量信息
     * @return
     */
    @RequestMapping("delChuangyeTeacher")
    @ResponseBody
    public String delChuangyeTeacher(HttpServletRequest request, String ets001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(ets001) && ets001 != null){
            EntrepreneurialTeachers entrepreneurialTeachers = new EntrepreneurialTeachers();
            entrepreneurialTeachers = entrepreneurialTeachersService.selectByPrimaryKey(ets001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            entrepreneurialTeachers.setDeleteby(fpUser.getRealname());
            entrepreneurialTeachers.setDeletedate(new Date());
            entrepreneurialTeachers.setAae100("0");
            s = entrepreneurialTeachersService.updateByPrimaryKeySelective(entrepreneurialTeachers);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据aab301  创业师资力量信息列表
     */
    @RequestMapping("querChuangyeTeacherByAab301")
    @ResponseBody
    public String querChuangyeTeacherByAab301(HttpServletRequest request,EntrepreneurialTeachers entrepreneurialTeachers){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 = "";
            if(!"".equals(entrepreneurialTeachers.getAab301()) && entrepreneurialTeachers.getAab301() != null){
                aab301 =  aab301Substr(entrepreneurialTeachers.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            entrepreneurialTeachers.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            if(!"".equals(entrepreneurialTeachers.getEts007()) && entrepreneurialTeachers.getEts007() != null){
                entrepreneurialTeachers.setEts007(entrepreneurialTeachers.getEts007()+"%");
            }
            List<EntrepreneurialTeachers> list= entrepreneurialTeachersService.querChuangyeTeacherByAab301(entrepreneurialTeachers,startRecord+"",pageSize+"");
            String countlist = entrepreneurialTeachersService.queryAllByAab301(entrepreneurialTeachers);
            DataTableResultVO<EntrepreneurialTeachers> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//***************************公益性劳务工作站*****************************************************************************


    /**
     * 保存公益性劳务工作站信息
     * @param request
     * @param modelMap
     * @param publicServiceWorkstation
     * @return
     */
    @RequestMapping("savePublicWork")
    @ResponseBody
    public String savePublicWork(HttpServletRequest request, ModelMap modelMap, PublicServiceWorkstation publicServiceWorkstation){
        String flag = "";
        if(publicServiceWorkstation != null){
            if(!"".equals(publicServiceWorkstation.getPsw001()) && publicServiceWorkstation.getPsw001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    publicServiceWorkstation.setAae012(fpUser.getId());
                    publicServiceWorkstation.setUpdateby(fpUser.getRealname());
                    publicServiceWorkstation.setUpdatedate(new Date());
                }
                publicServiceWorkstationService.updateByPrimaryKeySelective(publicServiceWorkstation);
                flag = "update";
            }else{
                publicServiceWorkstation.setPsw001(UUIDGenerator.generate().toString());
                publicServiceWorkstation.setAae036(new Date());
                publicServiceWorkstation.setDatasource("1");//1代表录入，2代表导入
                publicServiceWorkstation.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    publicServiceWorkstation.setAae011(fpUser.getId());
                    publicServiceWorkstation.setCreateby(fpUser.getRealname());
                    if(!"".equals(publicServiceWorkstation.getAab301()) && publicServiceWorkstation.getAab301() != null){
                    }else{
                        publicServiceWorkstation.setAab301(fpUser.getAab301());
                    }
                }
                publicServiceWorkstationService.insertSelective(publicServiceWorkstation);
                flag = "insert";
            }
        }
        return flag;
    }

    /**
     * 修改公益性劳务工作站信息
     * @return
     */
    @RequestMapping("updatePublicWork")
    @ResponseBody
    public PublicServiceWorkstation updatePublicWork(HttpServletRequest request, String psw001,ModelMap modelMap){
        PublicServiceWorkstation publicServiceWorkstation = null;
        if(!"".equals(psw001) && psw001 != null){
            publicServiceWorkstation = publicServiceWorkstationService.selectByPrimaryKey(psw001);
        }
        return publicServiceWorkstation;
    }


    /**
     * 删除公益性劳务工作站信息
     * @return
     */
    @RequestMapping("delPublicWork")
    @ResponseBody
    public String delPublicWork(HttpServletRequest request, String psw001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(psw001) && psw001 != null){
            PublicServiceWorkstation publicServiceWorkstation = new PublicServiceWorkstation();
            publicServiceWorkstation = publicServiceWorkstationService.selectByPrimaryKey(psw001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            publicServiceWorkstation.setDeleteby(fpUser.getRealname());
            publicServiceWorkstation.setDeletedate(new Date());
            publicServiceWorkstation.setAae100("0");
            s = publicServiceWorkstationService.updateByPrimaryKeySelective(publicServiceWorkstation);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据aab301  公益性劳务工作站信息列表
     */
    @RequestMapping("queryPublicWorkByAab301")
    @ResponseBody
    public String queryPublicWorkByAab301(HttpServletRequest request,String aab301,String psw002_scan,String psw003_scan){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            if(!"".equals(aab301) && aab301 != null){
                aab301 =  aab301Substr(aab301)+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            if(!"".equals(psw002_scan) && psw002_scan != null){
                psw002_scan = "%"+psw002_scan+"%";
            }
            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<PublicServiceWorkstation> list= publicServiceWorkstationService.querPublicWorkByAab301(aab301,psw002_scan,psw003_scan,startRecord+"",pageSize+"");
            String countlist = publicServiceWorkstationService.queryAllByAab301(aab301);
            DataTableResultVO<PublicServiceWorkstation> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


//***************************劳务输出工作机构*****************************************************************************


    /**
     * 保存劳务输出工作机构信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("saveLaborExportAgency")
    @ResponseBody
    public String saveLaborExportAgency(HttpServletRequest request, ModelMap modelMap, LaborExportAgency laborExportAgency){
        String flag = "";
        if(laborExportAgency != null){
            if(!"".equals(laborExportAgency.getLea001()) && laborExportAgency.getLea001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborExportAgency.setAae012(fpUser.getId());
                    laborExportAgency.setUpdateby(fpUser.getRealname());
                    laborExportAgency.setUpdatedate(new Date());
                }
                laborExportAgencyService.updateByPrimaryKeySelective(laborExportAgency);
                flag = "update";
            }else{
                laborExportAgency.setLea001(UUIDGenerator.generate().toString());
                laborExportAgency.setAae036(new Date());
                laborExportAgency.setDatasource("1");//1代表录入，2代表导入
                laborExportAgency.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborExportAgency.setAae011(fpUser.getId());
                    laborExportAgency.setCreateby(fpUser.getRealname());
                    if(!"".equals(laborExportAgency.getAab301()) && laborExportAgency.getAab301() != null){
                    }else{
                        laborExportAgency.setAab301(fpUser.getAab301());
                    }
                }
                laborExportAgencyService.insertSelective(laborExportAgency);
                flag =laborExportAgency.getLea001();
            }
        }
        return flag;
    }

    /**
     * 修改劳务输出工作机构信息
     * @return
     */
    @RequestMapping("updateLaborExportAgency")
    @ResponseBody
    public LaborExportAgency updateLaborExportAgency(HttpServletRequest request, String lea001,ModelMap modelMap){
        LaborExportAgency laborExportAgency = null;
        if(!"".equals(lea001) && lea001 != null){
            laborExportAgency = laborExportAgencyService.selectByPrimaryKey(lea001);
        }
        return laborExportAgency;
    }


    /**
     * 删除劳务输出工作机构信息
     * @return
     */
    @RequestMapping("delLaborExportAgency")
    @ResponseBody
    public String delLaborExportAgency(HttpServletRequest request, String lea001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(lea001) && lea001 != null){
            LaborExportAgency laborExportAgency = new LaborExportAgency();
            laborExportAgency = laborExportAgencyService.selectByPrimaryKey(lea001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            laborExportAgency.setDeleteby(fpUser.getRealname());
            laborExportAgency.setDeletedate(new Date());
            laborExportAgency.setAae100("0");
            s = laborExportAgencyService.updateByPrimaryKeySelective(laborExportAgency);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }

    /**
     * 根据aab301  劳务输出工作机构信息列表
     */
    @RequestMapping("queryLaborExportAgencyByAab301")
    @ResponseBody
    public String queryLaborExportAgencyByAab301(HttpServletRequest request,LaborExportAgency laborExportAgency){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 ="";
            if(!"".equals(laborExportAgency.getAab301()) && laborExportAgency.getAab301() != null){
                aab301 =  aab301Substr(laborExportAgency.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            laborExportAgency.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<LaborExportAgency> list= laborExportAgencyService.queryLaborExportAgencyByAab301(laborExportAgency,startRecord+"",pageSize+"");
            String countlist = laborExportAgencyService.queryAllByAab301(laborExportAgency);
            DataTableResultVO<LaborExportAgency> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //***************************劳务扶贫公司*****************************************************************************


    /**
     * 保存劳务扶贫公司信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("saveLaborAlleviationCompany")
    @ResponseBody
    public String saveLaborExportAgency(HttpServletRequest request, ModelMap modelMap, LaborAlleviationCompany laborAlleviationCompany){
        String flag = "";
        if(laborAlleviationCompany != null){
            if(!"".equals(laborAlleviationCompany.getLac001()) && laborAlleviationCompany.getLac001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborAlleviationCompany.setAae012(fpUser.getId());
                    laborAlleviationCompany.setUpdateby(fpUser.getRealname());
                    laborAlleviationCompany.setUpdatedate(new Date());
                }
                laborAlleviationCompanyService.updateByPrimaryKeySelective(laborAlleviationCompany);
                flag = "update";
            }else{
                laborAlleviationCompany.setLac001(UUIDGenerator.generate().toString());
                laborAlleviationCompany.setAae036(new Date());
                laborAlleviationCompany.setDatasource("1");//1代表录入，2代表导入
                laborAlleviationCompany.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    laborAlleviationCompany.setAae011(fpUser.getId());
                    laborAlleviationCompany.setCreateby(fpUser.getRealname());
                    if(!"".equals(laborAlleviationCompany.getAab301()) && laborAlleviationCompany.getAab301() != null){
                    }else{
                        laborAlleviationCompany.setAab301(fpUser.getAab301());
                    }
                }
                laborAlleviationCompanyService.insertSelective(laborAlleviationCompany);
                flag = laborAlleviationCompany.getLac001();
            }
        }
        return flag;
    }

    /**
     * 修改劳务扶贫公司信息
     * @return
     */
    @RequestMapping("updateLaborAlleviationCompany")
    @ResponseBody
    public LaborAlleviationCompany updateLaborAlleviationCompany(HttpServletRequest request, String lac001,ModelMap modelMap){
        LaborAlleviationCompany laborAlleviationCompany = null;
        if(!"".equals(lac001) && lac001 != null){
            laborAlleviationCompany = laborAlleviationCompanyService.selectByPrimaryKey(lac001);
        }
        return laborAlleviationCompany;
    }


    /**
     * 删除劳务扶贫公司信息
     * @return
     */
    @RequestMapping("delLaborAlleviationCompany")
    @ResponseBody
    public String delLaborAlleviationCompany(HttpServletRequest request, String lac001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(lac001) && lac001 != null){
            LaborAlleviationCompany laborAlleviationCompany = new LaborAlleviationCompany();
            laborAlleviationCompany = laborAlleviationCompanyService.selectByPrimaryKey(lac001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            laborAlleviationCompany.setDeleteby(fpUser.getRealname());
            laborAlleviationCompany.setDeletedate(new Date());
            laborAlleviationCompany.setAae100("0");
            s = laborAlleviationCompanyService.updateByPrimaryKeySelective(laborAlleviationCompany);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 根据aab301
     * 劳务服务机构台账
     */
    @RequestMapping("laborAlleviationCompanyList")
    public String laborAlleviationCompanyList(){
        return "accountInformation/labor_alleviation_company_account_list";
    }


    /**
     * 根据aab301  劳务扶贫公司列表
     * 劳务服务机构
     */
    @RequestMapping("queryLaborAlleviationCompanyByAab301")
    @ResponseBody
    public String queryLaborAlleviationCompanyByAab301(HttpServletRequest request,LaborAlleviationCompany laborAlleviationCompany){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 ="";
            if(!"".equals(laborAlleviationCompany.getAab301()) && laborAlleviationCompany.getAab301() != null){
                aab301 =  aab301Substr(laborAlleviationCompany.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            laborAlleviationCompany.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            if(!"".equals(laborAlleviationCompany.getLac010()) && laborAlleviationCompany.getLac010() != null){
                laborAlleviationCompany.setLac010(laborAlleviationCompany.getLac010()+"%");
            }

            List<LaborAlleviationCompany> list= laborAlleviationCompanyService.queryLaborAlleviationCompanyByAab301(laborAlleviationCompany,startRecord+"",pageSize+"");
            String countlist = laborAlleviationCompanyService.queryAllByAab301(laborAlleviationCompany);
            DataTableResultVO<LaborAlleviationCompany> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //***************************创业服务站公司*****************************************************************************


    /**
     * 保存创业服务站信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("saveExSituPoverty")
    @ResponseBody
    public String saveExSituPoverty(HttpServletRequest request, ModelMap modelMap, ExSituPoverty exSituPoverty){
        String flag = "";
        if(exSituPoverty != null){
            if(!"".equals(exSituPoverty.getEsp001()) && exSituPoverty.getEsp001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    exSituPoverty.setAae012(fpUser.getId());
                    exSituPoverty.setUpdateby(fpUser.getRealname());
                    exSituPoverty.setUpdatedate(new Date());
                }
                exSituPovertyService.updateByPrimaryKeySelective(exSituPoverty);
                flag = "update";
            }else{
                exSituPoverty.setEsp001(UUIDGenerator.generate().toString());
                exSituPoverty.setAae036(new Date());
                exSituPoverty.setDatasource("1");//1代表录入，2代表导入
                exSituPoverty.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    exSituPoverty.setAae011(fpUser.getId());
                    exSituPoverty.setCreateby(fpUser.getRealname());
                    if(!"".equals(exSituPoverty.getAab301()) && exSituPoverty.getAab301() != null){
                    }else{
                        exSituPoverty.setAab301(fpUser.getAab301());
                    }
                }
                exSituPovertyService.insertSelective(exSituPoverty);
                flag = exSituPoverty.getEsp001();
            }
        }
        return flag;
    }

    /**
     * 修改创业服务站信息
     * @return
     */
    @RequestMapping("updateExSituPoverty")
    @ResponseBody
    public ExSituPoverty updateExSituPoverty(HttpServletRequest request, String esp001,ModelMap modelMap){
        ExSituPoverty exSituPoverty = null;
        if(!"".equals(esp001) && esp001 != null){
            exSituPoverty = exSituPovertyService.selectByPrimaryKey(esp001);
        }
        return exSituPoverty;
    }


    /**
     * 删除创业服务站信息
     * @return
     */
    @RequestMapping("delExSituPoverty")
    @ResponseBody
    public String delExSituPoverty(HttpServletRequest request, String esp001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(esp001) && esp001 != null){
            ExSituPoverty exSituPoverty = new ExSituPoverty();
            exSituPoverty = exSituPovertyService.selectByPrimaryKey(esp001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            exSituPoverty.setDeleteby(fpUser.getRealname());
            exSituPoverty.setDeletedate(new Date());
            exSituPoverty.setAae100("0");
            s = exSituPovertyService.updateByPrimaryKeySelective(exSituPoverty);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 易地扶贫就业服务站台账
     * @return
     */
    @RequestMapping("exSituPovertyList")
    public String exSituPovertyList(){
        return "accountInformation/ex_situ_poverty_account_list";
    }


    /**
     * 根据aab301  创业服务站列表
     */
    @RequestMapping("queryExSituPovertyByAab301")
    @ResponseBody
    public String queryExSituPovertyByAab301(HttpServletRequest request,ExSituPoverty exSituPoverty){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 ="";
            if(!"".equals(exSituPoverty.getAab301()) && exSituPoverty.getAab301() != null){
                aab301 =  aab301Substr(exSituPoverty.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            exSituPoverty.setAab301(aab301);

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            if(!"".equals(exSituPoverty.getEsp010()) && exSituPoverty.getEsp010() != null){
                exSituPoverty.setEsp010(exSituPoverty.getEsp010()+"%");
            }

            List<ExSituPoverty> list= exSituPovertyService.queryExSituPovertyByAab301(exSituPoverty,startRecord+"",pageSize+"");
            String countlist = exSituPovertyService.queryAllByAab301(exSituPoverty);
            DataTableResultVO<ExSituPoverty> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(Integer.parseInt(countlist));//数据的条数
            result.setRecordsFiltered(Integer.parseInt(countlist));//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //***************************岗位信息库*****************************************************************************


    /**
     * 保存岗位信息库信息
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("saveJobInformationBase")
    @ResponseBody
    public String saveJobInformationBase(HttpServletRequest request, ModelMap modelMap, JobInformationBase jobInformationBase){
        String flag = "";
        if(jobInformationBase != null){
            if(!"".equals(jobInformationBase.getJib001()) && jobInformationBase.getJib001() != null){
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    jobInformationBase.setAae012(fpUser.getId());
                    jobInformationBase.setUpdateby(fpUser.getRealname());
                    jobInformationBase.setUpdatedate(new Date());
                }
                jobInformationBaseService.updateByPrimaryKeySelective(jobInformationBase);
                flag = "update";
            }else{
                jobInformationBase.setJib001(UUIDGenerator.generate().toString());
                jobInformationBase.setAae036(new Date());
                jobInformationBase.setDatasource("1");//1代表录入，2代表导入
                jobInformationBase.setAae100("1");
                FpUser fpUser = null;
                if(request.getSession().getAttribute("fpUser") != null){
                    fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                    jobInformationBase.setAae011(fpUser.getId());
                    jobInformationBase.setCreateby(fpUser.getRealname());
                    if(!"".equals(jobInformationBase.getAab301()) && jobInformationBase.getAab301() != null){
                    }else{
                        jobInformationBase.setAab301(fpUser.getAab301());
                    }
                }
                jobInformationBaseService.insertSelective(jobInformationBase);
                flag = jobInformationBase.getJib001();
            }
        }
        return flag;
    }

    /**
     * 修改岗位信息库信息
     * @return
     */
    @RequestMapping("updateJobInformationBase")
    @ResponseBody
    public JobInformationBase updateJobInformationBase(HttpServletRequest request, String jib001,ModelMap modelMap){
        JobInformationBase jobInformationBase = null;
        if(!"".equals(jib001) && jib001 != null){
            jobInformationBase = jobInformationBaseService.selectByPrimaryKey(jib001);
        }
        return jobInformationBase;
    }


    /**
     * 删除岗位信息库信息
     * @return
     */
    @RequestMapping("delJobInformationBase")
    @ResponseBody
    public String delJobInformationBase(HttpServletRequest request, String jib001,ModelMap modelMap){
        int s = 0;
        if(!"".equals(jib001) && jib001 != null){
            JobInformationBase jobInformationBase  = new JobInformationBase();
            jobInformationBase = jobInformationBaseService.selectByPrimaryKey(jib001);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            jobInformationBase.setDeleteby(fpUser.getRealname());
            jobInformationBase.setDeletedate(new Date());
            jobInformationBase.setAae100("0");
            s = jobInformationBaseService.updateByPrimaryKeySelective(jobInformationBase);
        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }


    /**
     * 岗位信息库台账
     * @return
     */
    @RequestMapping("jobInformationBaseList")
    public String jobInformationBaseList(){
        return "accountInformation/job_information_base_account_list";
    }


    /**
     * 根据aab301  岗位信息库列表
     */
    @RequestMapping("queryJobInformationBaseByAab301")
    @ResponseBody
    public String queryJobInformationBaseByAab301(HttpServletRequest request,JobInformationBase jobInformationBase){
        try {
            int pageSize = 50;
            int startRecord = 0;
            String pageIndex = request.getParameter("pageIndex");

            String aab301 ="";
            if(!"".equals(jobInformationBase.getAab301()) && jobInformationBase.getAab301() != null){
                aab301 =  aab301Substr(jobInformationBase.getAab301())+ "%";
            }else{
                //从session中获取当前登录人的aab301 行政区划
                FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                aab301 = fpUser.getAab301();
                aab301 =  aab301Substr(aab301)+ "%";
            }
            jobInformationBase.setAab301(aab301);
            if(!"".equals(jobInformationBase.getJib009()) && jobInformationBase.getJib009() != null){
                jobInformationBase.setJib009(jobInformationBase.getJib009()+"%");
            }

            //开始的数据行数   //分页的当前位置
            String start = request.getParameter("start");
            if (!"".equals(start) && start != null) {
                startRecord = Integer.parseInt(start);
            }
            //每页的数据数   分页的数据条数
            String size = request.getParameter("length");
            if (!"".equals(size) && size != null) {
                pageSize = (Integer.parseInt(pageIndex)+1)*pageSize;
            }

            List<JobInformationBase> list= jobInformationBaseService.queryJobInformationBaseByAab301(jobInformationBase,startRecord+"",pageSize+"");
            String countlist = jobInformationBaseService.queryAllByAab301(jobInformationBase);
            DataTableResultVO<JobInformationBase> result=new DataTableResultVO<>();
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
     * 根据编码查询详细名称
     * @param request
     * @param aab301
     * @return
     */
    @RequestMapping("queryAab301Info")
    @ResponseBody
    public PoorXzqh queryAab301Info(HttpServletRequest request,String aab301){
        PoorXzqh poorXzqh = null;
        poorXzqh = ztreeService.queryAab301Info(aab301);
        return poorXzqh;
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
