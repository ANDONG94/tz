package com.tdkj.dao;

import com.tdkj.model.EmploymentSubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmploymentSubsidyMapper {

    int deleteByPrimaryKey( @Param("ets001")String ets001, @Param("realname")String realname);

    int insert(EmploymentSubsidy employmentSubsidy);

    int insertSelective(EmploymentSubsidy employmentSubsidy);

    EmploymentSubsidy selectByPrimaryKey(String ets001);

    int updateByPrimaryKeySelective(EmploymentSubsidy employmentSubsidy);

    int updateByPrimaryKey(EmploymentSubsidy employmentSubsidy);

    /**
     * 根据劳动力id  查询该劳动力的就业补贴信息
     * @return
     */
    public List<EmploymentSubsidy> querEmploymentSubsidyByEts003(String ets003);

    /**
     *根据就业主键 删除就业补贴信息
     * @param ets002
     * @param realname
     * @return
     */
    public int deleteJiuYeSubsidyByEts002(@Param("ets002")String ets002, @Param("realname")String realname);


    /**
     * 根据劳动力id  查询该劳动力的就业补贴信息
     * @return
     */
    public EmploymentSubsidy querEmploymentSubsidyCountByEts003(String ets003);


    /**
     * 根据劳动力id  查询该就业id的就业补贴信息
     * @return
     */
    public EmploymentSubsidy querEmploymentSubsidyCountByEts002(String ets002);

}