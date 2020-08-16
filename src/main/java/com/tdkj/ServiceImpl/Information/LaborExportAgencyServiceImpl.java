package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.LaborExportAgencyMapper;
import com.tdkj.model.LaborExportAgency;
import com.tdkj.service.Information.LaborExportAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
@Service
public class LaborExportAgencyServiceImpl implements LaborExportAgencyService {

    @Autowired
    private LaborExportAgencyMapper laborExportAgencyMapper;

    @Override
    public int deleteByPrimaryKey(String lea001, String realname) {
        return laborExportAgencyMapper.deleteByPrimaryKey(lea001,realname);
    }

    @Override
    public int insert(LaborExportAgency laborExportAgency) {
        return 0;
    }

    @Override
    public int insertSelective(LaborExportAgency laborExportAgency) {
        return laborExportAgencyMapper.insertSelective(laborExportAgency);
    }

    @Override
    public LaborExportAgency selectByPrimaryKey(String lea001) {
        return laborExportAgencyMapper.selectByPrimaryKey(lea001);
    }

    @Override
    public int updateByPrimaryKeySelective(LaborExportAgency laborExportAgency) {
        return laborExportAgencyMapper.updateByPrimaryKeySelective(laborExportAgency);
    }

    @Override
    public int updateByPrimaryKey(LaborExportAgency laborExportAgency) {
        return 0;
    }

    @Override
    public List<LaborExportAgency> queryLaborExportAgencyByAab301(LaborExportAgency laborExportAgency, String startRecord, String pageSize) {
        return laborExportAgencyMapper.queryLaborExportAgencyByAab301(laborExportAgency,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(LaborExportAgency laborExportAgency) {
        return laborExportAgencyMapper.queryAllByAab301(laborExportAgency);
    }
}
