package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.EmploymentSituationMapper;
import com.tdkj.model.EmploymentSituation;
import com.tdkj.model.EysPlf;
import com.tdkj.service.InformationCollection.EmploymentSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-28.
 */
@Service
public class EmploymentSituationServiceImpl implements EmploymentSituationService {

    @Autowired
    private EmploymentSituationMapper employmentSituationMapper;

    @Override
    public int deleteByPrimaryKey(String eys001) {
        return employmentSituationMapper.deleteByPrimaryKey(eys001);
    }

    @Override
    public int insert(EmploymentSituation record) {
        return 0;
    }

    @Override
    public int insertSelective(EmploymentSituation employmentSituation) {
        return employmentSituationMapper.insertSelective(employmentSituation);
    }

    @Override
    public EmploymentSituation selectByPrimaryKey(String eys001) {
        return employmentSituationMapper.selectByPrimaryKey(eys001);
    }

    @Override
    public int updateByPrimaryKeySelective(EmploymentSituation employmentSituation) {
        return employmentSituationMapper.updateByPrimaryKeySelective(employmentSituation);
    }

    @Override
    public int updateByPrimaryKeySelectiveKill() {



        return 0;
    }

    @Override
    public int updateByPrimaryKey(EmploymentSituation record) {
        return 0;
    }

    /**
     * 根据劳动力id  查询该劳动力的就业技能
     * @return
     */
    @Override
    public List<EmploymentSituation> queryEmploymentSituationByEys002(String eys002) {
        return employmentSituationMapper.queryEmploymentSituationByEys002(eys002);
    }


    /**
     * 根据劳动力id  查询该劳动力的公益性岗位
     * @return
     */
    @Override
    public List<EmploymentSituation> queryEmploymentSituationByEys002Post(String eys002) {
        return employmentSituationMapper.queryEmploymentSituationByEys002Post(eys002);
    }


    /**
     * 根据劳动力id  删除该劳动力下的所有就业技能情况
     * @param plf001
     * @return
     */
    @Override
    public int deleteEmploymentSituationByEys002(String eys002,String realname) {
        return employmentSituationMapper.deleteEmploymentSituationByEys002(eys002,realname);
    }

    /**
     * 删除所有公益性岗位有关的就业信息
     * @param eys002
     * @param realname
     * @return
     */
    @Override
    public int deletePublicPostByEys002(String eys002, String realname) {
        return employmentSituationMapper.deletePublicPostByEys002(eys002,realname);
    }

    //************************************以下是台账信息**********************************************************************

    /**
     * 根据行政区划查询劳动力就业台账信息
     * @param aab301
     * @return
     */
    @Override
    public List<EysPlf> queryEmploymentSituationAccountByAab301(EysPlf eysPlf, String startRecord, String pageSize) {
        return employmentSituationMapper.queryEmploymentSituationAccountByAab301(eysPlf,startRecord,pageSize);
    }


    /**
     *根据行政区划查询劳动力就业台账总共多条
     * @return
     */
    @Override
    public String queryAllEmploymentSituationByAab301(EysPlf eysPlf) {
        return employmentSituationMapper.queryAllEmploymentSituationByAab301(eysPlf);
    }

    /**
     * 根据行政区划查询劳动力公益性岗位台账信息
     * @return
     */
    @Override
    public List<EysPlf> queryPublicPostAccountByAab301(EysPlf eysPlf, String startRecord, String pageSize) {
        return employmentSituationMapper.queryPublicPostAccountByAab301(eysPlf,startRecord,pageSize);
    }

    /**
     *根据行政区划查询劳动力公益性岗位台账总共多条
     * @return
     */
    @Override
    public String queryPublicPostCountByAab301(EysPlf eysPlf) {
        return employmentSituationMapper.queryPublicPostCountByAab301(eysPlf);
    }

    @Override
    public int updateIsNewByEys002(String eys002) {
        return employmentSituationMapper.updateIsNewByEys002(eys002);
    }

    //根据劳动力id 查询最新的就业信息
    @Override
    public EmploymentSituation queryNewTrainSituation(String eys002) {
        return employmentSituationMapper.queryNewEmploymentSituation(eys002);
    }

    @Override
    public int f_jy_cy(String plf001) {
        return employmentSituationMapper.f_jy_cy(plf001);
    }

    /**
     * 根据行政区划查询查询劳动力就业培训列表
     * @return
     */
    @Override
    public List<EysPlf> queryEmploymentSituationTrainingByAab301(EysPlf eysPlf, String startRecord, String pageSize) {
        return employmentSituationMapper.queryEmploymentSituationTrainingByAab301(eysPlf,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询查询劳动力就业培训总数
     * @return
     */
    @Override
    public String queryAllEmploymentSituationTrainingByAab301(EysPlf eysPlf) {

        return employmentSituationMapper.queryAllEmploymentSituationTrainingByAab301(eysPlf);
    }



    /******************************以下是有问题就业信息查询 ************************************************************************************************************/

    @Override
    public List<EysPlf> queryErrorEmploymentSituationAccountByAab301(EysPlf eysPlf, String startRecord, String pageSize) {
        return employmentSituationMapper.queryErrorEmploymentSituationAccountByAab301(eysPlf,startRecord,pageSize);
    }

    @Override
    public String queryAllErrorEmploymentSituationByAab301(EysPlf eysPlf) {
        return employmentSituationMapper.queryAllErrorEmploymentSituationByAab301(eysPlf);
    }



    //************************************以下是弱劳动力就业台账信息**********************************************************************


    /**
     * 根据行政区划查询弱劳动力就业台账信息
     * @param aab301
     * @return
     */
    @Override
    public List<EysPlf> queryRuoEmploymentSituationAccountByAab301(EysPlf eysPlf, String startRecord, String pageSize){
        return employmentSituationMapper.queryRuoEmploymentSituationAccountByAab301(eysPlf,startRecord,pageSize);
    }


    /**
     *根据行政区划查询弱劳动力就业台账总共多条
     * @return
     */
    @Override
    public String queryAllRuoEmploymentSituationByAab301(EysPlf eysPlf){
        return employmentSituationMapper.queryAllRuoEmploymentSituationByAab301(eysPlf);
    }

}
