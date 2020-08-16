package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.TakeTheLeadMapper;
import com.tdkj.model.TakeTheLead;
import com.tdkj.service.Information.TakeTheLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
@Service
public class TakeTheLeadServiceImpl implements TakeTheLeadService {

    @Autowired
    private TakeTheLeadMapper takeTheLeadMapper;

    @Override
    public int deleteByPrimaryKey(String thl001) {
        return takeTheLeadMapper.deleteByPrimaryKey(thl001);
    }

    @Override
    public int insert(TakeTheLead takeTheLead) {
        return 0;
    }

    @Override
    public int insertSelective(TakeTheLead takeTheLead) {
        return takeTheLeadMapper.insertSelective(takeTheLead);
    }

    @Override
    public TakeTheLead selectByPrimaryKey(String thl001) {
        return takeTheLeadMapper.selectByPrimaryKey(thl001);
    }

    @Override
    public int updateByPrimaryKeySelective(TakeTheLead takeTheLead) {
        return takeTheLeadMapper.updateByPrimaryKeySelective(takeTheLead);
    }

    @Override
    public int updateByPrimaryKey(TakeTheLead takeTheLead) {
        return 0;
    }

    /**
     * 根据行政区划查询创业带头人信息列表
     * @return
     */
    @Override
    public List<TakeTheLead> queryChuangyeLeaderByAab301(TakeTheLead takeTheLead, String startRecord, String pageSize) {
        return takeTheLeadMapper.queryChuangyeLeaderByAab301(takeTheLead,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    @Override
    public String queryAllByAab301(TakeTheLead takeTheLead) {
        return takeTheLeadMapper.queryAllByAab301(takeTheLead);
    }
}
