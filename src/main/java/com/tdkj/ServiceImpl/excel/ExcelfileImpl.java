package com.tdkj.ServiceImpl.excel;

import com.tdkj.dao.InformationCollection.EmploymentSituationMapper;
import com.tdkj.dao.InformationCollection.PoorHouseholdsMapper;
import com.tdkj.dao.InformationCollection.PoorLaborForceMapper;
import com.tdkj.dao.importExcle.*;
import com.tdkj.model.*;
import com.tdkj.service.excel.Excelfile;
import com.tdkj.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelfileImpl implements Excelfile {

    @Autowired
    private PoorHouseholdsTmpMapper poorHouseholdsTmpMapper;
    @Autowired
    private PoorLaborForceTmpMapper poorLaborForceTmpMapper;
    @Autowired
    private EmploymentSituationTmpMapper employmentSituationTmpMapper;
    @Autowired
    private EntrepreneurshipTmpMapper entrepreneurshipTmpMapper;
    @Autowired
    private TrainingSituationTmpMapper trainingSituationTmpMapper;
    @Autowired
    private TechnicalSchoolsTmpMapper technicalSchoolsTmpMapper;
    @Autowired
    private PoorHouseholdsMapper poorHouseholdsMapper;
    @Autowired
    private PoorLaborForceMapper poorLaborForceMapper;
    @Autowired
    private EmploymentSituationMapper employmentSituationMapper;

    public static final String INSERT="1";
    public static final String UPDATE="9";


    /**
     * ******************************************************************贫困户************************************************************************
     */
    /**
     * 贫困户保存临时表
     * @param list
     */
    public void saveExcelList(List<PoorHouseholdsTmp> list) {
            //调用mapper的保存方法
                poorHouseholdsTmpMapper.addList(list);
    }

    /**
     * 贫困户正确信息
     * @param batch
     * @return
     */
    @Override
    public List<PoorHouseholdsTmp> getPoorHouseholdsTmpCorrect(String batch) {
        return poorHouseholdsTmpMapper.getPoorHouseholdsTmpCorrect(batch);
    }


    /**
     * 贫困户错误信息
     * @param batch
     * @return
     */
    @Override
    public List<PoorHouseholdsTmp> getPoorHouseholdsTmpError(String batch) {
        return poorHouseholdsTmpMapper.getPoorHouseholdsTmpError(batch);
    }

    /**
     * 将正确信息进行拷贝
     * @param batch
     * @return
     */
    @Override
    public int SavePoorHouseholds(String batch) throws Exception {
        /*return poorHouseholdsTmpMapper.SavePoorHouseholds(batch);*/
        //定义多个集合
        List<PoorHouseholdsTmp>  list =poorHouseholdsTmpMapper.CoppyselectByPrimaryKey(batch);
        List<String> selectList = new ArrayList();
        List<PoorHouseholdsTmp>  updateList =new ArrayList();
        List<PoorHouseholds>  addList =new ArrayList();
        //获取所有的身份证
        for (PoorHouseholdsTmp poorHouseholdsTmp:list) {
            selectList.add(poorHouseholdsTmp.getPhd003());
        }
        //根据是否查询是否已存在
        List<PoorHouseholds> por=poorHouseholdsMapper.selectByPrimaryidCardList(selectList);
        //遍历查询结果
        for ( PoorHouseholds phd :por) {
            String phd003= phd.getPhd003();
            String phd001= phd.getPhd001();
            for (PoorHouseholdsTmp phdtmp:list) {
                String phd003tmp= phdtmp.getPhd003();
                if(phd003.equals(phd003tmp)){
                    phdtmp.setPhd001(phd001);
                    //添加已存在需要修改的数据
                    phdtmp.setUpdateby(phdtmp.getCreateby());
                    phdtmp.setUpdatedate(phdtmp.getAae036());
                    updateList.add(phdtmp);
                    //除去需要修改的数据
                    selectList.remove(phd003tmp);
                }
            }

        }
        if(updateList.size()>0){
            //修改已存在的数据
            poorHouseholdsMapper.updateCardList(updateList);
        }
        String id ="";
        //获取需要新增的数据
        for (int i = 0; i < selectList.size(); i++) {
            String phd003=selectList.get(i);
            for (PoorHouseholdsTmp poorHouseholdsTmp:list) {
                if(phd003.equals(poorHouseholdsTmp.getPhd003())){
                    PoorHouseholds poorHouseholds =new PoorHouseholds();
                    BeanUtils.copyProperties(poorHouseholdsTmp,poorHouseholds);
                    //添加需要新增的数据

                    poorHouseholds.setPhd001(UUIDGenerator.generate().toString());
                    addList.add(poorHouseholds);
                    id+="'"+poorHouseholds.getPhd001()+"',";
                }
            }
        }
        if(addList.size()>0){
            //新增
            poorHouseholdsMapper.addList(addList);
        }
        return 1;
    }

/**
 * ******************************************************************劳动力************************************************************************
 */
    /**
     * 贫困劳动力保存临时表
     * @param list
     */
    @Override
    public void saveExcelListPLF(List<PoorLaborForceTmp> list) {
        //定义各种集合
        List<String> selectList =new ArrayList();
        List<String> existenceList =new ArrayList();
        List<PoorLaborForceTmp> list2 =new ArrayList<>();
        //遍历需要保存的数据
        for (PoorLaborForceTmp poorLaborForceTmp:list) {
            String aae100=poorLaborForceTmp.getIdentification();
            //拿到所有正确数据的身份证号码
            if("1".equals(aae100)){
                selectList.add(poorLaborForceTmp.getPlf002());
            }
        }
        if(selectList.size()>0){
            //查询拿到的身份证是否存在
            List<PoorHouseholds> por=poorHouseholdsMapper.selectByPrimaryidCardList(selectList);
            //遍历查出来的数据
            for (PoorHouseholds poorHouseholds:por) {
                String phd003= poorHouseholds.getPhd003();
                String phd001= poorHouseholds.getPhd001();
                existenceList.add(phd003);
                //遍历临时数据
                for (PoorLaborForceTmp poorLaborForceTmp:list) {
                    String PLF002=poorLaborForceTmp.getPlf002();
                    //把存在的数据主键记录进去
                    if(phd003.equals(PLF002)){
                        poorLaborForceTmp.setPlf002(phd001);
                    }

                }
            }
        }
        //通过差集处理得到不存在的数据
        selectList.removeAll(existenceList);
        //添加所有的临时数据
        if(list.size()>0){
            poorLaborForceTmpMapper.addList(list);
        }
        //循环错误数据集合
        for (int i = 0; i < selectList.size(); i++) {
            String plf002=selectList.get(i);
            //循环所有导入数据
            for (PoorLaborForceTmp poorLaborForceTmp:list) {
                //将错误信息写入
                if(plf002.equals(poorLaborForceTmp.getPlf002())){
                    poorLaborForceTmp.setMessage("贫困户（"+poorLaborForceTmp.getAac003()+"）不存在="+poorLaborForceTmp.getMessage());
                    poorLaborForceTmp.setIdentification("2");
                    list2.add(poorLaborForceTmp);
                }
            }
        }
        if(list2.size()>0){
            //修改错误数据
            poorLaborForceTmpMapper.updateCardList(list2);
        }
    }


    /**
     * 查询贫困户id
     * @param batch
     * @return
     */
    @Override
    public int PutPoorHouseholds(String batch) {


        List<PoorLaborForceTmp> list=poorLaborForceTmpMapper.PutPoorHouseholds(batch);
        List<PoorHouseholds> phd008List=new ArrayList<>();
        for (PoorLaborForceTmp poorLaborForceTmp:list) {
            PoorHouseholds poorHouseholds = new PoorHouseholds();
            String plf002=poorLaborForceTmp.getPlf002();
            poorHouseholds.setPhd001(plf002);
            phd008List.add(poorHouseholds);

        }
        if(phd008List.size()>0){
            poorHouseholdsMapper.updatePhd008(phd008List);
        }

        return 0;
    }

    /**
     * 贫困劳动力正确信息
     * @param batch
     * @return
     */
    @Override
    public List<PoorLaborForceTmp> getPoorLaborForceTmpCorrect(String batch) {
        return poorLaborForceTmpMapper.getPoorLaborForceTmpCorrect(batch);
    }

    /**
     * 贫困劳动力错误信息
     * @param batch
     * @return
     */
    @Override
    public List<PoorLaborForceTmp> getPoorLaborForceTmpError(String batch) {
        return poorLaborForceTmpMapper.getPoorLaborForceTmpError(batch);
    }

    /**
     * 将正确信息进行拷贝
     * @param batch
     * @return
     */
    @Override
    public int SavePoorLaborForce(String batch) throws Exception {

        List<PoorLaborForceTmp> list =poorLaborForceTmpMapper.getPoorLaborForceTmpSave(batch);

            List<String> selectList = new ArrayList();
            List<PoorLaborForceTmp>  updateList =new ArrayList();
            List<PoorLaborForce>  addList =new ArrayList();
            for (PoorLaborForceTmp poorLaborForceTmp:list) {
                selectList.add(poorLaborForceTmp.getPlf005());
            }
            List<PoorLaborForce> listPlf=poorLaborForceMapper.selectByPrimaryidCardList(selectList);
            for ( PoorLaborForce poorLaborForce :listPlf) {
                String plf005= poorLaborForce.getPlf005();
                String plf001= poorLaborForce.getPlf001();
                for (PoorLaborForceTmp plftmp:list) {
                    String plf005tmp= plftmp.getPlf005();
                    if(plf005.equals(plf005tmp)){
                        plftmp.setPlf001(plf001);
                        plftmp.setPlf025("0");
                        if(plftmp.getPlf018().equals("01")||plftmp.getPlf018().equals("02")||plftmp.getPlf018().equals("05")){
                            plftmp.setIspoor("1");
                        }
                        plftmp.setUpdateby(plftmp.getCreateby());
                        plftmp.setUpdatedate(plftmp.getAae036());
                        updateList.add(plftmp);
                        selectList.remove(plf005tmp);
                    }
                }
            }
            if(updateList.size()>0){
                poorLaborForceMapper.updateCardList(updateList);
            }
        for (int j = 0; j < selectList.size(); j++) {
           String   plf005tmp=selectList.get(j);
            for (PoorLaborForceTmp poorLaborForceTmp:list) {
               String plf005= poorLaborForceTmp.getPlf005();
               if(plf005tmp.equals(plf005)){
                   PoorLaborForce poorLaborForce =new PoorLaborForce();
                   BeanUtils.copyProperties(poorLaborForceTmp,poorLaborForce);
                   poorLaborForce.setPlf025("0");

                   if(poorLaborForce.getPlf018()!=null && !"".equals(poorLaborForce.getPlf018())){
                       if(poorLaborForce.getPlf018().equals("01")||poorLaborForce.getPlf018().equals("02")||poorLaborForce.getPlf018().equals("05")){
                           poorLaborForce.setIspoor("1");
                       }

                   }

                   addList.add(poorLaborForce);
               }
            }
        }
        if (addList.size()>0){
            poorLaborForceMapper.addList(addList);
        }
        return 1;
    }

    /**
     * ******************************************************************就业信息************************************************************************
     */

    /**
     * 保存导入信息到临时表
     * @param list
     */
    @Override
    public void saveExcelListES(List<EmploymentSituationTmp> list) {

        //注解参考saveExcelListPLF()方法
        List<String> selectList =new ArrayList();
        List<String> existenceList =new ArrayList();
        List<EmploymentSituationTmp> list2 =new ArrayList<>();
        for (EmploymentSituationTmp employmentSituationTmp:list) {
            String aae100=employmentSituationTmp.getIdentification();
            if("1".equals(aae100)){
                selectList.add(employmentSituationTmp.getEys002());
            }
        }
        if(selectList.size()>0){
            List<PoorLaborForce> plf=poorLaborForceMapper.selectByPrimaryidCardList(selectList);
            for (PoorLaborForce poorLaborForce:plf) {
                String plf005= poorLaborForce.getPlf005();
                String plf001= poorLaborForce.getPlf001();
                existenceList.add(plf005);
                for (EmploymentSituationTmp employmentSituationTmp:list) {
                    String eys002=employmentSituationTmp.getEys002();
                    if(plf005.equals(eys002)){
                        employmentSituationTmp.setEys002(plf001);
                        employmentSituationTmp.setAab301(poorLaborForce.getAab301());
                    }
                }
            }
        }



        //通过差集处理得到不存在的数据
        selectList.removeAll(existenceList);
        if(list.size()>0){
            employmentSituationTmpMapper.addList(list);
        }

        for (int i = 0; i < selectList.size(); i++) {
            String plf005=selectList.get(i);
            for (EmploymentSituationTmp employmentSituationTmp:list) {
                String eys002=employmentSituationTmp.getEys002();
                if(plf005.equals(eys002)){
                    employmentSituationTmp.setMessage("贫困劳动力（"+employmentSituationTmp.getAac003()+"）不存在="+employmentSituationTmp.getMessage());
                    employmentSituationTmp.setIdentification("2");
                    list2.add(employmentSituationTmp);
                }
            }
        }
        if(list2.size()>0){
            employmentSituationTmpMapper.updateCardList(list2);
        }
        employmentSituationTmpMapper.stored_procedure(list.get(0).getBatch(),"1");
    }

    /**
     * 查询正确信息
     * @param batch
     * @return
     */
    @Override
    public List<EmploymentSituationTmp> getEmploymentSintuationCorrect(String batch) {
        return employmentSituationTmpMapper.getEmploymentSintuationCorrect(batch);
    }

    /**
     * 查询错误信息
     * @param batch
     * @return
     */
    @Override
    public List<EmploymentSituationTmp> getEmploymentSintuationError(String batch) {
        return employmentSituationTmpMapper.getEmploymentSintuationError(batch);
    }

    /**
     * 保存正确信息到正式库
     * @param batch
     * @return
     */
    @Override
    public int SaveEmploymentSintuation(String batch) {

        List<EmploymentSituation> list=employmentSituationTmpMapper.getEmploymentSintuation(batch);
        list.forEach(eys->{

            if (INSERT.equals(eys.getIdentification())) {
                employmentSituationMapper.insertSelective(eys);

            }else if(UPDATE.equals(eys.getIdentification())){
                eys.setUpdateby(eys.getCreateby());
                eys.setUpdatedate(eys.getAae036());
                eys.setCreateby(null);
                eys.setAae036(null);
                eys.setAab301(null);
                employmentSituationMapper.updateByPrimaryKeySelective(eys);

            }
        } );

        return 1;
    }

    /**
     * ******************************************************************创业信息************************************************************************
     */

    /**
     * 保存临时信息
     * @param list
     */
    @Override
    public void saveExcelListEP(List<EntrepreneurshipTmp> list) {
        //注解参考saveExcelListPLF()方法
        List<String> selectList =new ArrayList();
        List<String> existenceList =new ArrayList();
        List<EntrepreneurshipTmp> list2 =new ArrayList<>();
        for (EntrepreneurshipTmp entrepreneurshipTmp:list) {
            String aae100=entrepreneurshipTmp.getIdentification();
            if("1".equals(aae100)){
                selectList.add(entrepreneurshipTmp.getEpp002());
            }
        }
        if(selectList.size()>0){
            List<PoorLaborForce> plf=poorLaborForceMapper.selectByPrimaryidCardList(selectList);

            for (PoorLaborForce poorLaborForce:plf) {
                String plf005= poorLaborForce.getPlf005();
                String plf001= poorLaborForce.getPlf001();
                existenceList.add(plf005);
                for (EntrepreneurshipTmp entrepreneurshipTmp:list) {
                    String eys002=entrepreneurshipTmp.getEpp002();
                    if(plf005.equals(eys002)){
                        entrepreneurshipTmp.setEpp002(plf001);
                    }
                }
            }
        }

        //通过差集处理得到不存在的数据
        selectList.removeAll(existenceList);
        if(list.size()>0){
            entrepreneurshipTmpMapper.addList(list);
        }
        for (int i = 0; i < selectList.size(); i++) {
            String plf005=selectList.get(i);
            for (EntrepreneurshipTmp entrepreneurshipTmp:list) {
                String eys002=entrepreneurshipTmp.getEpp002();
                if(plf005.equals(eys002)){
                    entrepreneurshipTmp.setMessage("贫困劳动力（"+entrepreneurshipTmp.getAac003()+"）不存在="+entrepreneurshipTmp.getMessage());
                    entrepreneurshipTmp.setIdentification("2");
                    list2.add(entrepreneurshipTmp);
                }
            }
        }
        if(list2.size()>0){
            entrepreneurshipTmpMapper.updateCardList(list2);
        }
        entrepreneurshipTmpMapper.stored_procedure(list.get(0).getBatch(),"1");
    }

    /**
     * 查询正确信息
     * @param batch
     * @return
     */
    @Override
    public List<EntrepreneurshipTmp> getEntrepreneurshipCorrect(String batch) {
        return entrepreneurshipTmpMapper.getEntrepreneurshipCorrect(batch);
    }

    /**
     * 查询错误信息
     * @param batch
     * @return
     */
    @Override
    public List<EntrepreneurshipTmp> getEntrepreneurshipError(String batch) {
        return entrepreneurshipTmpMapper.getEntrepreneurshipError(batch);
    }
    /**
     * 保存正确信息
     * @param batch
     * @return
     */
    @Override
    public int SaveEntrepreneurship(String batch) {
        return entrepreneurshipTmpMapper.SaveEntrepreneurship(batch);
    }

    /**
     * ******************************************************************培訓信息************************************************************************
     */

    /**
     * 保存临时信息
     * @param list
     */
    @Override
    public void saveExcelListTS(List<TrainingSituationTmp> list) {
        //注解参考saveExcelListPLF()方法
        List<String> selectList =new ArrayList();
        List<String> existenceList =new ArrayList();
        List<TrainingSituationTmp> list2 =new ArrayList<>();
        for (TrainingSituationTmp trainingSituationTmp:list) {
            String aae100=trainingSituationTmp.getIdentification();
            if("1".equals(aae100)){
                selectList.add(trainingSituationTmp.getTsn010());
            }
        }
        if(selectList.size()>0){
            List<PoorLaborForce> plf=poorLaborForceMapper.selectByPrimaryidCardList(selectList);
            for (PoorLaborForce poorLaborForce:plf) {
                String plf005= poorLaborForce.getPlf005();
                String plf001= poorLaborForce.getPlf001();
                existenceList.add(plf005);
                for (TrainingSituationTmp trainingSituationTmp:list) {
                    String tsn010=trainingSituationTmp.getTsn010();
                    if(plf005.equals(tsn010)){
                        trainingSituationTmp.setTsn010(plf001);
                    }
                }
            }
        }
        //通过差集处理得到不存在的数据
        selectList.removeAll(existenceList);
        if(list.size()>0){
            trainingSituationTmpMapper.addList(list);
        }
        for (int i = 0; i < selectList.size(); i++) {
            String plf005=selectList.get(i);
            for (TrainingSituationTmp trainingSituationTmp:list) {
                String eys002=trainingSituationTmp.getTsn010();
                if(plf005.equals(eys002)){
                    trainingSituationTmp.setMessage("贫困劳动力（"+trainingSituationTmp.getAac003()+"）不存在="+trainingSituationTmp.getMessage());
                    trainingSituationTmp.setIdentification("2");
                    list2.add(trainingSituationTmp);
                }
            }
        }
        if(list2.size()>0){
            trainingSituationTmpMapper.updateCardList(list2);
        }
        trainingSituationTmpMapper.stored_procedure(list.get(0).getBatch(),"1");
    }
    /**
     * 查询正确信息
     * @param batch
     * @return
     */
    @Override
    public List<TrainingSituationTmp> getTrainingSituationCorrect(String batch) {

        return trainingSituationTmpMapper.getTrainingSituationCorrect(batch);
    }
    /**
     * 查询错误信息
     * @param batch
     * @return
     */
    @Override
    public List<TrainingSituationTmp> getTrainingSituationError(String batch) {
        return trainingSituationTmpMapper.getTrainingSituationError(batch);
    }
    /**
     * 保存正确信息
     * @param batch
     * @return
     */
    @Override
    public int SaveTrainingSituation(String batch) {
        return trainingSituationTmpMapper.SaveTrainingSituation(batch);
    }

    /**
     * ******************************************************************技工信息************************************************************************
     */

    /**
     * 保存临时信息
     * @param list
     */
    @Override
    public void saveExcelListTSS(List<TechnicalSchoolsTmp> list) {

        //注解参考saveExcelListPLF()方法
        List<String> selectList =new ArrayList();
        List<String> existenceList =new ArrayList();
        List<TechnicalSchoolsTmp> list2 =new ArrayList<>();
        for (TechnicalSchoolsTmp technicalSchoolsTmp:list) {
            String aae100=technicalSchoolsTmp.getIdentification();
            if("1".equals(aae100)){
                selectList.add(technicalSchoolsTmp.getThs002());
            }
        }
        if(selectList.size()>0){
            List<PoorLaborForce> plf=poorLaborForceMapper.selectByPrimaryidCardList(selectList);
            for (PoorLaborForce poorLaborForce:plf) {
                String plf005= poorLaborForce.getPlf005();
                String plf001= poorLaborForce.getPlf001();
                existenceList.add(plf005);
                for (TechnicalSchoolsTmp technicalSchoolsTmp:list) {
                    String eys002=technicalSchoolsTmp.getThs002();
                    if(plf005.equals(eys002)){
                        technicalSchoolsTmp.setThs002(plf001);
                    }
                }
            }
        }

        //通过差集处理得到不存在的数据
        selectList.removeAll(existenceList);
        if(list.size()>0){
            technicalSchoolsTmpMapper.addList(list);
        }
        for (int i = 0; i < selectList.size(); i++) {
            String plf005=selectList.get(i);
            for (TechnicalSchoolsTmp technicalSchoolsTmp:list) {
                String eys002=technicalSchoolsTmp.getThs002();
                if(plf005.equals(eys002)){
                    technicalSchoolsTmp.setMessage("贫困劳动力（"+technicalSchoolsTmp.getAac003()+"）不存在="+technicalSchoolsTmp.getMessage());
                    technicalSchoolsTmp.setIdentification("2");
                    list2.add(technicalSchoolsTmp);
                }
            }
        }
        if(list2.size()>0){
            technicalSchoolsTmpMapper.updateCardList(list2);
        }
        technicalSchoolsTmpMapper.stored_procedure(list.get(0).getBatch(),"1");
    }
    /**
     * 查询正确信息
     * @param batch
     * @return
     */
    @Override
    public List<TechnicalSchoolsTmp> getTechnicalSchoolsCorrect(String batch) {
        return technicalSchoolsTmpMapper.getTechnicalSchoolsCorrect(batch);
    }

    /**
     * 查询错误信息
     * @param batch
     * @return
     */
    @Override
    public List<TechnicalSchoolsTmp> getTechnicalSchoolsError(String batch) {
        return technicalSchoolsTmpMapper.getTechnicalSchoolsError(batch);
    }
    /**
     * 保存正确信息
     * @param batch
     * @return
     */
    @Override
    public int SaveTechnicalSchools(String batch) {
        return technicalSchoolsTmpMapper.SaveTechnicalSchools(batch);
    }


}
