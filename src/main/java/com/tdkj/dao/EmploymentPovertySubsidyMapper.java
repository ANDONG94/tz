package com.tdkj.dao;

import com.tdkj.model.EmploymentPovertySubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmploymentPovertySubsidyMapper {

    int deleteByPrimaryKey(@Param("eps001")String eps001,@Param("realname")String realname);

    int insert(EmploymentPovertySubsidy employmentPovertySubsidy);

    int insertSelective(EmploymentPovertySubsidy employmentPovertySubsidy);

    EmploymentPovertySubsidy selectByPrimaryKey(String eps001);

    int updateByPrimaryKeySelective(EmploymentPovertySubsidy employmentPovertySubsidy);

    int updateByPrimaryKey(EmploymentPovertySubsidy employmentPovertySubsidy);

    /**
     * 根据扶贫基地id  查询补贴
     * @return
     */
    public List<EmploymentPovertySubsidy> querEmploymentPovertySubsidyByEps002(String eps002);


    /**
     *根据扶贫基地id  删除补贴
     * @param eps002
     * @param realname
     * @return
     */
    public int deleteEmploymentPovertySubsidyByEps002(@Param("eps002")String eps002, @Param("realname")String realname);


    /**
     * 根据扶贫基地id 查询补贴之和
     * @param eps002
     * @return
     */
    public EmploymentPovertySubsidy selectSubsibyByEps002(String eps002);


}