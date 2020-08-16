package com.tdkj.dao;

import com.tdkj.model.VentureSubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VentureSubsidyMapper {
    int deleteByPrimaryKey(@Param("ves001")String ves001, @Param("realname")String realname);

    int insert(VentureSubsidy ventureSubsidy);

    int insertSelective(VentureSubsidy ventureSubsidy);

    VentureSubsidy selectByPrimaryKey(String ves001);

    int updateByPrimaryKeySelective(VentureSubsidy ventureSubsidy);

    int updateByPrimaryKey(VentureSubsidy ventureSubsidy);

    /**
     * 根据劳动力id  查询该劳动力的创业补贴信息
     * @return
     */
    public List<VentureSubsidy> queryChuangYeSubsidyByVes003(String ves003);


    /**
     *根据就业主键 删除创业补贴信息
     * @param ves002
     * @param realname
     * @return
     */
    public int deleteChuangYeSubsidyByVes002(@Param("ves002")String ves002, @Param("realname")String realname);


    /**
     * 根据劳动力id  查询该劳动力的创业补贴count信息
     * @return
     */
    public VentureSubsidy queryChuangYeSubsidyCountByVes003(String ves003);


    /**
     * 根据劳动力id  查询就业id的创业补贴信息
     * @return
     */
    public VentureSubsidy queryChuangYeSubsidyCountByVes002(String ves002);


}