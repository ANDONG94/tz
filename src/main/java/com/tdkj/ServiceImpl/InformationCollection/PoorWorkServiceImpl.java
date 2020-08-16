package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.PoorHouseholdsMapper;
import com.tdkj.model.PoorHouseholds;
import com.tdkj.service.InformationCollection.PoorWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
@Service
public class PoorWorkServiceImpl implements PoorWorkService{

    @Autowired
    private PoorHouseholdsMapper poorHouseholdsMapper;

    @Override
    public int deleteByPrimaryKey(String phd001) {
        return poorHouseholdsMapper.deleteByPrimaryKey(phd001);
    }

    @Override
    public int insert(PoorHouseholds record) {
        return 0;
    }

    @Override
    public int insertSelective(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.insertSelective(poorHouseholds);
    }

    @Override
    public PoorHouseholds selectByPrimaryKey(String phd001) {
        return poorHouseholdsMapper.selectByPrimaryKey(phd001);
    }

    @Override
    public int updateByPrimaryKeySelective(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.updateByPrimaryKeySelective(poorHouseholds);
    }

    @Override
    public int updateByPrimaryKey(PoorHouseholds record) {
        return 0;
    }

    /**
     * 根据行政区划查询贫困户信息
     * @return
     */
    @Override
    public List<PoorHouseholds> queryAllPoorWorkByAab301(PoorHouseholds poorHouseholds,String startRecord,String pageSize) {
        return poorHouseholdsMapper.queryAllPoorWorkByAab301(poorHouseholds,startRecord,pageSize);
    }

    @Override
    public List<PoorHouseholds> queryPoi(String aab301, String phd002_scan, String phd003_scan, String phd012_scan) {
        return poorHouseholdsMapper.queryPoi(aab301);
    }

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(PoorHouseholds poorHouseholds){
        return poorHouseholdsMapper.queryAllByAab301(poorHouseholds);
    }

    /**
     * 根据身份证号码查询，该用户是否已经登记
     * @param phd003
     * @return
     */
    @Override
    public String queryPoorByIdCard(String phd003) {
        return poorHouseholdsMapper.queryPoorByIdCard(phd003);
    }


    //************************************以下是台账信息**********************************************************************

    /**
     * 根据行政区划查询贫困户台账信息
     * @return
     */
    @Override
    public List<PoorHouseholds> queryPoorAccountByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize) {
        return poorHouseholdsMapper.queryPoorAccountByAab301(poorHouseholds,startRecord,pageSize);
    }

    /**
     *根据行政区划查询台账总共多条
     * @return
     */
    @Override
    public String queryAllAccoutByAab301(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.queryAllAccoutByAab301(poorHouseholds);
    }

    @Override
    public PoorHouseholds selectByPrimaryidCard(String phd003) {
        return poorHouseholdsMapper.selectByPrimaryidCard(phd003);
    }

    /**
     * 根据行政区划查询待确认贫困户信息
     * @return
     */
    @Override
    public List<PoorHouseholds> queryToBeComfirePoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize) {
        return poorHouseholdsMapper.queryToBeComfirePoorByAab301(poorHouseholds,startRecord,pageSize);
    }

    /**
     *根据行政区划查询待确认贫困户总共多条
     * @return
     */
    @Override
    public String queryAllToBeComfireByAab301(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.queryAllToBeComfireByAab301(poorHouseholds);
    }

    /**
     * 根据行政区划查询待完善贫困户信息
     * @return
     */
    @Override
    public List<PoorHouseholds> queryToBeCompletePoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize) {
        return poorHouseholdsMapper.queryToBeCompletePoorByAab301(poorHouseholds,startRecord,pageSize);
    }

    /**
     *根据行政区划查询待完善贫困户总共多条
     * @return
     */
    @Override
    public String queryAllToBeCompleteByAab301(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.queryAllToBeCompleteByAab301(poorHouseholds);
    }

    @Override
    public int F_phd008(String phd001) {
        return poorHouseholdsMapper.F_phd008(phd001);
    }

    //根据劳动力id   修改帮扶措施中的 脱贫状态和易地扶贫
    @Override
    public int updateEysEppTsnThsByPlf001(String phd001) {
        return poorHouseholdsMapper.updateEysEppTsnThsByPlf001(phd001);
    }


    /**
     * 待帮扶户信息
     * @param poorHouseholds
     * @param startRecord
     * @param pageSize
     * @return
     */
    @Override
    public List<PoorHouseholds> queryTobeHelpPoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize) {
        return poorHouseholdsMapper.queryTobeHelpPoorByAab301(poorHouseholds,startRecord,pageSize);
    }

    /**
     * 待帮扶户 总数
     * @param poorHouseholds
     * @return
     */
    @Override
    public String queryAllTobeHelpPoorByAab301(PoorHouseholds poorHouseholds) {
        return poorHouseholdsMapper.queryAllTobeHelpPoorByAab301(poorHouseholds);
    }


}
