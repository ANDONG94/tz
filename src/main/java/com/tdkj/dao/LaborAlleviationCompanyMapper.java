package com.tdkj.dao;

import com.tdkj.model.LaborAlleviationCompany;
import com.tdkj.model.LaborExportAgency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LaborAlleviationCompanyMapper {

    int deleteByPrimaryKey(@Param("lac001")String lac001,@Param("realname")String realname);

    int insert(LaborAlleviationCompany laborAlleviationCompany);

    int insertSelective(LaborAlleviationCompany laborAlleviationCompany);

    LaborAlleviationCompany selectByPrimaryKey(String lac001);

    int updateByPrimaryKeySelective(LaborAlleviationCompany laborAlleviationCompany);

    int updateByPrimaryKey(LaborAlleviationCompany laborAlleviationCompany);


    /**
     * 根据行政区划查询劳务输出工作机构列表
     * @return
     */
    public List<LaborAlleviationCompany> queryLaborAlleviationCompanyByAab301(@Param("laborAlleviationCompany")LaborAlleviationCompany laborAlleviationCompany, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(@Param("laborAlleviationCompany")LaborAlleviationCompany laborAlleviationCompany);


}