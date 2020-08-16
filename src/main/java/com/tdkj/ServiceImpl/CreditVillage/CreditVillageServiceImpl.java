package com.tdkj.ServiceImpl.CreditVillage;

import com.tdkj.dao.CreditVillage.CreditVillageMapper;
import com.tdkj.model.CreditVillage;
import com.tdkj.service.CreditVillage.CreditVillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class CreditVillageServiceImpl implements CreditVillageService {

    @Autowired
    private CreditVillageMapper creditVillageMapper;

    @Override
    public int deleteByPrimaryKey(String cvg001) {
        return creditVillageMapper.deleteByPrimaryKey(cvg001);
    }

    @Override
    public int insert(CreditVillage record) {
        return 0;
    }

    @Override
    public int insertSelective(CreditVillage record) {
        return creditVillageMapper.insertSelective(record);
    }

    @Override
    public CreditVillage selectByPrimaryKey(String cvg001) {
        return creditVillageMapper.selectByPrimaryKey(cvg001);
    }

    @Override
    public int updateByPrimaryKeySelective(CreditVillage record) {
        return creditVillageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CreditVillage record) {
        return 0;
    }

    /**
     * 根据aab301 查询信用乡村信息
     * @param aab301
     * @return
     */
    @Override
    public List<CreditVillage> queryCreditVillageByAab301(CreditVillage creditVillage,String startRecord,String pageSize) {
        return creditVillageMapper.queryCreditVillageByAab301(creditVillage,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询总条数
     * @param aab301
     * @return
     */
    @Override
    public String queryAllByAab301(CreditVillage creditVillage) {
        return creditVillageMapper.queryAllByAab301(creditVillage);
    }
}
