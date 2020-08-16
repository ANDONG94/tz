package com.tdkj.service.Information;

import com.tdkj.model.JobInformationBase;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 岗位信息库
 *
 */
public interface JobInformationBaseService {

    int deleteByPrimaryKey(String jib001,String realname);

    int insert(JobInformationBase jobInformationBase);

    int insertSelective(JobInformationBase jobInformationBase);

    JobInformationBase selectByPrimaryKey(String jib001);

    int updateByPrimaryKeySelective(JobInformationBase jobInformationBase);

    int updateByPrimaryKey(JobInformationBase jobInformationBase);

    /**
     * 根据行政区划查询岗位信息库列表
     * @return
     */
    public List<JobInformationBase> queryJobInformationBaseByAab301(JobInformationBase jobInformationBase, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(JobInformationBase jobInformationBase);
}
