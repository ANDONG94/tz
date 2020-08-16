package com.tdkj.dao;

import com.tdkj.model.TechnicalSchoolSubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TechnicalSchoolSubsidyMapper {


    int deleteByPrimaryKey(@Param("tss001")String tss001, @Param("realname")String realname);

    int insert(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    int insertSelective(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    TechnicalSchoolSubsidy selectByPrimaryKey(String tss001);

    int updateByPrimaryKeySelective(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    int updateByPrimaryKey(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    /**
     * 根据劳动力id  查询该劳动力的技校补贴信息
     * @return
     */
    public List<TechnicalSchoolSubsidy> querySchoolSubsidyByTss003(String tss003);


    /**
     *根据就业主键 删除技校补贴信息
     * @param tss002
     * @param realname
     * @return
     */
    public int deleteSchoolSubsidyByTss002(@Param("tss002")String tss002,@Param("realname")String realname);


    /**
     * 根据劳动力id  查询该劳动力的技校补贴信息
     * @return
     */
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss003(String tss003);


    /**
     * 根据劳动力id  查询该技校id的技校补贴信息
     * @return
     */
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss002(String tss002);


}