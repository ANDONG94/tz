package com.tdkj.service.JobFair;


import com.tdkj.model.JobFair;
import java.util.List;

/**
 * @ClassName: jobFairService
 * @Description TODO
 * @Author and
 * @Date 2019/5/1212:32
 * @Version 1.0
 */

public interface JobFairService {

    int deleteByPrimaryKey(String jfr001);

    int insert(JobFair record);

    int insertSelective(JobFair record);

    JobFair selectByPrimaryKey(String jfr001);

    int updateByPrimaryKeySelective(JobFair record);

    int updateByPrimaryKey(JobFair record);

    List<JobFair> queryJobFairServiceByAab301(JobFair jobFair, String startRecord, String pageSize);

    String queryAllByAab301(JobFair jobFair);

}
