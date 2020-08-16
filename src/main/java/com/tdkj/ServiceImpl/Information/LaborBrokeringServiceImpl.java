package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.LaborBrokeringMapper;
import com.tdkj.model.LaborBrokering;
import com.tdkj.service.Information.LaborBrokeringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
@Service
public class LaborBrokeringServiceImpl implements LaborBrokeringService {

    @Autowired
    private LaborBrokeringMapper laborBrokeringMapper;

    @Override
    public int deleteByPrimaryKey(String lbk001) {
        return laborBrokeringMapper.deleteByPrimaryKey(lbk001);
    }

    @Override
    public int insert(LaborBrokering record) {
        return 0;
    }

    @Override
    public int insertSelective(LaborBrokering record) {
        return laborBrokeringMapper.insertSelective(record);
    }

    @Override
    public LaborBrokering selectByPrimaryKey(String lbk001) {
        return laborBrokeringMapper.selectByPrimaryKey(lbk001);
    }

    @Override
    public int updateByPrimaryKeySelective(LaborBrokering record) {
        return laborBrokeringMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LaborBrokering record) {
        return 0;
    }

    /**
     * 根据行政区划查询劳务经济人列表
     * @return
     */
    @Override
    public List<LaborBrokering> querLaowuPersonByAab301(LaborBrokering laborBrokering, String startRecord, String pageSize) {
        return laborBrokeringMapper.querLaowuPersonByAab301(laborBrokering,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    @Override
    public String queryAllByAab301(LaborBrokering laborBrokering) {
        return laborBrokeringMapper.queryAllByAab301(laborBrokering);
    }
}
