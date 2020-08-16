package com.tdkj.ServiceImpl.JobFair;

import com.tdkj.dao.JobFair.JobFairMapper;
import com.tdkj.model.JobFair;
import com.tdkj.service.JobFair.JobFairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: jobFairServiceImpl
 * @Description TODO
 * @Author and
 * @Date 2019/5/1212:55
 * @Version 1.0
 */
@Service
public class JobFairServiceImpl implements JobFairService {
    @Autowired
    private JobFairMapper jobFairMapper;

    @Override
    public int deleteByPrimaryKey(String jfr001) {
        return jobFairMapper.deleteByPrimaryKey(jfr001);
    }

    @Override
    public int insert(JobFair record) {
        return jobFairMapper.insert(record);
    }

    @Override
    public int insertSelective(JobFair record) {
        return jobFairMapper.insertSelective(record);

    }

    @Override
    public JobFair selectByPrimaryKey(String jfr001) {
        return jobFairMapper.selectByPrimaryKey(jfr001);
    }

    @Override
    public int updateByPrimaryKeySelective(JobFair record) {
        return jobFairMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(JobFair record) {
        return jobFairMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<JobFair> queryJobFairServiceByAab301(JobFair jobFair, String startRecord, String pageSize) {
        return jobFairMapper.queryJobFairServiceByAab301(jobFair,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(JobFair jobFair) {
        return jobFairMapper.queryAllByAab301(jobFair);
    }
}
