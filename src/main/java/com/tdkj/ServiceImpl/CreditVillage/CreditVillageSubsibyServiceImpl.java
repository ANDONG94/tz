package com.tdkj.ServiceImpl.CreditVillage;

import com.tdkj.dao.CreditVillage.CreditVillageSubsibyMapper;
import com.tdkj.model.CreditVillageSubsiby;
import com.tdkj.service.CreditVillage.CreditVillageSubsibyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/12/7.
 */
@Service
public class CreditVillageSubsibyServiceImpl implements CreditVillageSubsibyService {

    @Autowired
    private CreditVillageSubsibyMapper creditVillageSubsibyMapper;

    @Override
    public int deleteByPrimaryKey(String cvs001) {
        return creditVillageSubsibyMapper.deleteByPrimaryKey(cvs001);
    }

    @Override
    public int insert(CreditVillageSubsiby creditVillageSubsiby) {
        return creditVillageSubsibyMapper.insert(creditVillageSubsiby);
    }

    @Override
    public int insertSelective(CreditVillageSubsiby creditVillageSubsiby) {
        return creditVillageSubsibyMapper.insertSelective(creditVillageSubsiby);
    }

    @Override
    public CreditVillageSubsiby selectByPrimaryKey(String cvs001) {
        return creditVillageSubsibyMapper.selectByPrimaryKey(cvs001);
    }

    @Override
    public int updateByPrimaryKeySelective(CreditVillageSubsiby creditVillageSubsiby) {
        return creditVillageSubsibyMapper.updateByPrimaryKeySelective(creditVillageSubsiby);
    }

    @Override
    public int updateByPrimaryKey(CreditVillageSubsiby creditVillageSubsiby) {
        return creditVillageSubsibyMapper.updateByPrimaryKey(creditVillageSubsiby);
    }

    @Override
    public List<CreditVillageSubsiby> querCreditVillageSubsibyByCvs002(String cvs002) {
        return creditVillageSubsibyMapper.querCreditVillageSubsibyByCvs002(cvs002);
    }

    @Override
    public CreditVillageSubsiby selectSubsibyByCvs002(String cvs002) {
        return creditVillageSubsibyMapper.selectSubsibyByCvs002(cvs002);
    }
}
