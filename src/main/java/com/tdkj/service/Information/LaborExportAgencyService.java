package com.tdkj.service.Information;

import com.tdkj.model.LaborBrokering;
import com.tdkj.model.LaborExportAgency;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 劳务输出工作机构
 */
public interface LaborExportAgencyService {

    int deleteByPrimaryKey(String lea001,String realname);

    int insert(LaborExportAgency laborExportAgency);

    int insertSelective(LaborExportAgency laborExportAgency);

    LaborExportAgency selectByPrimaryKey(String lea001);

    int updateByPrimaryKeySelective(LaborExportAgency laborExportAgency);

    int updateByPrimaryKey(LaborExportAgency laborExportAgency);

    /**
     * 根据行政区划查询劳务输出工作机构列表
     * @return
     */
    public List<LaborExportAgency> queryLaborExportAgencyByAab301(LaborExportAgency laborExportAgency, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(LaborExportAgency laborExportAgency);



}
