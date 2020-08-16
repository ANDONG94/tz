package com.tdkj.ServiceImpl.IncubatorBase;

import com.tdkj.dao.IncubatorBase.IncubatorBaseMapper;
import com.tdkj.model.IncubatorBase;
import com.tdkj.service.IncubatorBase.IncubatorBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class IncubatorBaseServiceImpl implements IncubatorBaseService {

    @Autowired
    private IncubatorBaseMapper incubatorBaseMapper;


    @Override
    public int deleteByPrimaryKey(String ibb001) {
        return incubatorBaseMapper.deleteByPrimaryKey(ibb001);
    }

    @Override
    public int insert(IncubatorBase record) {
        return 0;
    }

    @Override
    public int insertSelective(IncubatorBase record) {
        return incubatorBaseMapper.insertSelective(record);
    }

    @Override
    public IncubatorBase selectByPrimaryKey(String ibb001) {
        return incubatorBaseMapper.selectByPrimaryKey(ibb001);
    }

    @Override
    public int updateByPrimaryKeySelective(IncubatorBase record) {
        return incubatorBaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IncubatorBase record) {
        return 0;
    }


    /**
     * 根据aab301 查询创业基地园区中心
     */
    @Override
    public List<IncubatorBase> queryIncubatorBaseByAab301(IncubatorBase incubatorBase, String startRecord, String pageSize) {
        return incubatorBaseMapper.queryIncubatorBaseByAab301(incubatorBase,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询所有数据
     * @param aab301
     * @return
     */
    @Override
    public String queryAllByAab301(IncubatorBase incubatorBase) {
        return incubatorBaseMapper.queryAllByAab301(incubatorBase);
    }


}
