package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.ExSituPovertyMapper;
import com.tdkj.model.ExSituPoverty;
import com.tdkj.service.Information.ExSituPovertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 异地扶贫创业服务站
 */
@Service
public class ExSituPovertyServiceImpl implements ExSituPovertyService {

    @Autowired
    private ExSituPovertyMapper exSituPovertyMapper;

    @Override
    public int deleteByPrimaryKey(String esp001,String realname) {
        return  exSituPovertyMapper.deleteByPrimaryKey(esp001,realname);
    }

    @Override
    public int insert(ExSituPoverty exSituPoverty) {
        return exSituPovertyMapper.insert(exSituPoverty);
    }

    @Override
    public int insertSelective(ExSituPoverty exSituPoverty) {
        return exSituPovertyMapper.insertSelective(exSituPoverty);
    }

    @Override
    public ExSituPoverty selectByPrimaryKey(String esp001) {
        return exSituPovertyMapper.selectByPrimaryKey(esp001);
    }

    @Override
    public int updateByPrimaryKeySelective(ExSituPoverty exSituPoverty) {
        return exSituPovertyMapper.updateByPrimaryKeySelective(exSituPoverty);
    }

    @Override
    public int updateByPrimaryKey(ExSituPoverty exSituPoverty) {
        return exSituPovertyMapper.updateByPrimaryKey(exSituPoverty);
    }

    @Override
    public List<ExSituPoverty> queryExSituPovertyByAab301(ExSituPoverty exSituPoverty, String startRecord, String pageSize) {
        return exSituPovertyMapper.queryExSituPovertyByAab301(exSituPoverty,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(ExSituPoverty exSituPoverty) {
        return exSituPovertyMapper.queryAllByAab301(exSituPoverty);
    }
}
