package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.BusinessGuaranteeLoanMapper;
import com.tdkj.model.BusinessGuaranteeLoan;
import com.tdkj.service.Information.BusinessGuaranteeLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/11/22.
 * 创业担保贷款
 */
@Service
public class BusinessGuaranteeLoanServiceImpl implements BusinessGuaranteeLoanService {

    @Autowired
    private BusinessGuaranteeLoanMapper businessGuaranteeLoanMapper;

    @Override
    public int deleteByPrimaryKey(String bgl001,String realname) {
        return businessGuaranteeLoanMapper.deleteByPrimaryKey(bgl001,realname);
    }

    @Override
    public int insert(BusinessGuaranteeLoan businessGuaranteeLoan) {
        return businessGuaranteeLoanMapper.insert(businessGuaranteeLoan);
    }

    @Override
    public int insertSelective(BusinessGuaranteeLoan businessGuaranteeLoan) {
        return businessGuaranteeLoanMapper.insertSelective(businessGuaranteeLoan);
    }

    @Override
    public BusinessGuaranteeLoan selectByPrimaryKey(String bgl001) {
        return businessGuaranteeLoanMapper.selectByPrimaryKey(bgl001);
    }

    @Override
    public int updateByPrimaryKeySelective(BusinessGuaranteeLoan businessGuaranteeLoan) {
        return businessGuaranteeLoanMapper.updateByPrimaryKeySelective(businessGuaranteeLoan);
    }

    @Override
    public int updateByPrimaryKey(BusinessGuaranteeLoan businessGuaranteeLoan) {
        return businessGuaranteeLoanMapper.updateByPrimaryKey(businessGuaranteeLoan);
    }

    @Override
    public List<BusinessGuaranteeLoan> queryBusinessGuaranteeLoanByAab301(BusinessGuaranteeLoan businessGuaranteeLoan, String startRecord, String pageSize) {
        return businessGuaranteeLoanMapper.queryBusinessGuaranteeLoanByAab301(businessGuaranteeLoan,startRecord,pageSize);
    }

    @Override
    public String queryAllBusinessGuaranteeLoanCountByAab301(BusinessGuaranteeLoan businessGuaranteeLoan) {
        return businessGuaranteeLoanMapper.queryAllBusinessGuaranteeLoanCountByAab301(businessGuaranteeLoan);
    }
}
