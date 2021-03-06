package com.tdkj.service.InformationCollection;

import com.tdkj.model.TechnicalSchools;
import com.tdkj.model.ThsPlf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
public interface TechnicalSchoolsService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String ths001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    int insert(TechnicalSchools record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    int insertSelective(TechnicalSchools record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    TechnicalSchools selectByPrimaryKey(String ths001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TechnicalSchools record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table technical_schools
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TechnicalSchools record);

    /**
     * 根据劳动力id 查询技工院校信息
     * @param ths002
     * @return
     */
    public List<TechnicalSchools> queryTechnicalSchoolsByThs002(String ths002);


    /**
     * 根据劳动力信息删除技工院校信息
     * @param ths002
     * @return
     */
    public int deleteTechnicalSchoolsByThs002(String ths002,String realname);


    /**
     * 根据行政区划查询劳动力技工院校台账信息
     * @param aab301
     * @return
     */
    public List<ThsPlf> queryTechnicalSchoolAccountByAab301(ThsPlf thsPlf, String startRecord, String pageSize);


    /**
     *根据行政区划查询劳动力技工院校总共多条
     * @param aab301
     * @return
     */
    public String queryAllTechnicalSchoolByAab301(ThsPlf thsPlf);

    /**
     * 根据劳动力Id 修改isnew=0
     * @param ths002
     * @return
     */
    public int updateIsNewByThs002(String ths002);

    //根据劳动力id 查询最新的技工院校信息
    public TechnicalSchools queryNewTechnicalSchools(String ths002);

}
