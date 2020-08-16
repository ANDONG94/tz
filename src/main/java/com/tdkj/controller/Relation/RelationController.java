package com.tdkj.controller.Relation;

import com.tdkj.model.*;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import com.tdkj.service.Relation.RelationService;
import com.tdkj.util.UUIDGenerator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Relation")
public class RelationController {
    @Autowired
    private RelationService relationService;
    @Autowired
    private PoorLaborForceService poorLaborForceService;
    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    CommunityFactoryService communityFactoryService;
    @Autowired
    PovertyAlleviationBaseService povertyAlleviationBaseService;


    /**
     * 查询单位的员工
     * @return
     *   2019-11-20日暂时不用  因为要加上岗时间判断，所以使用下面的方法
     */
    @RequestMapping(value = "/Register",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public String getPeopleRegister(HttpServletRequest request, String aac002){
        String aac003 ="no";
        PoorLaborForce poorLaborForce=poorLaborForceService.queryName(aac002);
        if (poorLaborForce!=null && !"".equals(poorLaborForce)){
            aac003= poorLaborForce.getPlf004();
            /*String count=relationService.queryAllByPlf001(poorLaborForce.getPlf001());
            if(!"0".equals(count)){
                aac003="have";
            }*/
        }
        request.getSession().setAttribute("PoorLaborForce",poorLaborForce);
        return aac003;
    }



    /**
     * 查询单位的员工
     * @return
     * rtn003   上岗年月   2019-11-20日新增
     */
    @RequestMapping(value = "/queryWorkerByIdCard",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public String queryWorkerByIdCard(HttpServletRequest request, String aac002,String rtn003){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String aac003 ="no";
        PoorLaborForce poorLaborForce=poorLaborForceService.queryName(aac002);
        if (poorLaborForce!=null && !"".equals(poorLaborForce)){
            aac003= poorLaborForce.getPlf004();
            List<Relation> list = relationService.queryRelationPlf001(poorLaborForce.getPlf001());
            if(list.size()>0){
                for (int i=0;i<list.size();i++){
                    String rtn005 = list.get(i).getRtn005();//离职年月
                    if(!"".equals(rtn005) && rtn005 != null){
                        try {
                            if(sdf.parse(rtn005).getTime()>sdf.parse(aac003).getTime()){//转成long类型比较
                                    System.out.println("离职年月大于当前的上岗年月,则不能新增");
                                    aac003="have";
                                    request.getSession().setAttribute("PoorLaborForce",poorLaborForce);
                                    return aac003;
                                }else if(sdf.parse(rtn005).getTime()<=sdf.parse(aac003).getTime()){
                                     System.out.println("离职年月小于当前的上岗年月,则可以新增");
                                        request.getSession().setAttribute("PoorLaborForce",poorLaborForce);
                                        return aac003;
                                 }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }else{  //说明之前录入的没有离职，则不能进行录入新的
                        aac003="have";
                        request.getSession().setAttribute("PoorLaborForce",poorLaborForce);
                        return aac003;
                    }
                }
            }

        }

        request.getSession().setAttribute("PoorLaborForce",poorLaborForce);
        return aac003;
    }

    /**
     * 查询单位的员工
     * @return
     */
    @RequestMapping(value = "/people",produces = "application/json;charset=utf-8",method = RequestMethod.GET)
    public String getPeople(String ctf001,HttpServletRequest request){
        int pageSize = 50;
        int startRecord = 0;
        String pageIndex = request.getParameter("pageIndex");
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

        List<Relation> list= relationService.selectByPrimaryKey(ctf001,startRecord+"",pageSize+"");
        String countlist = relationService.queryAllByCompanyid(ctf001);
        DataTableResultVO<Relation> result=new DataTableResultVO<>();
        result.setData(list);//data参数。
        result.setRecordsTotal(Integer.parseInt(countlist+""));//数据的条数
        result.setRecordsFiltered(Integer.parseInt(countlist+""));//过滤后数据的条数
        return JSONObject.fromObject(result).toString();
    }

    /**
     * 增加单位下的员工
     * @return
     */
    @RequestMapping(value = "/people",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String postPeople(HttpServletRequest request, Relation relation){
        relation.setAab301(relation.getAab301_a());
        String flag=null;

        if(relation.getType_a()!=null &&!"".equals(relation.getType_a())){
            relation.setType(relation.getType_a());
        }

        PoorLaborForce poorLaborForce= (PoorLaborForce)request.getSession().getAttribute("PoorLaborForce");
        if(!"".equals(relation.getRtn001()) && relation.getRtn001() != null){
            FpUser fpUser = null;
            if(request.getSession().getAttribute("fpUser") != null){
                fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                relation.setAae012(fpUser.getId());
                relation.setUpdateby(fpUser.getRealname());
                relation.setUpdatedate(new Date());
            }
            if (relation.getType().equals("1")){
                String companyid = relation.getCompanyid();
                String count = relationService.queryAllByCompanyid(companyid);
                //根据companyid 查询社区工厂
                CommunityFactory communityFactory = communityFactoryService.selectByPrimaryKey(companyid);
                communityFactory.setCtf008(Integer.parseInt(count));
                communityFactoryService.updateByPrimaryKeySelective(communityFactory);
            }else if(relation.getType().equals("2")){
                String companyid = relation.getCompanyid();
                String count = relationService.queryAllByCompanyid(companyid);
                //根据companyid 查询扶贫基地

                PovertyAlleviationBase povertyAlleviationBase  = povertyAlleviationBaseService.selectByPrimaryKey(companyid);
                povertyAlleviationBase.setPab008(Integer.parseInt(count));
                povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
            }


            relationService.updateByPrimaryKeySelective(relation);
            flag = "update";
        }else{
            relation.setRtn001(UUIDGenerator.generate().toString());
            relation.setPeopleid(poorLaborForce.getPlf001());
            relation.setAae036(new Date());
            relation.setDatasource("1");//1代表录入，2代表导入
            relation.setAae100("1");


            FpUser fpUser = null;
            if(request.getSession().getAttribute("fpUser") != null){
                fpUser = (FpUser)request.getSession().getAttribute("fpUser");
                relation.setAae011(fpUser.getId());
                relation.setCreateby(fpUser.getRealname());
                if(!"".equals(relation.getAab301()) && relation.getAab301() != null){
                }else{
                    relation.setAab301(fpUser.getAab301());
                }
            }
            relationService.insertSelective(relation);
            if (relation.getType().equals("1")){
                String companyid = relation.getCompanyid();
                String count = relationService.queryAllByCompanyid(companyid);
                //根据companyid 查询社区工厂
                CommunityFactory communityFactory = communityFactoryService.selectByPrimaryKey(companyid);
                communityFactory.setCtf008(Integer.parseInt(count));
                communityFactoryService.updateByPrimaryKeySelective(communityFactory);
            }else if(relation.getType().equals("2")){
                String companyid = relation.getCompanyid();
                String count = relationService.queryAllByCompanyid(companyid);
                //根据companyid 查询扶贫基地
                PovertyAlleviationBase povertyAlleviationBase  = povertyAlleviationBaseService.selectByPrimaryKey(companyid);
                povertyAlleviationBase.setPab008(Integer.parseInt(count));
                povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
            }

            flag = "insert";
        }

        return flag;
    }


    /**
     * 修改单位下的员工
     * @return
     */
    @RequestMapping(value = "/people",produces = "application/json;charset=utf-8",method = RequestMethod.PUT)
    public Relation putPeople(String rtn001){
        Relation relaTio=null;
        if (rtn001!=null && !"".equals(rtn001))
         relaTio =relationService.selectByPrimaryKeyRtn(rtn001);
        return relaTio;
    }

    /**
     * 删除单位下的员工
     * @return
     */
    @RequestMapping(value = "/people",produces = "application/json;charset=utf-8",method = RequestMethod.DELETE)
    public String delPeople(HttpServletRequest request,String rtn001){
        int s = 0;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        if(!"".equals(rtn001) && rtn001 != null){
            Relation relation = relationService.selectByPrimaryKeyRtn(rtn001);
            String companyid = relation.getCompanyid();
            String count = relationService.queryAllByCompanyid(companyid);
            //判断是社区工厂还是扶贫基地
            if(!"".equals(relation.getType()) && relation.getType() != null && relation.getType().equals("1")){
                //说明是社区工厂
                //根据companyid 查询社区工厂
                CommunityFactory communityFactory = communityFactoryService.selectByPrimaryKey(companyid);
                communityFactory.setCtf008(Integer.parseInt(count)-1);
                communityFactory.setUpdateby(fpUser.getRealname());
                communityFactory.setDeletedate(new Date());
                communityFactoryService.updateByPrimaryKeySelective(communityFactory);
            }else if(!"".equals(relation.getType()) && relation.getType() != null && relation.getType().equals("2")){
                //说明是扶贫基地
                PovertyAlleviationBase povertyAlleviationBase = povertyAlleviationBaseService.selectByPrimaryKey(companyid);
                povertyAlleviationBase.setPab008(Integer.parseInt(count)-1);
                povertyAlleviationBase.setUpdateby(fpUser.getRealname());
                povertyAlleviationBase.setDeletedate(new Date());
                povertyAlleviationBaseService.updateByPrimaryKeySelective(povertyAlleviationBase);
            }

            relation.setDeleteby(fpUser.getRealname());
            relation.setDeletedate(new Date());
            relationService.updateByPrimaryKeySelective(relation);
            s = relationService.deleteByPrimaryKey(rtn001);

        }
        if(s > 0 ){
            return "yes";
        }else{
            return "no";
        }
    }
}
