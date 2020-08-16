package com.tdkj.ServiceImpl.Relation;

import com.tdkj.dao.Relation.RelationMapper;
import com.tdkj.model.Relation;
import com.tdkj.service.Relation.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationMapper relationMapper;

    @Override
    public int deleteByPrimaryKey(String rtn001) {
        return relationMapper.deleteByPrimaryKey(rtn001);
    }

    @Override
    public int insert(Relation record) {
        return relationMapper.insert(record);
    }

    @Override
    public int insertSelective(Relation record) {
        return relationMapper.insertSelective(record);
    }

    @Override
    public List<Relation> selectByPrimaryKey(String companyid,String startRecord,String pageSize) {
        return relationMapper.selectCompanyid(companyid,startRecord,pageSize);
    }

    @Override
    public Relation selectByPrimaryKeyRtn(String rtn001) {
        return relationMapper.selectByPrimaryKeyRtn(rtn001);
    }

    @Override
    public int updateByPrimaryKeySelective(Relation record) {
        return relationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Relation record) {
        return relationMapper.updateByPrimaryKey(record);
    }

    @Override
    public String queryAllByCompanyid(String companyid) {
        return relationMapper.queryAllByCompanyid(companyid);
    }

    @Override
    public int updateByCompanyid(String companyid) {
        return relationMapper.updateByCompanyid(companyid);
    }

    @Override
    public String queryAllByPlf001(String peopleid) {
        return relationMapper.queryAllByPlf001(peopleid);
    }

    @Override
    public List<Relation> queryRelationPlf001(String peopleid) {
        return relationMapper.queryRelationPlf001(peopleid);
    }
}
