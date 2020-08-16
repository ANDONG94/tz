package com.tdkj.service.InformationCollection;

import com.tdkj.model.PoorHouseholds;

import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
public interface PoorWorkService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String phd001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    int insert(PoorHouseholds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    int insertSelective(PoorHouseholds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    PoorHouseholds selectByPrimaryKey(String phd001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PoorHouseholds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_households
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PoorHouseholds record);

    /**
     * 根据行政区划查询贫困户信息
     * @param aab301
     * @return
     */
    public List<PoorHouseholds> queryAllPoorWorkByAab301(PoorHouseholds poorHouseholds,String startRecord,String pageSize);


    /**
     * 根据行政区划查询贫困户信息
     * @param aab301
     * @return
     */
    List<PoorHouseholds> queryPoi(String aab301, String phd002_scan, String phd003_scan, String phd012_scan);



    /**
     * 根据行政区划查询总共多条
     * @return
     */
    String queryAllByAab301(PoorHouseholds poorHouseholds);

    /**
     * 根据身份证号码查询，该用户是否已经登记
     * @param phd003
     * @return
     */
    String queryPoorByIdCard(String phd003);


    //************************************以下是台账信息**********************************************************************


    /**
     * 根据行政区划查询贫困户台账信息
     * @return
     */
    List<PoorHouseholds> queryPoorAccountByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize);


    /**
     *根据行政区划查询台账总共多条
     * @return
     */
    String queryAllAccoutByAab301(PoorHouseholds poorHouseholds);


    PoorHouseholds selectByPrimaryidCard(String phd003);


    /**
     * 根据行政区划查询待确认贫困户信息
     * @return
     */
    List<PoorHouseholds> queryToBeComfirePoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize);


    /**
     *根据行政区划查询待确认贫困户总共多条
     * @return
     */
    public String queryAllToBeComfireByAab301(PoorHouseholds poorHouseholds);


    /**
     * 根据行政区划查询待完善贫困户信息
     * @return
     */
    List<PoorHouseholds> queryToBeCompletePoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize);


    /**
     *根据行政区划查询待完善贫困户总共多条
     * @return
     */
    String queryAllToBeCompleteByAab301(PoorHouseholds poorHouseholds);

    /**
     * 計算劳动力数量
     *
     * @param phd001
     * @return
     */
    int F_phd008(String phd001);

    //根据劳动力id   修改帮扶措施中的 脱贫状态和易地扶贫
    int updateEysEppTsnThsByPlf001(String phd001);


    //************************************以下是  待帮扶   信息**********************************************************************


    /**
     * 根据行政区划查询   待帮扶贫困户 信息
     * @return
     */
    List<PoorHouseholds> queryTobeHelpPoorByAab301(PoorHouseholds poorHouseholds, String startRecord, String pageSize);


    /**
     *根据行政区划查询  待帮扶贫困户  总共多条
     * @return
     */
    String queryAllTobeHelpPoorByAab301(PoorHouseholds poorHouseholds);
}