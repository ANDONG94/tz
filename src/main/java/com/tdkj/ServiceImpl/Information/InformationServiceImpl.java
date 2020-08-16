package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.InformationMapper;
import com.tdkj.model.Information;
import com.tdkj.service.Information.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationMapper informationMapper;

    @Override
    public int deleteByPrimaryKey(String ift001) {
        return informationMapper.deleteByPrimaryKey(ift001);
    }

    @Override
    public int insert(Information record) {
        return 0;
    }

    @Override
    public int insertSelective(Information record) {
        return informationMapper.insertSelective(record);
    }

    @Override
    public Information selectByPrimaryKey(String ift001) {
        return informationMapper.selectByPrimaryKey(ift001);
    }

    @Override
    public int updateByPrimaryKeySelective(Information record) {
        return informationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Information record) {
        return 0;
    }

    /**
     * 根据aab301查询综合信息
     */
    @Override
    public List<Information> queryInformationByAab301(String aab301,String niandu,String startRecord,String pageSize){
        return informationMapper.queryInformationByAab301(aab301,niandu,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(String aab301) {
        return informationMapper.queryAllByAab301(aab301);
    }
}
