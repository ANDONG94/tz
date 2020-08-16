package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.CommunityFactorySubsidyMapper;
import com.tdkj.model.CommunityFactorySubsidy;
import com.tdkj.service.Subsidy.CommunityFactorySubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/29.
 */
@Service
public class CommunityFactorySubsidyServiceImpl implements CommunityFactorySubsidyService {

    @Autowired
    private CommunityFactorySubsidyMapper communityFactorySubsidyMapper;

    @Override
    public int deleteByPrimaryKey(String cfs001, String realname) {
        return communityFactorySubsidyMapper.deleteByPrimaryKey(cfs001,realname);
    }

    @Override
    public int insert(CommunityFactorySubsidy communityFactorySubsidy) {
        return communityFactorySubsidyMapper.insert(communityFactorySubsidy);
    }

    @Override
    public int insertSelective(CommunityFactorySubsidy communityFactorySubsidy) {
        return communityFactorySubsidyMapper.insertSelective(communityFactorySubsidy);
    }

    @Override
    public CommunityFactorySubsidy selectByPrimaryKey(String cfs001) {
        return communityFactorySubsidyMapper.selectByPrimaryKey(cfs001);
    }

    @Override
    public int updateByPrimaryKeySelective(CommunityFactorySubsidy communityFactorySubsidy) {
        return communityFactorySubsidyMapper.updateByPrimaryKeySelective(communityFactorySubsidy);
    }

    @Override
    public int updateByPrimaryKey(CommunityFactorySubsidy communityFactorySubsidy) {
        return communityFactorySubsidyMapper.updateByPrimaryKey(communityFactorySubsidy);
    }

    @Override
    public List<CommunityFactorySubsidy> querCommunityFactorySubsidyByCfs002(String cfs002) {
        return communityFactorySubsidyMapper.querCommunityFactorySubsidyByCfs002(cfs002);
    }

    @Override
    public int deleteCommunityFactorySubsidyByCfs002(String cfs002, String realname) {
        return communityFactorySubsidyMapper.deleteCommunityFactorySubsidyByCfs002(cfs002,realname);
    }

    @Override
    public CommunityFactorySubsidy selectSubsibyByCfs002(String cfs002) {
        return communityFactorySubsidyMapper.selectSubsibyByCfs002(cfs002);
    }
}
