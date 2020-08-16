package com.tdkj.dao;

import com.tdkj.model.CommunityFactorySubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityFactorySubsidyMapper {
    int deleteByPrimaryKey(@Param("cfs001")String cfs001,@Param("realname")String realname);

    int insert(CommunityFactorySubsidy communityFactorySubsidy);

    int insertSelective(CommunityFactorySubsidy communityFactorySubsidy);

    CommunityFactorySubsidy selectByPrimaryKey(String cfs001);

    int updateByPrimaryKeySelective(CommunityFactorySubsidy communityFactorySubsidy);

    int updateByPrimaryKey(CommunityFactorySubsidy communityFactorySubsidy);


    /**
     * 根据社区工厂id  查询补贴
     * @return
     */
    public List<CommunityFactorySubsidy> querCommunityFactorySubsidyByCfs002(String cfs002);


    /**
     *根据社区工厂id  删除补贴
     * @param cfs002
     * @param realname
     * @return
     */
    public int deleteCommunityFactorySubsidyByCfs002(@Param("cfs002")String cfs002, @Param("realname")String realname);

    /**
     * 根据工厂id  查询所有补贴之和
     * @param cfs002
     * @return
     */
    public CommunityFactorySubsidy selectSubsibyByCfs002(String cfs002);


}