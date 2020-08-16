package com.tdkj.ServiceImpl.Ndrw;

import com.tdkj.dao.ndrw.NdrwMapper;
import com.tdkj.model.Ndrw;
import com.tdkj.service.Ndrw.NdrwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/16.
 */
@Service
public class NdrwServiceImpl implements NdrwService{

    @Autowired
    private NdrwMapper ndrwMapper;

    @Override
    public int deleteByPrimaryKey(String ndrwid) {
        return ndrwMapper.deleteByPrimaryKey(ndrwid);
    }

    @Override
    public int insert(Ndrw record) {
        return ndrwMapper.insert(record);
    }

    @Override
    public int insertSelective(Ndrw record) {
        return ndrwMapper.insertSelective(record);
    }

    @Override
    public Ndrw selectByPrimaryKey(String ndrwid) {
        return ndrwMapper.selectByPrimaryKey(ndrwid);
    }

    @Override
    public int updateByPrimaryKeySelective(Ndrw record) {
        return ndrwMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Ndrw record) {
        return ndrwMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Ndrw> queryNdrwByAab301(Ndrw ndrw, String startRecord, String pageSize) {
        return ndrwMapper.queryNdrwByAab301(ndrw,startRecord,pageSize);
    }

    @Override
    public String queryAllCountByAab301(Ndrw ndrw) {
        return ndrwMapper.queryAllCountByAab301(ndrw);
    }

    @Override
    public List<Ndrw> checkNdrwByAab301(Ndrw ndrw) {
        return ndrwMapper.checkNdrwByAab301(ndrw);
    }
}
