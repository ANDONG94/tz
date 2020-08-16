package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.JobInformationBaseMapper;
import com.tdkj.model.JobInformationBase;
import com.tdkj.service.Information.JobInformationBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
@Service
public class JobInformationBaseServiceImpl implements JobInformationBaseService {
    @Autowired
    private JobInformationBaseMapper jobInformationBaseMapper;


    @Override
    public int deleteByPrimaryKey(String jib001, String realname) {
        return jobInformationBaseMapper.deleteByPrimaryKey(jib001,realname);
    }

    @Override
    public int insert(JobInformationBase jobInformationBase) {
        return 0;
    }

    @Override
    public int insertSelective(JobInformationBase jobInformationBase) {
        return jobInformationBaseMapper.insertSelective(jobInformationBase);
    }

    @Override
    public JobInformationBase selectByPrimaryKey(String jib001) {
        return jobInformationBaseMapper.selectByPrimaryKey(jib001);
    }

    @Override
    public int updateByPrimaryKeySelective(JobInformationBase jobInformationBase) {
        return jobInformationBaseMapper.updateByPrimaryKeySelective(jobInformationBase);
    }

    @Override
    public int updateByPrimaryKey(JobInformationBase jobInformationBase) {
        return 0;
    }

    @Override
    public List<JobInformationBase> queryJobInformationBaseByAab301(JobInformationBase jobInformationBase, String startRecord, String pageSize) {
        return jobInformationBaseMapper.queryJobInformationBaseByAab301(jobInformationBase,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(JobInformationBase jobInformationBase) {
        return jobInformationBaseMapper.queryAllByAab301(jobInformationBase);
    }
}
