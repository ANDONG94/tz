package com.tdkj.service.Subsidy;

import com.tdkj.model.CommunityFactorySubsidy;
import com.tdkj.model.EmploymentSubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hedd on 2019/8/29.
 */
public interface CommunityFactorySubsidyService {

    int deleteByPrimaryKey(String cfs001,String realname);

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
    public int deleteCommunityFactorySubsidyByCfs002(String cfs002,String realname);

    /**
     * 根据工厂id  查询所有补贴之和
     * @param cfs002
     * @return
     */
    public CommunityFactorySubsidy selectSubsibyByCfs002(String cfs002);



}
