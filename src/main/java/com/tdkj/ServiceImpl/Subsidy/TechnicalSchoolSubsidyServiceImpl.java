package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.TechnicalSchoolSubsidyMapper;
import com.tdkj.model.TechnicalSchoolSubsidy;
import com.tdkj.service.Subsidy.TechnicalSchoolSubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 技校补贴
 */
@Service
public class TechnicalSchoolSubsidyServiceImpl implements TechnicalSchoolSubsidyService {
    @Autowired
    private TechnicalSchoolSubsidyMapper technicalSchoolSubsidyMapper;

    @Override
    public int deleteByPrimaryKey(String tss001,String realname) {
        return technicalSchoolSubsidyMapper.deleteByPrimaryKey(tss001,realname);
    }

    @Override
    public int insert(TechnicalSchoolSubsidy technicalSchoolSubsidy) {
        return technicalSchoolSubsidyMapper.insert(technicalSchoolSubsidy);
    }

    @Override
    public int insertSelective(TechnicalSchoolSubsidy technicalSchoolSubsidy) {
        return technicalSchoolSubsidyMapper.insertSelective(technicalSchoolSubsidy);
    }

    @Override
    public TechnicalSchoolSubsidy selectByPrimaryKey(String tss001) {
        return technicalSchoolSubsidyMapper.selectByPrimaryKey(tss001);
    }

    @Override
    public int updateByPrimaryKeySelective(TechnicalSchoolSubsidy technicalSchoolSubsidy) {
        return technicalSchoolSubsidyMapper.updateByPrimaryKeySelective(technicalSchoolSubsidy);
    }

    @Override
    public int updateByPrimaryKey(TechnicalSchoolSubsidy technicalSchoolSubsidy) {
        return technicalSchoolSubsidyMapper.updateByPrimaryKey(technicalSchoolSubsidy);
    }

    @Override
    public List<TechnicalSchoolSubsidy> querySchoolSubsidyByTss003(String tss003) {
        return technicalSchoolSubsidyMapper.querySchoolSubsidyByTss003(tss003);
    }

    @Override
    public int deleteSchoolSubsidyByTss002(String tss002, String realname) {
        return technicalSchoolSubsidyMapper.deleteSchoolSubsidyByTss002(tss002,realname);
    }

    @Override
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss003(String tss003) {
        return technicalSchoolSubsidyMapper.querySchoolSubsidyCountByTss003(tss003);
    }

    @Override
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss002(String tss002) {
        return technicalSchoolSubsidyMapper.querySchoolSubsidyCountByTss002(tss002);
    }
}
