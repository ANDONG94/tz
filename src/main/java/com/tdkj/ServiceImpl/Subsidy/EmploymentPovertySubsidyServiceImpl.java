package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.EmploymentPovertySubsidyMapper;
import com.tdkj.model.EmploymentPovertySubsidy;
import com.tdkj.service.Subsidy.EmploymentPovertySubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/29.
 */
@Service
public class EmploymentPovertySubsidyServiceImpl implements EmploymentPovertySubsidyService {

    @Autowired
    private EmploymentPovertySubsidyMapper employmentPovertySubsidyMapper;


    @Override
    public int deleteByPrimaryKey(String eps001, String realname) {
        return employmentPovertySubsidyMapper.deleteByPrimaryKey(eps001,realname);
    }

    @Override
    public int insert(EmploymentPovertySubsidy employmentPovertySubsidy) {
        return employmentPovertySubsidyMapper.insert(employmentPovertySubsidy);
    }

    @Override
    public int insertSelective(EmploymentPovertySubsidy employmentPovertySubsidy) {
        return employmentPovertySubsidyMapper.insertSelective(employmentPovertySubsidy);
    }

    @Override
    public EmploymentPovertySubsidy selectByPrimaryKey(String eps001) {
        return employmentPovertySubsidyMapper.selectByPrimaryKey(eps001);
    }

    @Override
    public int updateByPrimaryKeySelective(EmploymentPovertySubsidy employmentPovertySubsidy) {
        return employmentPovertySubsidyMapper.updateByPrimaryKeySelective(employmentPovertySubsidy);
    }

    @Override
    public int updateByPrimaryKey(EmploymentPovertySubsidy employmentPovertySubsidy) {
        return employmentPovertySubsidyMapper.updateByPrimaryKey(employmentPovertySubsidy);
    }

    @Override
    public List<EmploymentPovertySubsidy> querEmploymentPovertySubsidyByEps002(String eps002) {
        return employmentPovertySubsidyMapper.querEmploymentPovertySubsidyByEps002(eps002);
    }

    @Override
    public int deleteEmploymentPovertySubsidyByEps002(String eps002, String realname) {
        return employmentPovertySubsidyMapper.deleteEmploymentPovertySubsidyByEps002(eps002,realname);
    }

    @Override
    public EmploymentPovertySubsidy selectSubsibyByEps002(String eps002) {
        return employmentPovertySubsidyMapper.selectSubsibyByEps002(eps002);
    }
}
