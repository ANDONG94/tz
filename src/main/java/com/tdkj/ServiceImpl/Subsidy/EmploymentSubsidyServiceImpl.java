package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.EmploymentSubsidyMapper;
import com.tdkj.model.EmploymentSubsidy;
import com.tdkj.service.Subsidy.EmploymentSubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 就业补贴
 */
@Service
public class EmploymentSubsidyServiceImpl implements EmploymentSubsidyService {

    @Autowired
    private EmploymentSubsidyMapper employmentSubsidyMapper;

    @Override
    public int deleteByPrimaryKey(String ets001,String realname) {
        return employmentSubsidyMapper.deleteByPrimaryKey(ets001,realname);
    }

    @Override
    public int insert(EmploymentSubsidy employmentSubsidy) {
        return employmentSubsidyMapper.insert(employmentSubsidy);
    }

    @Override
    public int insertSelective(EmploymentSubsidy employmentSubsidy) {
        return employmentSubsidyMapper.insertSelective(employmentSubsidy);
    }

    @Override
    public EmploymentSubsidy selectByPrimaryKey(String ets001) {
        return employmentSubsidyMapper.selectByPrimaryKey(ets001);
    }

    @Override
    public int updateByPrimaryKeySelective(EmploymentSubsidy employmentSubsidy) {
        return employmentSubsidyMapper.updateByPrimaryKeySelective(employmentSubsidy);
    }

    @Override
    public int updateByPrimaryKey(EmploymentSubsidy employmentSubsidy) {
        return employmentSubsidyMapper.updateByPrimaryKey(employmentSubsidy);
    }

    /**
     * 根据劳动力id  查询补贴信息
     * @param ets003
     * @return
     */
    @Override
    public List<EmploymentSubsidy> querEmploymentSubsidyByEts003(String ets003) {
        return employmentSubsidyMapper.querEmploymentSubsidyByEts003(ets003);
    }

    /**
     * 根据就业id  删除补贴信息
     * @param ets002
     * @param realname
     * @return
     */
    @Override
    public int deleteJiuYeSubsidyByEts002(String ets002, String realname) {
        return employmentSubsidyMapper.deleteJiuYeSubsidyByEts002(ets002,realname);
    }

    /**
     * 根据劳动力id  查询补贴之和信息
     * @param ets003
     * @return
     */
    @Override
    public EmploymentSubsidy querEmploymentSubsidyCountByEts003(String ets003) {
        return employmentSubsidyMapper.querEmploymentSubsidyCountByEts003(ets003);
    }

    @Override
    public EmploymentSubsidy querEmploymentSubsidyCountByEts002(String ets002) {
        return employmentSubsidyMapper.querEmploymentSubsidyCountByEts002(ets002);
    }
}
