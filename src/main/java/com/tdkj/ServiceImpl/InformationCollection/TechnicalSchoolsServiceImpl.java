package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.TechnicalSchoolsMapper;
import com.tdkj.model.TechnicalSchools;
import com.tdkj.model.ThsPlf;
import com.tdkj.service.InformationCollection.TechnicalSchoolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class TechnicalSchoolsServiceImpl implements TechnicalSchoolsService {

    @Autowired
    private TechnicalSchoolsMapper technicalSchoolsMapper;

    @Override
    public int deleteByPrimaryKey(String ths001) {
        return technicalSchoolsMapper.deleteByPrimaryKey(ths001);
    }

    @Override
    public int insert(TechnicalSchools record) {
        return 0;
    }

    @Override
    public int insertSelective(TechnicalSchools technicalSchools) {
        return technicalSchoolsMapper.insertSelective(technicalSchools);
    }

    @Override
    public TechnicalSchools selectByPrimaryKey(String ths001) {
        return technicalSchoolsMapper.selectByPrimaryKey(ths001);
    }

    @Override
    public int updateByPrimaryKeySelective(TechnicalSchools technicalSchools) {
        return technicalSchoolsMapper.updateByPrimaryKeySelective(technicalSchools);
    }

    @Override
    public int updateByPrimaryKey(TechnicalSchools technicalSchools) {
        return 0;
    }

    @Override
    public List<TechnicalSchools> queryTechnicalSchoolsByThs002(String ths002) {
        return technicalSchoolsMapper.queryTechnicalSchoolsByThs002(ths002);
    }

    @Override
    public int deleteTechnicalSchoolsByThs002(String ths002,String realname) {
        return technicalSchoolsMapper.deleteTechnicalSchoolsByThs002(ths002,realname);
    }

    @Override
    public List<ThsPlf> queryTechnicalSchoolAccountByAab301(ThsPlf thsPlf, String startRecord, String pageSize) {
        return technicalSchoolsMapper.queryTechnicalSchoolAccountByAab301(thsPlf,startRecord,pageSize);
    }

    @Override
    public String queryAllTechnicalSchoolByAab301(ThsPlf thsPlf) {
        return technicalSchoolsMapper.queryAllTechnicalSchoolByAab301(thsPlf);
    }

    @Override
    public int updateIsNewByThs002(String ths002) {
        return technicalSchoolsMapper.updateIsNewByThs002(ths002);
    }

    //根据劳动力id 查询最新的技工院校信息
    @Override
    public TechnicalSchools queryNewTechnicalSchools(String ths002) {
        return technicalSchoolsMapper.queryNewTechnicalSchools(ths002);
    }
}
