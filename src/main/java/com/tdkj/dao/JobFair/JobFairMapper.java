package com.tdkj.dao.JobFair;

import com.tdkj.model.JobFair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobFairMapper {
    int deleteByPrimaryKey(String jfr001);

    int insert(JobFair record);

    int insertSelective(JobFair record);

    JobFair selectByPrimaryKey(String jfr001);

    int updateByPrimaryKeySelective(JobFair record);

    int updateByPrimaryKey(JobFair record);

    List<JobFair> queryJobFairServiceByAab301(@Param("jobFair")JobFair jobFair, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    String queryAllByAab301(@Param("jobFair")JobFair jobFair);
}