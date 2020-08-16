package com.tdkj.dao;

import com.tdkj.model.JobInformationBase;
import com.tdkj.model.LaborAlleviationCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobInformationBaseMapper {

    int deleteByPrimaryKey(@Param("jib001")String jib001,@Param("realname")String realname);

    int insert(JobInformationBase jobInformationBase);

    int insertSelective(JobInformationBase jobInformationBase);

    JobInformationBase selectByPrimaryKey(String jib001);

    int updateByPrimaryKeySelective(JobInformationBase jobInformationBase);

    int updateByPrimaryKey(JobInformationBase jobInformationBase);

    /**
     * 根据行政区划查询岗位信息库列表
     * @return
     */
    public List<JobInformationBase> queryJobInformationBaseByAab301(@Param("jobInformationBase")JobInformationBase jobInformationBase, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(@Param("jobInformationBase")JobInformationBase jobInformationBase);

}