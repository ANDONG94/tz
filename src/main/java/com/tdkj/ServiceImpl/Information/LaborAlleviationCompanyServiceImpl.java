package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.LaborAlleviationCompanyMapper;
import com.tdkj.model.LaborAlleviationCompany;
import com.tdkj.service.Information.LaborAlleviationCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 劳务扶贫公司
 */
@Service
public class LaborAlleviationCompanyServiceImpl implements LaborAlleviationCompanyService {

    @Autowired
    private LaborAlleviationCompanyMapper laborAlleviationCompanyMapper;

    @Override
    public int deleteByPrimaryKey(String lac001, String realname) {
        return laborAlleviationCompanyMapper.deleteByPrimaryKey(lac001,realname);
    }

    @Override
    public int insert(LaborAlleviationCompany laborAlleviationCompany) {
        return 0;
    }

    @Override
    public int insertSelective(LaborAlleviationCompany laborAlleviationCompany) {
        return laborAlleviationCompanyMapper.insertSelective(laborAlleviationCompany);
    }

    @Override
    public LaborAlleviationCompany selectByPrimaryKey(String lac001) {
        return laborAlleviationCompanyMapper.selectByPrimaryKey(lac001);
    }

    @Override
    public int updateByPrimaryKeySelective(LaborAlleviationCompany laborAlleviationCompany) {
        return laborAlleviationCompanyMapper.updateByPrimaryKeySelective(laborAlleviationCompany);
    }

    @Override
    public int updateByPrimaryKey(LaborAlleviationCompany laborAlleviationCompany) {
        return 0;
    }

    @Override
    public List<LaborAlleviationCompany> queryLaborAlleviationCompanyByAab301(LaborAlleviationCompany laborAlleviationCompany, String startRecord, String pageSize) {
        return laborAlleviationCompanyMapper.queryLaborAlleviationCompanyByAab301(laborAlleviationCompany,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(LaborAlleviationCompany laborAlleviationCompany) {
        return laborAlleviationCompanyMapper.queryAllByAab301(laborAlleviationCompany);
    }
}
