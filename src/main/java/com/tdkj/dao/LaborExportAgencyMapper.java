package com.tdkj.dao;

import com.tdkj.model.LaborExportAgency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LaborExportAgencyMapper {

    int deleteByPrimaryKey(@Param("lea001")String lea001,@Param("realname")String realname);

    int insert(LaborExportAgency record);

    int insertSelective(LaborExportAgency record);

    LaborExportAgency selectByPrimaryKey(String lea001);

    int updateByPrimaryKeySelective(LaborExportAgency record);

    int updateByPrimaryKey(LaborExportAgency record);

    /**
     * 根据行政区划查询劳务输出工作机构列表
     * @return
     */
    public List<LaborExportAgency> queryLaborExportAgencyByAab301(@Param("laborExportAgency")LaborExportAgency laborExportAgency, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(@Param("laborExportAgency")LaborExportAgency laborExportAgency);
}