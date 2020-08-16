package com.tdkj.ServiceImpl.FenhuHehu;

import com.tdkj.dao.FenhuHehu.SplitAndPoorMapper;
import com.tdkj.model.SplitAndPoor;
import com.tdkj.service.FenhuHehu.SplitAndPoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/10/9.
 */
@Service
public class SplitAndPoorServiceImpl implements SplitAndPoorService {

    @Autowired
    private SplitAndPoorMapper splitAndPoorMapper;

    @Override
    public int deleteByPrimaryKey(String sap001) {
        return splitAndPoorMapper.deleteByPrimaryKey(sap001);
    }

    @Override
    public int insert(SplitAndPoor splitAndPoor) {
        return splitAndPoorMapper.insert(splitAndPoor);
    }

    @Override
    public int insertSelective(SplitAndPoor splitAndPoor) {
        return splitAndPoorMapper.insertSelective(splitAndPoor);
    }

    @Override
    public SplitAndPoor selectByPrimaryKey(String sap001) {
        return splitAndPoorMapper.selectByPrimaryKey(sap001);
    }

    @Override
    public int updateByPrimaryKeySelective(SplitAndPoor splitAndPoor) {
        return splitAndPoorMapper.updateByPrimaryKeySelective(splitAndPoor);
    }

    @Override
    public int updateByPrimaryKey(SplitAndPoor splitAndPoor) {
        return splitAndPoorMapper.updateByPrimaryKey(splitAndPoor);
    }

    @Override
    public List<SplitAndPoor> querySplitAndPoorByAab301(SplitAndPoor splitAndPoor, String startRecord, String pageSize) {
        return splitAndPoorMapper.querySplitAndPoorByAab301(splitAndPoor,startRecord,pageSize);
    }

    @Override
    public String queryAllSplitAndPoorByAab301(SplitAndPoor splitAndPoor) {
        return splitAndPoorMapper.queryAllSplitAndPoorByAab301(splitAndPoor);
    }
}
