package com.tdkj.service.Information;

import com.tdkj.model.TakeTheLead;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
public interface TakeTheLeadService {
    int deleteByPrimaryKey(String thl001);

    int insert(TakeTheLead takeTheLead);

    int insertSelective(TakeTheLead takeTheLead);

    TakeTheLead selectByPrimaryKey(String thl001);

    int updateByPrimaryKeySelective(TakeTheLead takeTheLead);

    int updateByPrimaryKey(TakeTheLead takeTheLead);

    /**
     * 根据行政区划查询创业带头人信息列表
     * @return
     */
    public List<TakeTheLead> queryChuangyeLeaderByAab301(TakeTheLead takeTheLead, String startRecord, String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String  queryAllByAab301(TakeTheLead takeTheLead);

}
